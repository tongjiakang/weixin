<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
<script type="text/javascript">
	var dataGrid;
	var organizationTree;
	$(function() {

		organizationTree = $('#organizationTree').tree({
			url : '${ctx}/sys/organization/tree',
			parentField : 'pid',
			lines : true,
			onClick : function(node) {
				dataGrid.datagrid('load', {
					orgId : node.id
				});
			}
		});

		dataGrid = $('#dataGrid')
				.datagrid(
						{
							url : '${ctx}/sys/user/dataGrid',
							fit : true,
							striped : true,
							rownumbers : true,
							pagination : true,
							singleSelect : true,
							idField : 'id',
							sortName : 'createDate',
							sortOrder : 'asc',
							pageSize : 50,
							pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
							columns : [ [
									{
										width : '80',
										title : '登录名',
										field : 'loginname',
										sortable : true
									},
									{
										width : '80',
										title : '姓名',
										field : 'userName',
										sortable : true
									},
									{
										width : '80',
										title : '部门ID',
										field : 'orgId',
										hidden : true
									},
									{
										width : '100',
										title : '组织机构',
										field : 'orgName',
										align : "center"
									},
									{
										width : '150',
										title : '创建时间',
										field : 'createDate',
										align : "center",
										sortable : true,
										formatter : function(value, row, index) {
											var unixTimestamp = new Date(value);
											return unixTimestamp.toLocaleString();
										}
									},
									{
										width : '60',
										title : '性别',
										field : 'sex',
										align : "center",
										sortable : true,
										formatter : function(value, row, index) {
											switch (value) {
											case 0:
												return '男';
											case 1:
												return '女';
											}
										}
									},
									{
										width : '100',
										title : '电话',
										field : 'phone',
										align : "center",
										sortable : true
									},
									{
										width : '100',
										title : '是否默认',
										field : 'isdefault',
										align : "center",
										sortable : true,
										formatter : function(value, row, index) {
											switch (value) {
											case 1:
												return '默认';
											case 0:
												return '否';
											}
										}
									},
									{
										field : 'status',
										title : '状态',
										width : 40,
										align : "center",
										hidden : true
									},
									{
										field : 'statusLabel',
										title : '状态',
										width : 40,
										align : "center",
										styler : function(value, row, index) {
											if (value == "停用") {
												return "color:red;";
											} else {
												return "color:green;";
											}
										}
									},
									{
										field : 'action',
										title : '操作',
										width : 100,
										align : "center",
										formatter : function(value, row, index) {
											var str = $.formatString(
													'<a href="javascript:void(0)" onclick="editFun(\'{0}\');" >修改</a>',
													row.id);
											return str;
										}
									} ] ],
							toolbar : '#toolbar'
						});
	});

	function addFun() {
		parent.$.modalDialog({
			title : '新建用户',
			width : 500,
			height : 300,
			href : '${ctx}/sys/user/addPage',
			buttons : [ {
				text : '保存',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#userAddForm');
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

	function deleteFun(id) {
		if (id == undefined) {//点击右键菜单才会触发这个
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager.confirm('询问', '您是否要删除当前用户？', function(b) {
			if (b) {
				var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
				if (currentUserId != id) {
					progressLoad();
					$.post('${ctx}/sys/user/delete', {
						id : id
					}, function(result) {
						if (result.success) {
							parent.$.messager.alert('提示', result.msg, 'info');
							dataGrid.datagrid('reload');
						}
						progressClose();
					}, 'JSON');
				} else {
					parent.$.messager.show({
						title : '提示',
						msg : '不可以删除自己！'
					});
				}
			}
		});
	}

	function editFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '修改用户',
			width : 500,
			height : 300,
			href : '${ctx}/sys/user/editPage?id=' + id,
			buttons : [ {
				text : '保存',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#userEditForm');
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

	function searchFun() {
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		$('#searchForm input').val('');
		dataGrid.datagrid('load', {});
	}

	function resetPwdFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		
		$.ajax({
			type : "get", // 请求方式  
			url : "${ctx}/sys/user/resetPassword",
			data : "id=" + id,
			contentType : "application/json",
			dataType : "json",
			success : function(data) {
				if (data.success) {
					parent.$.messager.alert('提示', data.msg, 'info');
				} else {
					parent.$.messager.alert('错误', data.msg, 'error');
				}
			}
		});
	}
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" style="height: 30px; overflow: hidden; background-color: #fff">
		<form id="searchForm">
			<table>
				<tr>
					<th>姓名:</th>
					<td>
						<input name="userName" placeholder="请输入用户姓名" />
					</td>
					<th>创建时间:</th>
					<td>
						<input name="createdatetimeStart" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />
						至
						<input name="createdatetimeEnd" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:true,title:'用户列表'">
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
	<div data-options="region:'west',border:true,split:false,title:'组织机构'" style="width: 180px; overflow: hidden;">
		<ul id="organizationTree" style="width: 160px; margin: 10px 10px 10px 10px">
		</ul>
	</div>
	<div id="toolbar" style="display: none;">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">新建</a>
			<a onclick="deleteFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-del'">删除</a>
			<a onclick="resetPwdFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-resetPassword'">重置密码</a>
	</div>
</body>
</html>