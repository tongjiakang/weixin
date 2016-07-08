<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	$(function() {
		$("#type").combobox({
			panelHeight : '200',
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
			panelHeight : '200'
		});

		$('#dictionaryAddForm').form({
			url : '${ctx}/sys/dictionary/add',
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
		<form id="dictionaryAddForm" method="post">
			<table class="grid">
				<tr>
					<td>键值</td>
					<td>
						<input name="value" type="text" placeholder="键值名称" class="easyui-validatebox span2" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td>标签</td>
					<td>
						<input name="label" type="text" placeholder="字典标签" class="easyui-validatebox span2" data-options="required:true">
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
						<input id="description" name="description" type="text" placeholder="内容描述" class="easyui-validatebox span2" data-options="required:true" value="">
					</td>
				</tr>
				<!-- 				<tr> -->
				<!-- 					<td>排序</td> -->
				<!-- 					<td> -->
				<!-- 						<input name="seq" min="0" type="text" placeholder="字典排序" class="easyui-validatebox span2" data-options="required:true" value="1"> -->
				<!-- 					</td> -->
				<!-- 				</tr> -->
			</table>
		</form>
	</div>
</div>