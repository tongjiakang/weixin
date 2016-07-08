<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	$(function() {

		$('#orgId').combotree({
			url : '${ctx}/sys/organization/loadRelatedOrgs',
			parentField : 'pid',
			lines : true,
			panelHeight : 'auto'
		});

		$('#roleAddForm').form({
			url : '${ctx}/sys/role/add',
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
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden; padding: 3px;">
		<form id="roleAddForm" method="post">
			<table class="grid">
				<tr>
					<td>角色名称</td>
					<td>
						<input name="name" type="text" placeholder="请输入角色名称" class="easyui-validatebox span2" data-options="required:true" value=""
							style="width: 200px; height: 29px;">
					</td>
				</tr>
				<tr>
					<td>角色类型</td>
					<td>
						<select id="roleType" name="type" style="width: 200px; height: 29px;" required="required" data-options="editable:false">
							<c:forEach items="${roleTypes}" var="json">
								<option value="${json.value}">${json.text}</option>
							</c:forEach>
						</select>
						<a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#roleType').combobox('clear');">清空</a>
					</td>
				</tr>
				<tr>
					<td>组织机构</td>
					<td colspan="3">
						<select id="orgId" name="orgId" style="width: 200px; height: 29px;" required="required" data-options="editable:false"></select>
						<a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#pid').combotree('clear');">清空</a>
					</td>
				</tr>
				<tr>
					<td>排序</td>
					<td>
						<input name="seq" min="0" value="0" class="easyui-numberspinner" style="width: 200px; height: 29px;" required="required" data-options="editable:true">
					</td>
				</tr>
				<tr>
					<td>描述</td>
					<td>
						<textarea name="description" rows="" cols="" style="width: 200px; height: 39px;"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>