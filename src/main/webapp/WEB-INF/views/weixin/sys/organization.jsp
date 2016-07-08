<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />
<title>组织机构</title>
<script type="text/javascript">
	var treeGrid;
	$(function() {
		treeGrid = $('#treeGrid').treegrid(
				{
					url : '${ctx}/sys/organization/treeGrid',
					idField : 'id',
					treeField : 'orgName',
					parentField : 'pid',
					fit : true,
					fitColumns : false,
					border : false,
					frozenColumns : [ [ {
						title : 'id',
						field : 'id',
						width : 40,
						hidden : true
					} ] ],
					columns : [ [
							{
								field : 'orgName',
								title : '组织机构',
								width : 180
							},
							{
								field : 'pid',
								title : '上级机构ID',
								width : 150,
								hidden : true
							},
							{
								width : '80',
								title : '创建人',
								field : 'createUser',
								align : "center",
								sortable : true
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
								align : "center",
								sortable : true
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
								width : 80,
								align : "center",
								formatter : function(value, row, index) {
									var str = '&nbsp;';
										str += $.formatString(
												'<a href="javascript:void(0)" onclick="editFun(\'{0}\');" >修改</a>',
												row.id);
										str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
										str += $.formatString(
												'<a href="javascript:void(0)" onclick="deleteFun(\'{0}\');" >删除</a>',
												row.id);
									return str;
								}
							} ] ],
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
				title : '修改组织机构',
				width : 500,
				height : 300,
				href : '${ctx}/sys/organization/editPage?id=' + node.id,
				buttons : [ {
					text : '保存',
					handler : function() {
						parent.$.modalDialog.openner_treeGrid = treeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
						var f = parent.$.modalDialog.handler.find('#organizationEditForm');
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
					$.post('${ctx}/sys/organization/delete', {
						id : node.id
					}, function(result) {
						if (result.success) {
							parent.$.messager.alert('提示', result.msg, 'info');
							treeGrid.treegrid('reload');
						} else {
							parent.$.messager.alert('提示', result.msg, 'info');
						}
						progressClose();
					}, 'JSON');
				}
			});
		}
	}

	function addFun() {
		parent.$.modalDialog({
			title : '新建组织机构',
			width : 500,
			height : 300,
			href : '${ctx}/sys/organization/addPage',
			buttons : [ {
				text : '保存',
				handler : function() {
					parent.$.modalDialog.openner_treeGrid = treeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#organizationAddForm');
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
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'center',border:false" style="overflow: hidden;">
			<table id="treeGrid"></table>
		</div>

		<div id="toolbar" style="display: none;">
				<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">新建</a>
		</div>
	</div>
</body>
</html>