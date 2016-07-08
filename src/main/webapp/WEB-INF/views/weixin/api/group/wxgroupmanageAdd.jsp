<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
		$('#groupAddForm').form({
			url : '${ctx}/wxgroupmanage/save',
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
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden; padding: 3px;">
		<form id="groupAddForm" method="post">
			<table class="grid">
				<input type="hidden" name="accountId" value="${param.accountId}"/>
				<tr>
					<td>分组名称:</td>
					<td>
						<input name="name" type="text" placeholder="请输入名称" class="easyui-validatebox span2" data-options="required:true" value=""
							style="width: 200px; height: 21px;">
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>