<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />
<title>上传文件素材管理</title>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		$('#type').combobox({
			onSelect:function(record){  
				$('#dataGrid').datagrid('load',{
					accountId: $('#account').combobox('getValue'),
					type:$('#type').combobox('getValue')
				});
            }
		});
		$('#account').combobox({
			url : '${ctx}/account/getAcounts',
			valueField : 'id',
			textField : 'text',
			onSelect:function(record){  
				$('#dataGrid').datagrid('load',{
					accountId: $('#account').combobox('getValue'),
					type:$('#type').combobox('getValue')
				});
            },
			onLoadSuccess:function(){
				var id = $("#account").combobox("getData")[0].id;
				var type = $("#type").combobox("getValue");
				$('#account').combobox('setValue', id);
				dataGrid = $('#dataGrid').datagrid(
						{
							url : '${ctx}/uploadFileMaterial/dataGrid',
							striped: true,
		                    rownumbers: true,
		                    pagination: true,
		                    singleSelect: false,
		                    fit:true,
		                    queryParams: {
		                    	accountId: id,
		                    	type:type
		                    	
		                	},
							idField : 'id',
							sortName : 'createDate',
							sortOrder : 'desc',
							pageSize : 50,
							pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
							columns : [ [
									{
										width : '150',
										title : '素材名称',
										field : 'name',
										align : "center"
									},
									{
										width : '350',
										title : '链接地址',
										field : 'url',
										align : "center",
										formatter : function(value, row, index) {
											if(value==undefined){
												return "";
											}
											return "<a  target='_blank' href='"+value+"'>"+value+"</a>";
										}
									},{
										width : '150',
										title : '更新时间',
										field : 'update_time',
										align : "center",
										formatter : function(value, row, index) {
											var unixTimestamp = new Date(value*1000);
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
											if (row.isdefault != 0) {
													str += $.formatString(
																	'<a href="javascript:void(0)" onclick="editFun(\'{0}\');" >修改</a>',
																	row.id);

													str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
													str += $.formatString(
																	'<a href="javascript:void(0)" onclick="deleteFun(\'{0}\');" >删除</a>',
																	row.media_id);
											}
											return str;
										}
									} ] ],
							toolbar : '#toolbar'
						});
			}
		});
		
	});

	function editFun(id) {
		parent.$.modalDialog({
			title : '修改关键字',
			width : 500,
			height : 320,
			href : '${ctx}/keyWord/updatePage?id=' + id,
			buttons : [ {
				text : '保存',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#keyWordEditForm');
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

	function deleteFun(media_id) {
		parent.$.messager.confirm('询问', '您是否要删除改素材？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/uploadFileMaterial/delete', {
					media_id : media_id,
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
			title : '上传文件素材',
			width : 500,
			height : 320,
			href : '${ctx}/uploadFileMaterial/addPage',
			buttons : [ {
				text : '保存',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;
					var f = parent.$.modalDialog.handler.find('#uploadFileMaterialAddForm');
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
					<th width="10%" align="right">公众号：</th>
					<td width="20%" align="left">
						<input name="accountId" id="account" class="easyui-combobox"
						 editable="false" />
					</td>
					<th width="10%" align="right">素材类型:</th>
					<td width="20%" align="left">
						<select name="type" id="type" class="easyui-combobox" data-options="editable:false,width:150,height:25,required:true">
							<option value="image">图片</option>
							<option value="voice">语音</option>
							<option value="video">视频</option>
							<option value="thumb">缩略图</option>
						</select>
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