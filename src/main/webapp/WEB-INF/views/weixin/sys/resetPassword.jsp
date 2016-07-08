<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
	$(function() {

		$('#resetPasswordForm').form({
			url : '${ctx}/sys/user/resetPassword',
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
					parent.$.messager.alert('提示', result.msg, 'info');
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
			<form id="resetPasswordForm" method="post">
				<table>
					<tr>
						<th>登录名</th>
						<td>${user.loginname}</td>
						<input type="hidden" name="loginname" value="${user.loginname}">
					</tr>
					<tr>
						<th>新密码</th>
						<td><input name="pwd" type="password" placeholder="请输入新密码" class="easyui-validatebox" data-options="required:true"></td>
					</tr>
					<tr>
						<th>重复密码</th>
						<td><input name="rePwd" type="password" placeholder="请再次输入新密码" class="easyui-validatebox" data-options="required:true,validType:'eqPwd[\'#resetPasswordForm input[name=pwd]\']'"></td>
					</tr>
				</table>
			</form>
	</div>
</div>