<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />
<c:if test="${fn:contains(sessionInfo.resourceList, '/sys/resource/edit')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/sys/resource/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<title>资源管理</title>
<script type="text/javascript">
	var treeGrid;
	$(function() {

		treeGrid = $('#treeGrid').treegrid(
				{
					url : '${ctx}/sys/resource/treeGrid',
					idField : 'id',
					treeField : 'name',
					parentField : 'pid',
					fit : true,
					fitColumns : false,
					border : false,
					frozenColumns : [ [ {
						title : '编号',
						field : 'id',
						width : 40
					} ] ],
					columns : [ [
							{
								field : 'name',
								title : '资源名称',
								width : 150
							},
							{
								field : 'url',
								title : '资源路径',
								width : 200
							},
							{
								field : 'seq',
								title : '排序号',
								width : 50,
								align : 'center'
							},
							{
								field : 'iconCls',
								title : '图标',
								width : 100,
								hidden : true
							},
							{
								field : 'resourcetype',
								title : '资源类型',
								width : 80,
								align : "center",
								formatter : function(value, row, index) {
									switch (value) {
									case 0:
										return '菜单';
									case 1:
										return '按钮';
									case 2:
										return '链接';
									case 3:
										return '页面';
									}
								}
							},
							{
								field : 'pid',
								title : '上级资源ID',
								width : 150,
								hidden : true
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
								width : 80,
								align : "center",
								formatter : function(value, row, index) {
									var str = '&nbsp;';
									if ($.canEdit) {
										str += $.formatString(
												'<a href="javascript:void(0)" onclick="editFun(\'{0}\');" >修改</a>',
												row.id);
									}
									if ($.canDelete) {
										str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
										str += $.formatString(
												'<a href="javascript:void(0)" onclick="deleteFun(\'{0}\');" >删除</a>',
												row.id);
									}
									return str;
								}
							} ] ],
					onLoadSuccess : function() {
// 						$('#treeGrid').treegrid('collapseAll')
					},
					toolbar : '#toolbar'
				});
	});

	function editFun(id) {
		if (id != undefined) {
			treeGrid.treegrid('select', id);
		}
		var node = treeGrid.treegrid('getSelected');
		if (node) {
			parent.$.modalDialog({
				title : '修改资源',
				width : 500,
				height : 350,
				href : '${ctx}/sys/resource/editPage?id=' + node.id,
				buttons : [ {
					text : '保存',
					handler : function() {
						parent.$.modalDialog.openner_treeGrid = treeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
						var f = parent.$.modalDialog.handler.find('#resourceEditForm');
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
	}

	function deleteFun(id) {
		if (id != undefined) {
			treeGrid.treegrid('select', id);
		}
		var node = treeGrid.treegrid('getSelected');
		if (node) {
			parent.$.messager.confirm('询问', '您是否要删除当前资源？删除当前资源会连同子资源一起删除!', function(b) {
				if (b) {
					progressLoad();
					$.post('${ctx}/sys/resource/delete', {
						id : node.id
					}, function(result) {
						if (result.success) {
							parent.$.messager.alert('提示', result.msg, 'info');
							treeGrid.treegrid('reload');
							parent.layout_west_tree.tree('reload');
						}
						progressClose();
					}, 'JSON');
				}
			});
		}
	}

	function addFun() {
		parent.$.modalDialog({
			title : '新建资源',
			width : 500,
			height : 350,
			href : '${ctx}/sys/resource/addPage',
			buttons : [ {
				text : '保存',
				handler : function() {
					parent.$.modalDialog.openner_treeGrid = treeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#resourceAddForm');
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
	
	function changeFun(operateType) {
		var node = treeGrid.treegrid('getSelected');
		if (node == undefined) {
			parent.$.messager.alert('提示', "请选择要操作的数据", 'info');
			return;
		}
		
		var msg = '';
		if(operateType == 'start'){
			msg = '您是否要启用当前资源？启用当前资源同时也会启用子资源!';
		}
		else{
			msg = '您是否要禁用当前资源？禁用当前资源同时也会禁用子资源!';
		}

		if (node) {
			parent.$.messager.confirm('询问', msg, function(b) {
				if (b) {
					progressLoad();
					$.post('${ctx}/sys/resource/change', {
						id: node.id,
						operateType: operateType
					}, function(result) {
						if (result.success) {
							parent.$.messager.alert('提示', result.msg, 'info');
							treeGrid.treegrid('reload');
							parent.layout_west_tree.tree('reload');
						}
						else {
							parent.$.messager.alert('错误', result.msg, 'error');
						}
						progressClose();
					}, 'JSON');
				}
			});
		}
	}
	
	function updateFun(){
		progressLoad();
		$.post('${ctx}/sys/resource/updateTree', {
		}, function(result) {
			if (result.success) {
				parent.$.messager.alert('提示', result.msg, 'info');
			}
			else {
				parent.$.messager.alert('错误', result.msg, 'error');
			}
			progressClose();
		}, 'JSON');
	}
	
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'center',border:false" style="overflow: hidden;">
			<table id="treeGrid"></table>
		</div>
	</div>

	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/sys/resource/add')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">新建</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/sys/resource/change')}">
			<a onclick="changeFun('start');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-start'">启用</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/sys/resource/change')}">
			<a onclick="changeFun('stop');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-stop'">停用</a>
		</c:if>
		<a onclick="updateFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true">Update</a>
	</div>
</body>
</html>