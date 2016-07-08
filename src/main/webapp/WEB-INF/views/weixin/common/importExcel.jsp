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
				}
				return isValid;
			},
			success : function(result) {
				progressClose();
				result = $.parseJSON(result);
				if (result.success) {
					$.messager.alert('提示', '导入成功', 'info');
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
	});
	function check() {
		var fileName = $('#uploadExcel').filebox('getValue');
		if (fileName == "") {
			$.messager.alert('提示', '请选择上传文件！', 'info');
			return false;
		} else {
			//对文件格式进行校验  
			var d1 = /\.[^\.]+$/.exec(fileName);
			if (d1 == ".xls") {
				return true;
			} else {
				$.messager.alert('提示', '请选择Excel2003格式文件！', 'info');
				$('#uploadExcel').filebox('setValue', '');
				return false;
			}
		}
	}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden; padding: 3px;">
		<form id="importForm" method="post" enctype="multipart/form-data">
			选择文件：
			<input id="uploadExcel" name="uploadExcel" class="easyui-filebox" style="width: 200px" data-options="prompt:'请选择文件...'">
			<br />

			<a href='${ctx}/common/download/${bizType}' id="download">下载模板</a>

		</form>
	</div>
</div>