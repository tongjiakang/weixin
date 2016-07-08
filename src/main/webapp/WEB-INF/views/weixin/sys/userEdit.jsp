<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	$(function() {

		$('#orgId').combotree({
			url : '${ctx}/sys/organization/tree',
			parentField : 'pid',
			lines : true,
			panelHeight : '200',
			panelWidth : '170',
			value : '${user.orgId}'
		});


		$('#userEditForm').form({
			url : '${ctx}/sys/user/edit',
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
		$("#sex").val('${user.sex}');
	});


	$.extend($.fn.validatebox.defaults.rules, {
		//移动手机号码验证
		mobile : {//value值为文本框中的值
			validator : function(value) {
				var reg = /^1[3|4|5|8|7|9]\d{9}$/;
				return reg.test(value);
			},
			message : '输入的手机号码格式不准确.'
		}
	})
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden; padding: 3px;">
		<form id="userEditForm" method="post">
			<table class="grid">
				<tr>
					<td>登录名</td>
					<td>
						<input name="id" type="hidden" value="${user.id}">
						<input name="loginname" type="text" placeholder="请输入登录名称" class="easyui-validatebox" data-options="required:true" disabled="disabled"
							value="${user.loginname}">
					</td>
					<td>姓名</td>
					<td>
						<input name="userName" type="text" placeholder="请输入姓名" class="easyui-validatebox" data-options="required:true" value="${user.userName}">
					</td>
				</tr>
				<tr>
					<td>密码</td>
					<td>
						<input type="password" name="password" disabled="disabled" value="${user.password}" />
					</td>
					<td>性别</td>
					<td>
						<select name="sex" id="sex" class="easyui-combobox" data-options="width:150,height:25,editable:false,panelHeight:'auto'">
							<option value="0">男</option>
							<option value="1">女</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>年龄</td>
					<td>
						<input type="text" name="age" value="${user.age}" class="easyui-numberbox" />
					</td>
					<td>电话</td>
					<td>
						<input type="text" name="phone" class="easyui-numberbox" value="${user.phone}" validtype="mobile" />
					</td>
				</tr>
				<tr>
					<td>部门</td>
					<td>
						<select id="orgId" name="orgId" style="width: 150px; height: 25px;" class="easyui-validatebox" data-options="required:true"></select>
					</td>
					<td>状态 </td>
					<td>
						<select id="status" name="status" class="easyui-combobox" data-options="width:150,height:25,editable:false,panelHeight:'auto'">
							<c:forEach items="${user.statusList}" var="baseType">
								<option value="${baseType.value}" ${resource.status == baseType.value ? 'selected':''}>${baseType.label}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>