<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />
<title>关注用户管理</title>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		$('#account').combobox({
			url : '${ctx}/account/getAcounts',
			valueField : 'id',
			textField : 'text',
			onSelect:function(record){  
				$('#dataGrid').datagrid('load',{
					accountId: $('#account').combobox('getValue')
				});
            },onLoadSuccess:function(){
            	var id = $("#account").combobox("getData")[0].id;
				$('#account').combobox('setValue', id);
				 dataGrid = $('#dataGrid').datagrid(
						{
							 url : '${ctx}/wxusermanage/dataGrid',
							 striped: true,
							 rownumbers: true,
							 pagination: true,
							 singleSelect: false,
							 fit:true,
							 idField : 'openid',
//							 sortName : 'createDate',
//							 sortOrder : 'desc',
							 pageSize : 50,
							 pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
							 queryParams: {
								 accountId: id
							 },
							frozenColumns:[[{field:'ckb',checkbox:true}]],
							columns :
									[ [
							        {
										width : '150',
										title : '头像',
										field : 'headimgurl',
										align : "center",
										formatter: function(value,row,index){
												return '<img style="width:80px;height:80px" src='+value+'>';
										}
									},
									{
										width : '150',
										title : '昵称',
										field : 'nickname',
										align : "center"
									},{
										width : '150',
										title : '分组',
										field : 'groupName',
										align : "center"
									},
									{
										width : '150',
										title : '性别',
										field : 'sex',
										align : "center",
										formatter: function(value,row,index){
											if (value==1){
												return '男';
											} else {
												return '女';
											}
										}
									},{
										width : '150',
										title : '省',
										field : 'province',
										align : "center"
									},{
										width : '150',
										title : '城市',
										field : 'city',
										align : "center"
									},{
										width : '150',
										title : 'OPENID',
										field : 'openid',
										align : "center"
									},{
										width : '150',
										title : '备注',
										field : 'remark',
										align : "center"
									}] ],
									toolbar:"#toolbar"
						}); 
            }
		});
		
	});
	function moveFun(){
		var selections = $('#dataGrid').datagrid("getSelections");
		if(selections.length==0){
			parent.$.messager.alert('错误', "请选择要移动的粉丝", 'error');
			return;
		}else if(selections.length>20){
			parent.$.messager.alert('错误', "移动分组不能超过20个粉丝", 'error');
			return;
		}
		
		var openids="";
		for(var i=0;i<selections.length;i++){
			openids=openids+selections[i].openid+",";
		}
		parent.$.modalDialog({
			title : '移动分组',
			width : 500,
			height : 320,
			href : '${ctx}/wxusermanage/moveGroupPage?openids=' + openids+'&accountId='+$('#account').combobox('getValue'),
			buttons : [ {
				text : '保存',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#moveGroupForm');
					f.submit();
				}
			}, {
				text : '取消',
				handler : function() {
					parent.$.modalDialog.handler.dialog('close');
				}
			} ]
		});
	}
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" class="divContent0">
		<form id="searchForm">
			<table class="tabContent0">
				<tr>
					<th width="10%" align="right">公众号：</th>
					<td width="20%" align="left">
						<input name="accountId" id="account" class="easyui-combobox"
						 editable="false" />
					</td>
					
				</tr>
			</table>
			<input id="page" name="page" type="hidden"/>
	        <input id="pageSize" name="pageSize" type="hidden"/>
	        <input id="sort" name="sort" type="hidden"/>
	        <input id="order" name="order" type="hidden"/>
		</form>
	</div>
	<div data-options="region:'center',border:true">
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
	<div id="toolbar" style="display: none;">
		<a onclick="moveFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-putout'">批量移动分组</a>
	</div>
</body>
</html>