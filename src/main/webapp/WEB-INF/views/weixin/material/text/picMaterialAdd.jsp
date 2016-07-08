<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />
<script type="text/javascript">
	function save() {
		var f = $('#picMaterialAddForm');
		f.attr("action", "${ctx}/picMaterial/save");
		f.submit();
	}
	$(function() {
		$('#orgId').combotree({
            url: '${ctx}/sys/organization/tree',
            parentField: 'pid',
            required: true,
            value:'${sessionInfo.orgId}'
        });
		$('#picMaterialAddForm').form({
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
					self.parent.closeTab('新建图文', "${param.title}");
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});

	});
</script>
</head>
<body>
	<div data-options="fit:true,border:false" class="divContent1">
		<div data-options="fit:true,border:false" class="divContent2">
			<form id="picMaterialAddForm" method="post">
				<table class="grid">
					<tr>
						<td class="tdTitle">机构</td>
						<td class="tdContent2"><input id="orgId" name="orgId" placeholder="请选择机构" data-options="required:true"/></td>
					</tr>
					<tr>
						<td class="tdTitle">图文名称</td>
						<td class="tdContent2"><input name="templateName" type="text" maxlength="20" placeholder="请输入图文名称" class="easyui-validatebox"
							missingMessage="图文名称不能为空" data-options="required:true"></td>
					</tr>
					<tr>
						<td class="tdTitle">模板类型</td>
						<td class="tdContent2"><select name="type" id="type">
								<option value="common">普通模板</option>
								<option value="cl">超链接模板</option>
						</select></td>
					</tr>
				</table>
			</form>
			<div class="divBtn">
				<a onclick="save()" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true">保存</a>
			</div>
		</div>
	</div>
</body>
</html>