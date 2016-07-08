<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	$(function() {
		$('#pid').combobox({
			url : '${ctx}/sys/area/getTopAreas',
			valueField : 'id',
			textField : 'name',
			panelHeight : 'auto',
			value : '${area.pid}'
		});

		$('#areaEditForm').form({
			url : '${ctx}/sys/area/operate/edit',
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
					parent.$.modalDialog.openner_treeGrid.treegrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});

		$("#description").val('${area.description}');

	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden; padding: 3px;">
		<form id="areaEditForm" method="post">
			<input name="id" type="hidden" value="${area.id}" />
			<%-- <input name="createUser" type="hidden" value="${area.createUser}" />
			<input name="createDate" type="hidden" value="${area.createDate}" /> --%>
			<table class="grid">
				<tr>
					<td>区域名称</td>
					<td>
						<input name="name" type="text" placeholder="请输入区域名称" class="easyui-validatebox" data-options="required:true" value="${area.name}" />
					</td>
				</tr>
				<tr>
					<td>排序</td>
					<td>
						<input name="seq" min="0" class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:true"
							value="${area.seq}" />
					</td>
				</tr>
				<tr>
					<td>上级地区</td>
					<td colspan="3">
						<select id="pid" name="pid" style="width: 200px; height: 29px;" data-options="editable:false"></select>
						<a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#pid').combobox('clear');">清空</a>
					</td>
				</tr>
				<tr>
					<td>描述</td>
					<td colspan="3">
						<textarea id="description" name="description" rows="" cols=""></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>