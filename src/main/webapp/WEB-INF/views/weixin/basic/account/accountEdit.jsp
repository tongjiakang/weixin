<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
		$('#accountAddForm').form({
			url : '${ctx}/account/update',
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
		<form id="accountAddForm" method="post">
			<input name="id" type="hidden" value="${account.id}" />
			<table class="grid">
				<tr>
					<td>公众号名称:</td>
					<td>
						<input name="name" type="text" placeholder="请输入公众号名称" class="easyui-validatebox span2" data-options="required:true" value="${account.name}"
							style="width: 200px; height: 21px;">
					</td>
				</tr>
				<tr>
					<td>APPID:</td>
					<td>
						<input type="text" name="appid" placeholder="请输入appid" class="easyui-validatebox span2" data-options="required:true" value="${account.appid}" style="width: 200px; height: 21px;">
					</td>
				</tr>
				<tr>
					<td>识别码:</td>
					<td>
						<input type="text" name="appsecret" placeholder="请输入识别码" class="easyui-validatebox span2" data-options="required:true" value="${account.appsecret}" style="width: 200px; height: 21px;">
					</td>
				</tr>
				<tr>
					<td>token:</td>
					<td>
						<input type="text" name="token" placeholder="请输入token" class="easyui-validatebox span2" data-options="required:true" value="${account.token}" style="width: 200px; height: 21px;">
					</td>
				</tr>
				<tr>
					<td>加密串:</td>
					<td>
						<input type="text" name="encodingAESKey" placeholder="请输入加密串" class="easyui-validatebox span2" data-options="required:true" value="${account.encodingAESKey}" style="width: 360px; height: 21px;">
					</td>
				</tr>
				<tr>
					<td>接受消息地址:</td>
					<td>
						<input type="text" name="receiverAddress" placeholder="请输入接受消息地址" class="easyui-validatebox span2" data-options="required:true" value="${account.receiverAddress}" style="width: 360px; height: 21px;">
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>