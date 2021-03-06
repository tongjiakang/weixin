<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
	$(function() {

		$('#editUserPwdForm').form({
			url : '${ctx}/sys/user/editUserPwd',
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
					
					parent.$.messager.alert('提示', result.msg, 'info', function() {
								$.post('${ctx}/console/logout', function (result) {
		                        	if (result.success) {
		                            	progressClose();
		                            	window.location.href = '${ctx}/console/index';
		                        	}
                   				}, 'json');
							});
					$(".panel-tool-close").css("display","none");
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
		<c:if test="${sessionInfo.loginname == null}">
			<div>登录已超时，请重新登录.</div>
			<script type="text/javascript" charset="utf-8">
				try {
					window.location.href='${ctx}/console/index';
				} catch (e) {
				}
			</script>
		</c:if>
		<c:if test="${sessionInfo.loginname != null}">
		 <div style="padding:20px 30px 30px 10px">
			<form id="editUserPwdForm" method="post">
				<table cellpadding="5">
					<tr>
						<th>登录名</th>
						<td>${sessionInfo.loginname}</td>
					</tr>
					<tr>
						<th>原密码</th>
						<td><input name="oldPwd" type="password" placeholder="请输入原密码" class="easyui-validatebox" data-options="required:true"></td>
					</tr>
					<tr>
						<th>新密码</th>
						<td><input name="pwd" type="password" placeholder="请输入新密码" class="easyui-validatebox" data-options="required:true"></td>
					</tr>
					<tr>
						<th>重复密码</th>
						<td><input name="rePwd" type="password" placeholder="请再次输入新密码" class="easyui-validatebox" data-options="required:true,validType:'eqPwd[\'#editUserPwdForm input[name=pwd]\']'"></td>
					</tr>
				</table>
			</form>
			</div>
		</c:if>
	</div>
</div>