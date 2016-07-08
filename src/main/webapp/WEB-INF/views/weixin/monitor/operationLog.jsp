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
<title>操作日志</title>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		$('#orgId').combotree({
			url : '${ctx}/sys/organization/tree',
			parentField : 'pid',
			required : true,
			value : '${sessionInfo.orgId}'
		});
		dataGrid = $('#dataGrid').datagrid({
			url : '${ctx}' + '/log/operationLog/dataGrid',
			fit : true,
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'createDate',
			sortOrder : 'desc',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
			columns : [ [ {
				width : '200',
				title : '操作菜單',
				field : 'logTitle'
			}, {
				width : '150',
				title : '操作时间',
				field : 'createDate',
				align : "center",
				sortable : true,
				formatter : function(value, row, index) {
					var unixTimestamp = new Date(value);
					return unixTimestamp.toLocaleString();
				}
			}, {
				width : '100',
				title : '操作用戶',
				field : 'createUser',
				align : "center"
			}, {
				width : '100',
				title : '所属机构',
				field : 'orgName',
				align : "center"
			}, {
				width : '100',
				title : '操作用户IP',
				align : "center",
				field : 'remoteaddr'
			}, {
				width : '100',
				title : '提交方式',
				field : 'method',
				align : "center"
			}, {
				width : '60',
				title : '状态',
				field : 'logType',
				align : "center",
				formatter : function(value, row, index) {
					switch (value) {
					case "1":
						return '正常';
					case "2":
						return '异常';
					}
				}
			}, {
				width : '350',
				title : '请求URL',
				field : 'requesturi'
			} ] ],
			toolbar : '#toolbar'
		});
	});

	function searchFun() {
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		$('#searchForm input').val('');
		dataGrid.datagrid('load', {});
	}
	function exportFun() {
		var grid = $('#dataGrid');
		var options = grid.datagrid('getPager').data("pagination").options;
		$("#page").val(options.pageNumber);
		$("#pageSize").val(options.pageSize);
		$("#sort").val("createDate");
		$("#order").val("desc");
		var form = $('#searchForm');
		form.attr("action", "${ctx}/log/operationLog/export");
		form.attr(target = "_blank");
		form.submit();
	}
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" style="width: 100%; margin-top: 10px; margin-bottom: 10px; overflow: hidden; background-color: #fff">
		<form id="searchForm">
			<table style="width: 90%; height: 100%; text-align: center;">
				<tr>

					<th width="10%" align="right">操作用户：</th>
					<td width="20%" align="left">
						<input name="createUser" placeholder="请输入操作用户" />
					</td>
					<th width="10%" align="right">操作人IP：</th>
					<td width="20%" align="left">
						<input name="remoteaddr" placeholder="请输入操作用户IP" />
					</td>
					<th width="10%" align="right">机构名称：</th>
					<td width="20%" align="left">
						<input id="orgId" name="orgId" placeholder="请选择机构" />
					</td>
				</tr>
				<tr>
					<th width="10%" align="right">操作时间：</th>
					<td width="60%" align="left" colspan="4">
						<input name="createDateStart" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />
						至
						<input name="createDateEnd" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />
					<td width="20%" align="left">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
					</td>
				</tr>
			</table>
			<div>
				<input id="page" name="page" type="hidden" />
				<input id="pageSize" name="pageSize" type="hidden" />
				<input id="sort" name="sort" type="hidden" />
				<input id="order" name="order" type="hidden" />
			</div>
		</form>
	</div>
	<div data-options="region:'center',border:true,title:'操作日志列表'">
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
	<div id="toolbar" style="display: none;">
		<a onclick="exportFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-export'">导出</a>
	</div>
</body>
</html>