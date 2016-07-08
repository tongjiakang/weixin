<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	$('#welcomesEditForm').form({
		url : '${ctx}/welcomes/update',
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
				parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
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

$(function(){
	
/* 	$('#accountId').combogrid(
			{
				panelWidth : 500,
				idField : 'id',
				textField : 'name',
				url : '${ctx}/account/dataGrid',
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
			}); */
	$('#accountId').combobox({
		url : '${ctx}/account/getAcounts',
		valueField : 'id',
		textField : 'text'
	});
	
	if("${welcomes.msgType}"=='text'){
		datagrid("${ctx}/news/dataGrid");
	}else if("${welcomes.msgType}"=='news'){
		datagrid("${ctx}/picMaterial/dataGrid");
	}

	$("#msgType").combobox({
		value : '${welcomes.msgType}',
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

	$('#accountId').combobox('setValue', "${welcomes.accountId}");
	$('#msgId').combogrid('setValue', "${welcomes.msgId}");
	
});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden; padding: 3px;">
		<form id="welcomesEditForm" method="post">
			<input type="hidden" name="id" value="${welcomes.id}">
			<table class="grid">
				<tr>
					<td>公众号:</td>
					<td>
					<input id="accountId" name="accountId" type="text" data-options="editable:false,panelHeight:'auto',required:true"/>
					</td>
				</tr>
				<tr>
					<td>消息类型:</td>
					<td><select name="msgType" id="msgType" class="easyui-combobox" data-options="editable:false,width:150,height:25,required:true">
							<option value="text">文本消息</option>
							<option value="news">图文消息</option>
					</select></td>
				</tr>
				<tr>
					<td>消息模板:</td>
					<td>
					<input id="msgId" name="msgId" type="text" data-options="editable:false,panelHeight:'auto',required:true"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>