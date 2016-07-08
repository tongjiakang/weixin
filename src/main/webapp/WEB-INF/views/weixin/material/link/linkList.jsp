<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />
<title>外部链接管理</title>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		$('#orgId').combotree({
			url : '${ctx}/sys/organization/tree',
			parentField : 'pid',
			required : true,
			panelHeight : '200',
			panelWidth : '170',
			value : '${sessionInfo.orgId}'
		});
		dataGrid = $('#dataGrid').datagrid(
				{
					url : '${ctx}/link/dataGrid',
					striped: true,
                    rownumbers: true,
                    pagination: true,
                    singleSelect: false,
                    fit:true,
					idField : 'id',
					sortName : 'createDate',
					sortOrder : 'desc',
					pageSize : 50,
					pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
					columns : [ [{ field: 'ck',checkbox:'true'},
							{
								width : '150',
								title : '名称',
								field : 'name',
								align : "center"
							},
							{
								width : '450',
								title : '链接',
								field : 'url',
								align : "center"
							},{
								width : '150',
								title : '机构名称',
								field : 'orgName',
								align : "center"
							},{
								width : '100',
								title : '使用状态',
								field : 'useStatus',
								align : "center",
								formatter : function(value, row, index) {
									if(value=='1'){
										return "<font color='green'>启用</font>";
									}else if(value=='-1'){
										return "<font color='red'>停用</font>";
									}else{
										return "";
									}
								}
							},{
								width : '150',
								title : '创建时间',
								field : 'createDate',
								align : "center",
								formatter : function(value, row, index) {
									var unixTimestamp = new Date(value);
									return unixTimestamp.toLocaleString();
								}
							},
							{
								field : 'action',
								title : '操作',
								width : 150,
								align : "center",
								formatter : function(value, row, index) {
									var str = '&nbsp;';
									var useName;
									if(row.useStatus=='1'){
										useName="停用";
									}else{
										useName="启用";
									}
									if (row.isdefault != 0) {
											str += $.formatString(
															'<a href="javascript:void(0)" onclick="editFun(\'{0}\');" >修改</a>',
															row.id);

											str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
											str += $.formatString(
															'<a href="javascript:void(0)" onclick="changeStatus(\'{0}\',\'{1}\');" >'+useName+'</a>',
															row.id,row.useStatus);
									}
									return str;
								}
							} ] ],
					toolbar : '#toolbar'
				});
	});

	function editFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '修改外部链接',
			width : 500,
			height : 300,
			href : '${ctx}/link/updatePage?id=' + id,
			buttons : [ {
				text : '保存',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#linkAddForm');
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
		var ids = id;
		if(isEmpty(ids)){
			var rows = dataGrid.datagrid('getSelections');
			for(var i=0;i<rows.length;i++){
				ids += rows[i].id+',';
			}
		}
		parent.$.messager.confirm('询问', '您是否要删除选中链接？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/link/delete', {
					id : ids
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
						dataGrid.datagrid('reload');
					} else {
						parent.$.messager.alert('错误', result.msg, 'error');
					}
					progressClose();
				}, 'JSON');
			}
		});
	}
	
	
	function changeStatus(id,useStatus) {
		var useName;
		if(useStatus=='1'){
			useName="停用";
		}else{
			useName="启用";
		}
		parent.$.messager.confirm('询问', '您是否要'+useName+'该素材？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/link/changeStatus', {
					id : id
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
						dataGrid.datagrid('reload');
					} else {
						parent.$.messager.alert('错误', result.msg, 'error');
					}
					progressClose();
				}, 'JSON');
			}
		});
	}


	function addFun() {
		parent.$.modalDialog({
			title : '新建外部链接',
			width : 500,
			height : 300,
			href : '${ctx}/link/addPage',
			buttons : [ {
				text : '保存',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;
					var f = parent.$.modalDialog.handler.find('#linkAddForm');
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
					<th width="10%" align="right">机构名称：</th>
					<td width="20%" align="left">
						<input id="orgId" name="orgId" placeholder="请选择机构" />
					</td>
					<th width="10%" align="right">名称:</th>
					<td width="20%" align="left">
						<input name="name" placeholder="">
					</td>
					<td width="20%" align="left">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
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
		<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">新建</a>
	</div>
</body>
</html>