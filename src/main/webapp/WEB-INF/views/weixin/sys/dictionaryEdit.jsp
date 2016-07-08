<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	$(function() {
		$("#type").combobox({
			panelHeight : '200',
			value : '${dictionary.type}',
			onChange : function(newValue, oldValue) {
				var url = '${ctx}/sys/dictionary/tree?type=' + newValue;
				$('#parentId').combotree("reload", url);
				$("[name='parentId']").val("");
			}
		});

		$('#parentId').combotree({
			url : '${ctx}/sys/dictionary/tree?type=' + $("#type").val(),
			parentField : 'pid',
			lines : true,
			panelWidth : '170',
			panelHeight : '200',
			value : '${dictionary.parentId}'
		});

		$('#dictionaryEditForm').form({
			url : '${ctx}/sys/dictionary/edit',
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
		<form id="dictionaryEditForm" method="post">
			<table class="grid">
				<tr>
					<td>键值</td>
					<td>
						<input name="id" type="hidden" value="${dictionary.id}">
						<input name="status" type="hidden" value="${dictionary.status}">
						<input name="value" type="text" class="easyui-validatebox" data-options="required:true" value="${dictionary.value}">
					</td>
				</tr>
				<tr>
					<td>标签名</td>
					<td>
						<input name="label" type="text" class="easyui-validatebox" data-options="required:true" value="${dictionary.label}">
					</td>
				</tr>
				<tr>
					<td>类型</td>

					<td>
						<select id="type" name="type" class="easyui-combobox" data-options="width:150,height:25,required:true">
							<c:forEach items="${dictionary.dictTypeList}" var="baseType">
								<option value="${baseType.value}">${baseType.label}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>上级字典</td>
					<td>
						<select id="parentId" name="parentId" class="easyui-combotree" data-options="width:150,height:25,required:false">
						</select>
					</td>
				</tr>
				<tr>
					<td>描述</td>
					<td>
						<input name="description" type="text" class="easyui-validatebox" data-options="required:true" value="${dictionary.description}">
					</td>
				</tr>
				<!-- 				<tr> -->
				<!-- 					<td>排序</td> -->
				<!-- 					<td> -->
				<%-- 						<input name="seq" min="0" type="text" class="easyui-validatebox" data-options="required:true" value="${dictionary.seq}"> --%>
				<!-- 					</td> -->
				<!-- 				</tr> -->
			</table>
		</form>
	</div>
</div>