<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	$(function() {

		$('#pid').combotree({
			url : '${ctx}/sys/organization/tree',
			parentField : 'pid',
			lines : true,
			panelHeight : 'auto'
		});

		$('#areaId').combotree({
			url : '${ctx}/sys/area/getAllAreas',
			parentField : 'pid',
			lines : true,
			panelHeight : 400,
			onLoadSuccess : function() {
				$("#areaId").combotree('tree').tree("collapseAll");
			},
			onSelect : function(node) {
				//返回树对象  
				var tree = $(this).tree;
				//选中的节点是否为叶子节点,如果不是叶子节点,清除选中  
				var isLeaf = tree('isLeaf', node.target);
				if (!isLeaf) {
					//清除选中  
					$('#areaId').treegrid("unselect");
				}
			}
		});

		$('#organizationAddForm').form({
			url : '${ctx}/sys/organization/add',
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

	});
</script>
<div style="padding: 3px;">
	<form id="organizationAddForm" method="post">
		<table class="grid">
			<tr>
				<td>名称</td>
				<td>
					<input name="orgName" type="text" placeholder="请输入名称" class="easyui-validatebox" data-options="required:true">
				</td>
				<td>类型</td>
				<td>
					<select name="orgType" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto',required:true">
						<option value="company">公司</option>
						<option value="department">部门</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>排序</td>
				<td>
					<input name="seq" min="0" class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:true" value="0">
				</td>
				<td>菜单图标</td>
				<td>
					<input name="iconCls" value="icon-folder" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td>地址</td>
				<td colspan="3">
					<input name="address" style="width: 300px;" />
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