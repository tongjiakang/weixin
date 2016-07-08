<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	$('#customMenuAddForm').form({
		url : '${ctx}/customMenu/save',
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
	$(function() {
		
		$('#parentMenu').combotree({
			url : '${ctx}/customMenu/tree',
			parentField : 'pid',
			panelHeight : '200',
			panelWidth : '170'
		});
		
		datagrid("${ctx}/news/dataGrid");
		datagridUrl("${ctx}/link/dataGrid");
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
/* 		$('#msgId').combobox({
			url : '${ctx}/keyWord/getMaterial?type=text',
			valueField : 'id',
			textField : 'text'
		}); */
		/* $('#url').combobox({
			url : '${ctx}/link/getLinks',
			valueField : 'id',
			textField : 'text'
		}); */
		
		$('#account').combobox({
			url : '${ctx}/account/getAcounts',
			valueField : 'id',
			textField : 'text',
			onSelect:function(record){
				$('#parentMenu').combotree({
					url : '${ctx}/customMenu/tree?account='+record.id,
					parentField : 'pid',
					panelHeight : '200',
					panelWidth : '170'
				});
            }
		});
		/* $("#msgType").change(function() {
			var url = "${ctx}/keyWord/getMaterial?type=" + $(this).val();
			$('#msgId').combobox('setValue', null);
			$('#msgId').combobox('reload', url);
		}); */
		$("[type='link']").hide();
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
				$('#url').combogrid('grid').datagrid('options').queryParams.name = '';
				datagridUrl("${ctx}/link/dataGrid");
				$('#url').combogrid({
					required : true
				});
				$("[type='msg']").hide();
				$('#msgId').combogrid({
					required : false
				});
			}
		});
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
			+ '<button href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchDataUrlGrid()">搜索</button></div></div>';
		
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
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden; padding: 3px;">
		<form id="customMenuAddForm" method="post">
			<table class="grid">
				<tr>
					<td>公众号:</td>
					<td><input name="account" id="account" class="easyui-combobox"
						data-options="required:true" editable="false" /></td>
				</tr>
				<tr>
					<td>菜单名称:</td>
					<td><input name="menuName" type="text" placeholder="请输入菜单名称"
						class="easyui-validatebox span2" data-options="required:true"
						value="" style="width: 200px; height: 21px;"></td>
				</tr>
				<tr>
					<td>上级菜单:</td>
					<td><input id="parentMenu" name="parentMenu" class="easyui-combotree"
						placeholder="请选择上级菜单" /></td>
				</tr>
				<tr>
					<td>动作设置:</td>
					<td><select name="menuType" id="menuType">
							<option value="msg">消息触发类</option>
							<option value="link">网页链接类</option>
					</select></td>
				</tr>
				<tr type="msg">
					<td>消息类型:</td>
					<td><select name="msgType" id="msgType" >
							<option value="text">文本消息</option>
							<option value="news">图文消息</option>
					</select></td>
				</tr>
				<tr type="msg">
					<td>消息模板:</td>
					<td><input id="msgId" name="msgId" type="text" data-options="editable:false,panelHeight:'auto',required:true"/></td>
				</tr>
				<tr type="link">
					<td>URL:</td>
					<td><input name="url" id="url" type="text" data-options="editable:false,panelHeight:'auto'" ></td>
				</tr>
				<tr>
					<td>菜单标识:</td>
					<td><input name="key" type="text" placeholder="请输入菜单标识"
						class="easyui-validatebox span2" data-options="required:true"
						value="" style="width: 200px; height: 21px;"></td>
				</tr>
				<tr>
					<td>顺序:</td>
					<td><input name="order" type="text" placeholder="请输入菜单顺序"
						class="easyui-numberbox span2" data-options="required:true,min:0,max:10,precision:0"
						value="" style="width: 200px; height: 21px;"></td>
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
</div>