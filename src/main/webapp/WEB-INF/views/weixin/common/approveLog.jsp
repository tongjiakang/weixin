<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	function submitFun() {
		var approveStatus = $("input[name='approve']:checked").val();

		var URI = '${workflowInstance.URI}';
		$("#remark").val($("#approveMsg").val());
		var f = $('#approveForm');
		f.attr("action", "${ctx}" + URI + "?approveStatus=" + approveStatus);
		f.submit();
	}
</script>
<div style="width: 100%; margin-top: 15px;">
	<table style="width: 100%;" border="0" cellpadding="0" cellspacing="0" align="center">
		<c:if test="${workflowInstance.isEnd != '1' && workflowInstance.isCanApprove == 'Y' && view != 'view'}">
			<tr style="height: 50px">
				<td>
					<div style="color: black; font-size: 15px; font-weight: bold; display: inline;">审核意见：</div>
					<div style="display: inline;">
						<textarea id="approveMsg" rows="3" cols="100"></textarea>
					</div>
				</td>
			</tr>
			<tr style="height: 40px">
				<td>
					<span style="color: black; font-size: 15px; font-weight: bold;"> 审核操作： </span>
					<span>
						<input type="radio" name="approve" value="2" checked="checked">
						同意
						</input>
						<input type="radio" name="approve" value="3">
						不同意
						</input>
						&nbsp;&nbsp;
						<a onclick="submitFun()" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-submit',plain:true">提交</a>
					</span>
					<!-- 					<span> -->
					<!-- 						<input id="aggree" type="button" value="同意" onclick="javascript:submitFun('2')" /> -->
					<!-- 					</span> -->
					<!-- 					<span> -->
					<!-- 						<input id="unAggree" type="button" value="不同意" onclick="javascript:submitFun('3')" /> -->
					<!-- 					</span> -->
				</td>
			</tr>
		</c:if>
		<tr style="height: 40px">
			<td style="color: black; font-size: 15px; font-weight: bold;" colspan="2">审核日志：</td>
		</tr>
		<tr>
			<td>
				<table style="width: 100%; border-left: 1px solid gray; border-top: 1px solid gray;" border="0" cellpadding="0" cellspacing="0">
					<tr style="height: 30px;">
						<td style="border-right: 1px solid gray; border-bottom: 1px solid gray; text-align: center; font-weight: bold;">审核节点</td>
						<td style="border-right: 1px solid gray; border-bottom: 1px solid gray; text-align: center; font-weight: bold;">审核人员</td>
						<td style="border-right: 1px solid gray; border-bottom: 1px solid gray; text-align: center; font-weight: bold;">审核日期</td>
						<td style="border-right: 1px solid gray; border-bottom: 1px solid gray; text-align: center; font-weight: bold;">审核操作</td>
						<td style="border-right: 1px solid gray; border-bottom: 1px solid gray; text-align: center; font-weight: bold;">审核意见</td>
					</tr>
					<c:choose>
						<c:when test="${workflowInstance.logList != null && fn:length(workflowInstance.logList) > 0}">
							<c:forEach items="${workflowInstance.logList}" var="log">
								<tr style="height: 30px">
									<td style="border-right: 1px solid gray; border-bottom: 1px solid gray; text-align: center;">${log.stepName }</td>
									<td style="border-right: 1px solid gray; border-bottom: 1px solid gray; text-align: center;">${log.createUser }</td>
									<td style="border-right: 1px solid gray; border-bottom: 1px solid gray; text-align: center;">${log.createDate }</td>
									<td style="border-right: 1px solid gray; border-bottom: 1px solid gray; text-align: center;">${log.processOperation }</td>
									<td style="border-right: 1px solid gray; border-bottom: 1px solid gray; text-align: center;">${log.remark }</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr style="height: 30px">
								<td colspan="5" style="border-right: 1px solid gray; border-bottom: 1px solid gray; text-align: center;">暂无审核信息！</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
			</td>
		</tr>
	</table>

</div>