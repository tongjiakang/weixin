<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />
<title>分组管理</title>
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
							url : '${ctx}/wxgroupmanage/dataGrid',
							striped: true,
		                    rownumbers: true,
		                    singleSelect: false,
		                    queryParams: {
		                    	accountId: id
		                	},
		                    fit:true,
							idField : 'id',
							columns : [ [
									{
										width : '150',
										title : 'id',
										field : 'id',
										align : "center"
									},
									{
										width : '150',
										title : '名称',
										field : 'name',
										align : "center"
									},
									{
										width : '150',
										title : '分组人数',
										field : 'count',
										align : "center"
									},
									{
										field : 'action',
										title : '操作',
										width : 150,
										align : "center",
										formatter : function(value, row, index) {
											var str = '&nbsp;';
											if (row.isdefault != 0) {
													str += $.formatString(
																	'<a href="javascript:void(0)" onclick="editFun(\'{0}\',\'{1}\');" >修改</a>',
																	row.id,row.name);

													str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
													str += $.formatString(
																	'<a href="javascript:void(0)" onclick="deleteFun(\'{0}\');" >删除</a>',
																	row.id);
											}
											return str;
										}
									}] ],
								toolbar:"#toolbar"
						});
            }
		});
		
	});
	
	function editFun(id,name) {
		parent.$.modalDialog({
			title : '修改分组名称',
			width : 500,
			height : 320,
			href : '${ctx}/wxgroupmanage/updatePage?id=' + id+'&accountId='+$('#account').combobox('getValue')+'&name='+name,
			buttons : [ {
				text : '保存',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#groupUpdateForm');
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
		parent.$.messager.confirm('询问', '您是否要删除该分组？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/wxgroupmanage/delete', {
					id : id,
					accountId : $('#account').combobox('getValue')
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
			title : '新建分组',
			width : 500,
			height : 320,
			href : '${ctx}/wxgroupmanage/addPage?accountId='+$('#account').combobox('getValue'),
			buttons : [ {
				text : '保存',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;
					var f = parent.$.modalDialog.handler.find('#groupAddForm');
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
		</form>
	</div>
	<div data-options="region:'center',border:true">
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
	<div id="toolbar" style="display: none;">
		<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">新建分组</a>
	</div>
</body>
</html>