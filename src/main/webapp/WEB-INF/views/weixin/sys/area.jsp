<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />
<c:if test="${fn:contains(sessionInfo.resourceList, '/sys/area/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/sys/area/operate/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<title>地区管理</title>
<script type="text/javascript">
	var treeGrid;
	$(function() {
		treeGrid = $('#treeGrid').treegrid(
				{
					url : '${ctx}/sys/area/treeGrid',
					idField : 'id',
					treeField : 'name',
					parentField : 'pid',
					fit : true,
					fitColumns : false,
					border : false,
					columns : [ [
							{
								title : '名称',
								field : 'name',
								width : 150
							},
							{
								field : 'seq',
								title : '排序号',
								width : 60,
								align : 'center'
							},
							{
								field : 'pid',
								title : '上级ID',
								width : 150,
								hidden : true
							},
							{
								width : '80',
								title : '创建人',
								field : 'createUser',
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
								width : '80',
								title : '修改人',
								field : 'updateUser',
								align : "center"
							},
							{
								width : '150',
								title : '修改时间',
								field : 'updateDate',
								align : "center",
								sortable : true,
								formatter : function(value, row, index) {
									var unixTimestamp = new Date(value);
									return unixTimestamp.toLocaleString();
								}

							},
							{
								field : 'action',
								title : '操作',
								width : 100,
								align : 'center',
								formatter : function(value, row, index) {
									var str = '&nbsp;';
									if ($.canEdit) {
										str += $.formatString(
												'<a href="javascript:void(0)" onclick="editFun(\'{0}\');" >修改</a>',
												row.id);
										str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
									}
									if ($.canDelete) {

										str += $.formatString(
												'<a href="javascript:void(0)" onclick="deleteFun(\'{0}\');" >删除</a>',
												row.id);
									}
									return str;
								}
							} ] ],
					toolbar : '#toolbar',
					onLoadSuccess : function() {
						$('#treeGrid').treegrid('collapseAll')
					}
				});
	});

	function editFun(id) {
		if (id != undefined) {
			treeGrid.treegrid('select', id);
		}
		var node = treeGrid.treegrid('getSelected');
		if (node) {
			parent.$.modalDialog({
				title : '修改地区',
				width : 500,
				height : 350,
				href : '${ctx}/sys/area/editPage?id=' + node.id,
				buttons : [ {
					text : '保存',
					handler : function() {
						parent.$.modalDialog.openner_treeGrid = treeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
						var f = parent.$.modalDialog.handler.find('#areaEditForm');
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

	function addFun() {
		parent.$.modalDialog({
			title : '新建地区',
			width : 500,
			height : 350,
			href : '${ctx}/sys/area/addPage',
			buttons : [ {
				text : '保存',
				handler : function() {
					parent.$.modalDialog.openner_treeGrid = treeGrid;
					var f = parent.$.modalDialog.handler.find('#areaAddForm');
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
		if (id != undefined) {
			treeGrid.treegrid('select', id);
		}
		var node = treeGrid.treegrid('getSelected');
		if (node) {
			parent.$.messager.confirm('询问', '您是否要删除当前地区？', function(b) {
				if (b) {
					progressLoad();
					$.post('${ctx}/sys/area/operate/delete', {
						id : node.id
					}, function(result) {
						if (result.success) {
							parent.$.messager.alert('提示', result.msg, 'info');
							treeGrid.treegrid('reload');
							parent.layout_west_tree.tree('reload');
						} else {
							parent.$.messager.alert('错误', result.msg, 'error');
						}
						progressClose();
					}, 'JSON');
				}
			});
		}
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
		<c:if test="${fn:contains(sessionInfo.resourceList, '/sys/area/addPage')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">新建</a>
		</c:if>
	</div>
</body>
</html>