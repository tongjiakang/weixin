<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	$('#uploadFileMaterialAddForm').form({
		url : '${ctx}/uploadFileMaterial/save',
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
				parent.$.messager.alert('提示', result.msg, 'info');
			} else {
				parent.$.messager.alert('错误', result.msg, 'error');
			}
		}
	});
	
	$(function(){
		$('#account').combobox({
			url : '${ctx}/account/getAcounts',
			valueField : 'id',
			textField : 'text'
		});
		
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden; padding: 3px;">
		<form id="uploadFileMaterialAddForm" method="post" enctype="multipart/form-data">
			<table class="grid">
				<tr>
					<td>公众号:</td>
					<td><input name="accountId" id="account" class="easyui-combobox"
						data-options="required:true" editable="false" /></td>
				</tr>
				<tr>
					<td>文件类型:</td>
					<td><select name="type" id="type" class="easyui-combobox" data-options="editable:false,width:150,height:25,required:true">
							<option value="image">图片</option>
							<option value="voice">语音</option>
							<option value="video">视频</option>
							<option value="thumb">缩略图</option>
					</select></td>
				</tr>
				<tr>
					<td>素材文件:</td>
					<td><input id="uploadFile"  class="easyui-filebox" name="uploadFile"  style="width: 150px" data-options="required:true">
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>