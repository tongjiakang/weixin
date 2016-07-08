<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	$(function() {

		$('#pid').combotree({
			url : '${ctx}/sys/organization/tree?flag=false',
			parentField : 'pid',
			lines : true,
			panelHeight : 'auto',
			value : '${organization.pid}'
		});

		$('#areaId').combotree({
			url : '${ctx}/sys/area/getAllAreas',
			parentField : 'pid',
			lines : true,
			panelHeight : 400,
			value : '${organization.areaId}',
			onSelect : function(node) {
				//返回树对象  
				var tree = $(this).tree;
				//选中的节点是否为叶子节点,如果不是叶子节点,清除选中  
				var isLeaf = tree('isLeaf', node.target);
				if (!isLeaf) {
					//清除选中  
					$('#areaId').treegrid("unselect");
				}
			},
			onLoadSuccess : function() {
				$("#areaId").combotree('tree').tree("collapseAll");
			}
		});

		$('#organizationEditForm').form({
			url : '${ctx}/sys/organization/edit',
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
					parent.$.modalDialog.openner_treeGrid.treegrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为organization.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
				}
			}
		});

		$("#orgType").val('${organization.orgType}')
	});
</script>
<div style="padding: 3px;">
	<form id="organizationEditForm" method="post">
		<table class="grid">
			<tr>
				<td>名称</td>
				<td>
					<input name="id" type="hidden" value="${organization.id}">
					<input name="createUser" type="hidden" value="${organization.createUser}">
					<input name="createDate" type="hidden" value="${organization.createDate}">
					<input name="orgName" type="text" placeholder="请输入名称" class="easyui-validatebox" data-options="required:true" value="${organization.orgName}">
				</td>
				<td>类型</td>
				<td>
					<select id="orgType" name="orgType" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto',required:true"
						disabled="disabled">
						<option value="company">公司</option>
						<option value="department">部门</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>排序</td>
				<td>
					<input name="seq" min="0" class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:true"
						value="${organization.seq}">
				</td>
				<td>菜单图标</td>
				<td>
					<input name="iconCls" value="${organization.iconCls}" />
				</td>
			</tr>
			<tr>
				<td>地址</td>
				<td colspan="3">
					<input name="address" style="width: 300px;" value="${organization.address}" />
				</td>
			</tr>
			<tr>
				<td>上级部门</td>
				<td colspan="3">
					<select id="pid" name="pid" style="width: 200px; height: 29px;" required="required" data-options="editable:false"></select>
					<a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#pid').combotree('clear');">清空</a>
				</td>
			</tr>
			<tr>
				<td>所在地区</td>
				<td colspan="3">
					<select id="areaId" name="areaId" style="width: 200px; height: 29px;" required="required" data-options="editable:false"></select>
					<a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#areaId').combotree('clear');">清空</a>
				</td>
			</tr>
		</table>
	</form>
</div>
