<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />
<c:if test="${fn:contains(sessionInfo.resourceList, '/sys/dictionary/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/sys/dictionary/addSame')}">
	<script type="text/javascript">
		$.canAddSame = true;
	</script>
</c:if>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据字典</title>
<script type="text/javascript">
	var dataGrid;
	$(function() {

		dataGrid = $('#dataGrid')
				.datagrid(
						{
							url : '${ctx}/sys/dictionary/dataGrid',
							fit : true,
							striped : true,
							rownumbers : true,
							pagination : true,
							idField : 'id',
							sortName : 'type',
							sortOrder : 'asc',
							pageSize : 50,
							pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
							columns : [ [
									{
										width : '150',
										title : '键值',
										field : 'value',
										align : 'center'
									},
									{
										width : '150',
										title : '标签名',
										field : 'label',
										align : 'center'
									},
									{
										width : '150',
										title : '类型',
										field : 'typeLabel',
										align : 'center'
									},
									{
										width : '200',
										title : '描述',
										field : 'description',
										align : 'center'
									},
									{
										width : '150',
										title : '父节点',
										field : 'parentLabel',
										align : 'center'
									},
// 									{
// 										width : '100',
// 										title : '排序号',
// 										field : 'seq',
// 										align : 'center'
// 									},
									{
										width : '100',
										title : '状态',
										field : 'statusLabel',
										align : 'center',
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
										width : '150',
										align : 'center',
										formatter : function(value, row, index) {
											var str = '';
											if (row.isdefault != 0) {
												if ($.canEdit) {
													str += $
															.formatString(
																	'<a href="javascript:void(0)" onclick="editFun(\'{0}\');" >修改</a>',
																	row.id);
												}
												if ($.canAddSame) {
													str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
													str += $
															.formatString(
																	'<a href="javascript:void(0)" onclick="addSameFun(\'{0}\');" >追加同类</a>',
																	row.id);
												}
											}
											return str;
										}
									} ] ],
							toolbar : '#toolbar'
						});
	});

	function addFun() {
		parent.$.modalDialog({
			title : '新建数据字典',
			width : 500,
			height : 300,
			href : '${ctx}/sys/dictionary/addPage',
			buttons : [ {
				text : '保存',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#dictionaryAddForm');
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

	function addSameFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '追加同类型数据字典',
			width : 500,
			height : 300,
			href : '${ctx}/sys/dictionary/addPage?id=' + id,
			buttons : [ {
				text : '保存',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#dictionaryAddForm');
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

	function changeFun(method) {
		var rows = dataGrid.datagrid('getSelections');
		if (rows[0] == undefined) {
			parent.$.messager.alert('提示', "请选择要操作的数据", 'info');
			return;
		}
		var ids = '';
		for (var i = 0; i < rows.length; i++) {
			ids += rows[i].id + ",";
		}
		parent.$.messager.confirm('询问', '您是否要操作当前字典？', function(b) {
			if (b) {
				var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
				progressLoad();
				$.post('${ctx}/sys/dictionary/change', {
					ids : ids,
					method : method
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
						dataGrid.datagrid('reload');
						dataGrid.datagrid('clearSelections');
					}
					progressClose();
				}, 'JSON');
			}
		});
	}

	function deleteFun() {
		var rows = dataGrid.datagrid('getSelections');
		if (rows[0] == undefined) {
			parent.$.messager.alert('提示', "请选择要操作的数据", 'info');
			return;
		}
		var ids = '';
		for (var i = 0; i < rows.length; i++) {
			ids += rows[i].id + ",";
		}
		parent.$.messager.confirm('询问', '您是否要删除所选字典？', function(b) {
			if (b) {
				var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
				progressLoad();
				$.post('${ctx}/sys/dictionary/delete', {
					ids : ids
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
						dataGrid.datagrid('reload');
						dataGrid.datagrid('clearSelections'); 
					}
					progressClose();
				}, 'JSON');
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
			title : '修改数据字典',
			width : 500,
			height : 300,
			href : '${ctx}/sys/dictionary/editPage?id=' + id,
			buttons : [ {
				text : '保存',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#dictionaryEditForm');
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
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" class="divContent0">
		<form id="searchForm">
			<table class="tabContent0">
				<tr>
					<th width="10%" align="right">类型:</th>
					<td width="20%" align="left">
						<select name="type" class="easyui-combobox" data-options="width:150,height:20,editable:false,panelHeight:'auto'">
							<option value="" selected="selected">全部</option>
							<c:forEach items="${dictionary.dictTypeList}" var="baseType">
								<option value="${baseType.value}">${baseType.label}</option>
							</c:forEach>
						</select>
					</td>
					<th width="10%" align="right">描述:</th>
					<td width="20%" align="left">
						<input name="description" placeholder="字典描述" />
					</td>
					<td>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:true,title:'数据字典'">
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/sys/dictionary/addPage')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">新建</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/sys/dictionary/change')}">
			<a onclick="changeFun('start');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-start'">启用</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/sys/dictionary/change')}">
			<a onclick="changeFun('stop');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-stop'">停用</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/sys/dictionary/delete')}">
			<a onclick="deleteFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-del'">删除</a>
		</c:if>
	</div>
</body>
</html>