<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />
<title>图文消息</title>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		$('#orgId').combotree({
            url: '${ctx}/sys/organization/tree',
            parentField: 'pid',
            required: true,
            value:'${sessionInfo.orgId}'
        });
		dataGrid = $('#dataGrid')
				.datagrid(
						{
							url : '${ctx}' + '/picMaterial/dataGrid',
							striped : true,
							rownumbers : true,
							pagination : true,
							singleSelect : true,
							idField : 'id',
							sortName : 'id',
							sortOrder : 'desc',
							queryParams: {
								orgId:'${sessionInfo.orgId}'
							},
							pageSize : 50,
							pageList : [ 10, 20, 30, 40, 50, 100, 200, 300,
									400, 500 ],
							columns : [ [
									{
										width : '50',
										title : 'id',
										field : 'id',
										hidden : true
									},{
										width : '300',
										title : '区域名称',
										field : 'orgName'
									},
									{
										width : '300',
										title : '图文名称',
										field : 'templateName'
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
									},
									{
										width : '240',
										title : '创建时间',
										field : 'createDate',
										align : 'center',
										formatter : function(value, row, index) {
											var unixTimestamp = new Date(value);
											return unixTimestamp
													.toLocaleString();
										}
									},
									{
										field : 'action',
										title : '操作',
										width : 250,
										align : 'center',
										formatter : function(value, row, index) {
											var useName;
											if(row.useStatus=='1'){
												useName="停用";
											}else{
												useName="启用";
											}
											var str = '&nbsp;';
											str += $
													.formatString(
															'<a href="javascript:void(0)" onclick="addPicItem(\'{0}\');" >添加图文</a>',
															row.id);
											str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
											str += $
													.formatString(
															'<a href="javascript:void(0)" onclick="editPicItem(\'{0}\');" >编辑图文</a>',
															row.id);
											str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
											str += $
													.formatString(
															'<a href="javascript:void(0)" onclick="changeStatus(\'{0}\',\'{1}\');" >'+useName+'</a>',
															row.id,row.useStatus);
											return str;
										}
									} ] ],
							toolbar : '#toolbar'
						});
	});
	
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
				$.post('${ctx}/picMaterial/changeStatus', {
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


	function addPicItem(templateId) {
		self.parent.addTab({
			url : "${ctx}/picMaterial/addArticle?templateId="+templateId+"&change=" + Date.parse(new Date())
					+ "&title=${param.title}",
			title : "添加图文",
			iconCls : "icon-add"
		});
	}
	
	function addFun() {
		self.parent.addTab({
			url : "${ctx}/picMaterial/addPage"+"?change=" + Date.parse(new Date())
					+ "&title=${param.title}",
			title : "新建图文",
			iconCls : "icon-add"
		});
	}
	function editFun() {
		var id;
		var select =$('#dataGrid').datagrid("getSelections");
		if(select.length==0){
			parent.$.messager.alert('提示',"请选择一行数据进行编辑", 'info');
			return;
		}else{
			id = select[0].id;
		}
		self.parent.addTab({
			url : "${ctx}/picMaterial/editPage"+"?change=" + Date.parse(new Date())
					+ "&title=${param.title}&id="+id,
			title : "图文编辑",
			iconCls : "icon-edit"
		});
	}
	function editPicItem(templateId){
		self.parent.addTab({
			url : "${ctx}/picMaterial?goMessage&templateId="+templateId+"&change=" + Date.parse(new Date())
					+ "&title=${param.title}",
			title : "编辑图文",
			iconCls : "icon-edit"
		});
	}
	function deleteFun(id) {
		parent.$.messager.confirm('询问', '您是否要删除当前图文模板？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/picMaterial/deleteMaterial', {
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
    function searchFun() {
        dataGrid.datagrid('load',$.serializeObject($('#searchForm')));
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
					<th width="10%" align="right">机构：</th>
					<td width="20%" align="left" colspan="4"><input id="orgId" name="orgId" placeholder="请选择机构" /></td>
					<th width="10%" align="right">图文名称：</th>
					<td width="20%" align="left" colspan="4"><input name="templateName" placeholder=""></td>
					<td width="20%" align="left"><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true"
						onclick="searchFun();">查询</a> <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true"
						onclick="cleanFun();">清空</a></td>
				</tr>
			</table>
			<input id="page" name="page" type="hidden" /> <input id="pageSize" name="pageSize" type="hidden" /> <input id="sort" name="sort" type="hidden" />
			<input id="order" name="order" type="hidden" />
		</form>
	</div>
	<div data-options="region:'center',border:true">
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
	<div id="toolbar" style="display: none;">
		<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">图文录入</a> <a
			onclick="editFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'">图文编辑</a> 
	</div>
</body>
</html>