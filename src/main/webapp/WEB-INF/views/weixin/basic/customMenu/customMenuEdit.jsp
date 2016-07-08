<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	$('#customMenuEditForm').form({
		url : '${ctx}/customMenu/update',
		onSubmit : function() {
			progressLoad();
			var isValid = $(this).form('validate');
			if (!isValid) {
				progressClose();
			}
			return isValid;
		},
		success : function(result) {
			progressClose();
			result = $.parseJSON(result);
			if (result.success) {
				parent.$.modalDialog.openner_treeGrid.treegrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
				parent.$.modalDialog.handler.dialog('close');
			} else {
				parent.$.messager.alert('错误', result.msg, 'error');
			}
		}
	});
	
	
	function searchDataGrid() {
		$('#msgId').combogrid('grid').datagrid('options').queryParams.name = $("[name='msgName']").val();
		$('#msgId').combogrid('grid').datagrid('reload');
	}
	
	function datagrid(url){
		
		$('#msgId').combogrid(
			{
				panelWidth : 500,
				idField : 'id',
				textField : 'name',
				url : '',
				pagination : true,
				editable : false,
				singleSelect : true,
				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
				columns : [ [
					{
						field : 'name',
						title : '名称',
						width : '200',
						align : 'center'
					}, {
						width : '200',
						title : '机构名称',
						field : 'orgName',
						align : 'center'
					} ] ]
			});
		
		var tool = '<div style="padding:5px;height:auto">'
			+ '<div>消息名称:'
			+ '<input type="text" name="msgName" placeholder="请输入消息名称"/>'
			+ '<button href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchDataGrid()">搜索</button></div></div>';
		
		$('#msgId').combogrid('grid').datagrid({
			toolbar: tool,
			url:url
		});
		
	}
	function searchDataUrlGrid() {
		$('#url').combogrid('grid').datagrid('options').queryParams.name = $("[name='urlName']").val();
		$('#url').combogrid('grid').datagrid('reload');
	}

	function datagridUrl(url){
		
		$('#url').combogrid(
			{
				panelWidth : 500,
				idField : 'id',
				textField : 'name',
				url : url,
				pagination : true,
				editable : false,
				singleSelect : true,
				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
				columns : [ [
					{
						field : 'name',
						title : '名称',
						width : '200',
						align : 'center'
					}, {
						width : '200',
						title : '链接',
						field : 'url',
						align : 'center'
					} ] ],
				toolbar: $("#toolbar")
			});
		
	}
	
	
	$(function() {
		
		$('#parentMenu').combotree({
			url : '${ctx}/customMenu/tree?account=${customMenu.account}',
			parentField : 'pid',
			panelHeight : '200',
			panelWidth : '170',
			value : '${customMenu.parentMenu}'
		});
		
		
	/* 	$('#url').combobox({
			url : '${ctx}/link/getLinks',
			valueField : 'id',
			textField : 'text',
			value:'${customMenu.url}'
		}); */
		
		$('#account').combobox({
			url : '${ctx}/account/getAcounts',
			valueField : 'id',
			textField : 'text',
			value : '${customMenu.account}',
			onSelect:function(record){
				$('#parentMenu').combotree({
					url : '${ctx}/customMenu/tree?account='+record.id,
					parentField : 'pid',
					panelHeight : '200',
					panelWidth : '170'
				});
            }
		});
		
		
		
		$("#msgType").combobox({
			editable:false,
			onChange : function(newValue, oldValue) {
				if(newValue=='text'){
					$('#msgId').combogrid('grid').datagrid('options').queryParams.name = '';
					datagrid("${ctx}/news/dataGrid");
				}else if(newValue=='news'){
					$('#msgId').combogrid('grid').datagrid('options').queryParams.name = '';
					datagrid("${ctx}/picMaterial/dataGrid");
				} 
			}
		});
		

		
		
		
		$("[type='link']").hide();
		if ('${customMenu.menuType}' == "msg") {
			$("[type='link']").hide();
			$('#url').combogrid({
				required : false
			});
			$("[type='msg']").show();
			$('#msgId').combogrid({
				required : true
			});
			if("${customMenu.msgType}"=='text'){
				datagrid("${ctx}/news/dataGrid");
			}else if("${customMenu.msgType}"=='news'){
				datagrid("${ctx}/picMaterial/dataGrid");
			}
			$('#msgId').combogrid('setValue', "${customMenu.msgId}");
		} else {
			$("[type='link']").show();
			$('#url').combogrid({
				required : true
			});
			$("[type='msg']").hide();
			$('#msgId').combogrid({
				required : false
			});
			datagridUrl("${ctx}/link/dataGrid");
			$('#url').combogrid('setValue', '${customMenu.url}');
		}
		$("#menuType").change(function() {
			if ($(this).val() == "msg") {
				
				$("[type='link']").hide();
				$('#url').combogrid({
					required : false
				});
				$("[type='msg']").show();
				$('#msgId').combogrid({
					required : true
				});
				 if($("#msgType").combobox('getValue')=='text'){
						$('#msgId').combogrid('grid').datagrid('options').queryParams.name = '';
						datagrid("${ctx}/news/dataGrid");
					}else if($("#msgType").combobox('getValue')=='news'){
						$('#msgId').combogrid('grid').datagrid('options').queryParams.name = '';
						datagrid("${ctx}/picMaterial/dataGrid");
					} 
			} else {
				$("[type='link']").show();
				$('#url').combogrid({
					required : true
				});
				$("[type='msg']").hide();
				$('#msgId').combogrid({
					required : false
				});
				$('#url').combogrid('grid').datagrid('options').queryParams.name = '';
				datagridUrl("${ctx}/link/dataGrid");
			}
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden; padding: 3px;">
		<form id="customMenuEditForm" method="post">
			<input type="hidden" name="id" value="${customMenu.id}">
			<table class="grid">
				<tr>
					<td>公众号:</td>
					<td><input name="account" id="account" class="easyui-combobox" disabled="disabled"
						data-options="required:true" editable="false" /></td>
				</tr>
				<tr>
					<td>菜单名称:</td>
					<td><input name="menuName" type="text" placeholder="请输入菜单名称"
						class="easyui-validatebox span2" data-options="required:true"
						value="${customMenu.menuName}" style="width: 200px; height: 21px;"></td>
				</tr>
				<tr>
					<td>上级菜单:</td>
					<td><input id="parentMenu" name="parentMenu"
						class="easyui-combotree" placeholder="请选择上级菜单" /></td>
				</tr>
				<tr>
					<td>动作设置:</td>
					<td><select name="menuType" id="menuType">
							<option value="msg" <c:if test="${customMenu.menuType=='msg'}">selected</c:if>>消息触发类</option>
							<option value="link" <c:if test="${customMenu.menuType=='link'}">selected</c:if>>网页链接类</option>
					</select></td>
				</tr>
				<tr type="msg">
					<td>消息类型:</td>
					<td><select name="msgType" id="msgType">
							<option value="text" <c:if test="${customMenu.msgType=='text'}">selected</c:if>>文本消息</option>
							<option value="news" <c:if test="${customMenu.msgType=='news'}">selected</c:if>>图文消息</option>
					</select></td>
				</tr>
				<tr type="msg">
					<td>消息模板:</td>
					<td><input id="msgId"  name="msgId"
						data-options="required:true" editable="false" /></td>
				</tr>
				<tr type="link">
					<td>URL:</td>
					<td><input name="url" id="url" type="text" data-options="editable:false,panelHeight:'auto',required:true" ></td>
				</tr>
				<tr>
					<td>菜单标识:</td>
					<td><input name="key" type="text" placeholder="请输入菜单标识"
						class="easyui-validatebox span2" data-options="required:true"
						value="${customMenu.key}" style="width: 200px; height: 21px;"></td>
				</tr>
				<tr>
					<td>顺序:</td>
					<td><input name="order" type="text" placeholder="请输入菜单顺序"
						class="easyui-numberbox span2"
						data-options="required:true,min:0,max:10,precision:0" value="${customMenu.order}"
						style="width: 200px; height: 21px;"></td>
				</tr>
			</table>
		</form>
		<div id="toolbar" style="display: none;">
			<div style="padding: 5px; height: auto">
				<div>
					链接名称 <input type="text" name="urlName" placeholder="请输入链接名称" />
					<button href="#" class="easyui-linkbutton" iconCls="icon-search"
						onclick="searchDataUrlGrid()">搜索</button>
				</div>
			</div>
		</div>
	</div>
</div>