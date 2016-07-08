<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />
<title>自定义菜单管理</title>
<script type="text/javascript">
	var treeGrid;
	$(function() {
		$('#account').combobox({
			url : '${ctx}/account/getAcounts',
			valueField : 'id',
			textField : 'text',
			onSelect:function(record){  
				$('#treeGrid').treegrid('load',{
					account: $('#account').combobox('getValue')
				});
            },
			onLoadSuccess:function(){
				var id = $("#account").combobox("getData")[0].id;
				$('#account').combobox('setValue', id);
				treeGrid = $('#treeGrid').treegrid(
						{
							url : '${ctx}/customMenu/treeGrid',
							idField : 'id',
							treeField : 'menuName',
							parentField : 'parentMenu',
							 queryParams: {
			                    	account: id
			                	},
							fit : true,
							fitColumns : false,
							border : false,
							columns : [ [
									{
										field : 'menuName',
										title : '菜单名称',
										width : 150
									},
									{
										field : 'menuType',
										title : '菜单类型',
										width : 200,
										formatter:function(value, row, index) {
											if(value=="link"){
												return "网页链接类";
											}else if(value=="msg"){
												return "消息触发类";
											}else{
												return "";
											}
										}
									},
									{
										field : 'order',
										title : '顺序',
										width : 50,
										align : 'center'
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
							onLoadSuccess : function() {
//		 						$('#treeGrid').treegrid('collapseAll')
							},
							toolbar : '#toolbar'
						});
			}
		});
		
	});

	function editFun(id) {
		parent.$.modalDialog({
			title : '修改菜单',
			width : 500,
			height : 380,
			href : '${ctx}/customMenu/updatePage?id=' + id,
			buttons : [ {
				text : '保存',
				handler : function() {
					parent.$.modalDialog.openner_treeGrid = treeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#customMenuEditForm');
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
		parent.$.messager.confirm('询问', '您是否要删除菜单？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/customMenu/delete', {
					id : id
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
						treeGrid.treegrid('reload');
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
			title : '新建菜单',
			width : 500,
			height : 380,
			href : '${ctx}/customMenu/addPage',
			buttons : [ {
				text : '保存',
				handler : function() {
					parent.$.modalDialog.openner_treeGrid = treeGrid;
					var f = parent.$.modalDialog.handler.find('#customMenuAddForm');
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
	
	function syncMenu(){
		var acountId = $("#account").combobox('getValue');
		$.post('${ctx}/customMenu/syncMenu', {
			acountId : acountId
		}, function(result) {
			if (result.success) {
				parent.$.messager.alert('提示', result.msg, 'info');
			} else {
				parent.$.messager.alert('错误', result.msg, 'error');
			}
			progressClose();
		}, 'JSON');
	}
	
	function searchFun() {
		treeGrid.treeGrid('load', $.serializeObject($('#searchForm')));
	}

	function cleanFun() {
		$('#searchForm input').val('');
		treeGrid.treeGrid('load', {});
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
						<input name="account" id="account" class="easyui-combobox"
						 editable="false" />
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
		<table id="treeGrid" data-options="fit:true,border:false"></table>
	</div>
	<div id="toolbar" style="display: none;">
		<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">新建</a>
		<a onclick="syncMenu();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-export'">同步菜单</a>
	</div>
</body>
</html>