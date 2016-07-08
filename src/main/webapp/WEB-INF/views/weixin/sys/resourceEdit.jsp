<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	$(function() {
		$('#pid').combotree({
			url : '${ctx}/sys/resource/allTree?flag=false',
			parentField : 'pid',
			lines : true,
			panelHeight : 'auto',
			value : '${resource.pid}',
			onLoadSuccess : function() {
				$("#pid").combotree('tree').tree("collapseAll");
			}
		});

		$('#resourceEditForm').form({
			url : '${ctx}/sys/resource/edit',
			onSubmit : function() {
				progressLoad();
				var isValid = $(this).form('validate');
				if (!isValid) {
					progressClose();
				}
				return isValid;
			},
			success : function(result) {
				progressClose();
				result = $.parseJSON(result);
				if (result.success) {
					parent.$.modalDialog.openner_treeGrid.treegrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为resource.jsp页面预定义好了
					parent.layout_west_tree.tree('reload');
					parent.$.modalDialog.handler.dialog('close');
				}
			}
		});

		$("#iconCls").val('${resource.iconCls}');
		$("#resourcetype").val('${resource.resourcetype}');

	});
</script>
<div style="padding: 3px;">
	<form id="resourceEditForm" method="post">
		<table class="grid">
			<tr>
				<td>资源名称</td>
				<td>
					<input name="id" type="hidden" value="${resource.id}">
					<input id="status" name="status" type="hidden" value="${resource.status}">
					<input name="name" type="text" placeholder="请输入资源名称" value="${resource.name}" class="easyui-validatebox span2" data-options="required:true">
				</td>
				<td>资源类型</td>
				<td>
					<select id="resourcetype" name="resourcetype" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
						<option value="0">菜单</option>
						<option value="3">页面</option>
						<option value="1">按钮</option>
						<option value="2">链接</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>资源路径</td>
				<td>
					<input name="url" type="text" value="${resource.url}" placeholder="请输入资源路径" class="easyui-validatebox span2">
				</td>
				<td>排序</td>
				<td>
					<input name="seq" min="0" value="${resource.seq}" class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required"
						data-options="editable:true">
				</td>
			</tr>
			<tr>
				<td>菜单图标</td>
				<td>
					<select id="iconCls" name="iconCls" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
						<option value="icon-company">父级菜单</option>
						<option value="icon-folder">子级菜单</option>
						<option value="icon-page">页面</option>
						<option value="icon-btn">按钮</option>
						<option value="icon-link">链接</option>
					</select>
				</td>
				<td>状态</td>
				<td>
					<select class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'" disabled="disabled">
						<c:forEach items="${resource.statusList}" var="baseType">
							<option value="${baseType.value}" ${resource.status == baseType.value ? 'selected':''}>${baseType.label}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>上级资源</td>
				<td colspan="3">
					<select id="pid" name="pid" style="width: 200px; height: 29px;" data-options="editable:false"></select>
					<a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#pid').combotree('clear');">清空</a>
				</td>
			</tr>
		</table>
	</form>
</div>
