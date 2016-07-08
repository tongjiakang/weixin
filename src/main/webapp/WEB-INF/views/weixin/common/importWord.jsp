<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	$(function() {
		$('#importForm').form({
			url : '${ctx}/biz/${bizType}/import',
			onSubmit : function() {
				progressLoad();
				var isValid = $(this).form('validate');
				if (!isValid || !check()) {
					progressClose();
					isValid = false;
				}
				return isValid;
			},
			success : function(result) {
				progressClose();
				result = $.parseJSON(result);
				if (result.success) {
					$.messager.alert('提示', '上传成功', 'info');
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
	});
	function check() {
		var fileName = $('#uploadWord').filebox('getValue');
		if (fileName == "") {
			$.messager.alert('提示', '请选择上传文件！', 'info');
			return false;
		} else {
			//对文件格式进行校验  
			var d1 = /\.[^\.]+$/.exec(fileName);
			if (d1 == ".doc") {
				return true;
			} else {
				$.messager.alert('提示', '请选择Word2003格式文件！', 'info');
				$('#uploadWord').filebox('setValue', '');
				return false;
			}
		}
	}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden; padding: 3px;">
		<form id="importForm" method="post" enctype="multipart/form-data">
			<table class="grid">
				<tr>
					<td class="tdTitle">公司名称：</td>
					<td class="tdContent2">
						<input name="companyName" type="text" placeholder="请输入公司名称" class="easyui-validatebox" data-options="required:true" />
					</td>
				</tr>
				<tr>
				<td class="tdTitle">选择文件：</td>
					<td class="tdContent2">
						<input id="uploadWord" name="uploadWord" class="easyui-filebox" style="width: 200px" data-options="prompt:'请选择文件...'">
					</td>
				</tr>
				<tr>
					<td class="tdTitle"></td>
					<td class="tdContent2"><a href='${ctx}/common/download/${bizType}' id="download">下载模板</a></td>
				</tr>
			</table>
		</form>
	</div>
</div>