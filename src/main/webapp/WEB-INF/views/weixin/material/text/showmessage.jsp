<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>微信页面</title>
<meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1, maximum-scale=1">
<meta http-equiv="cache-control" content="no-cache">
</head>
<jsp:include page="../../../inc.jsp"></jsp:include>
<link href="plug-in/weixin/index.css" type="text/css" rel="stylesheet" />
<style type="text/css">
</style>
<body>
	<div style="overflow: hidden; clear: both;">
		<div style="float: left; width: 25%;">
			<div id="appmsg200159594" class="pp">
				<div class="appmsg multi" data-fileid="200159570" data-id="200159594">
					<div class="appmsg_content">
						<div class="appmsg_info">
							<em class="appmsg_date">${addtime}</em>
						</div>

						<div class="cover_appmsg_item" onmouseover="mouseover('header')" onmouseout="mouseout('header')">
							<h4 class="appmsg_title">
								<a target="_blank" href="#">${headerNews.title}</a>
							</h4>
							<div class="appmsg_thumb_wrp">
								<img class="appmsg_thumb" alt="" src="${headerNews.imagePath}">
							</div>
							<div class="fe" style="display: none">
								<a class="edit1" href="#" onclick="goUpate('${headerNews.id}')"></a>
							</div>
						</div>

						<c:forEach items="${newsList}" var="news">
							<div class="appmsg_item" onmouseover="mouseover('item')" onmouseout="mouseout('item')">
								<div class="fd" style="display: none">
									<a class="edit" id="edit" href="#" onclick="goUpate('${news.id}')"></a> <a class="dete" id="delete" href="#"
										onclick="goDelete('${news.id}')"></a>
								</div>
								<img class="appmsg_thumb" alt="" src="${news.imagePath}">
								<h4 class="appmsg_title">
									<a target="_blank" href="#">${news.title}</a>
								</h4>
							</div>

						</c:forEach>
					</div>
				</div>

			</div>
		</div>
		<div style="float: right; width: 75%;">
			<iframe scrolling="yes" id="showframe" src="" frameborder="0" style="width: 100%; height: 820px;"></iframe>
		</div>
	</div>
</body>
<script type="text/javascript">
	function mouseover(symbol) {
		if (symbol == 'header') {
			$(".fe").removeAttr("style");
			$(".fd").attr("style", "display:none");
		} else if (symbol == 'item') {
			$(".fe").attr("style", "display:none");
			$(".fd").removeAttr("style");
		}
	}

	function mouseout(symbol) {
		if (symbol == 'header') {
			$(".fe").attr("style", "display:none");
		} else if (symbol == 'item') {
			$(".fd").attr("style", "display:none");
		}
	}

	function goUpate(newsId) {
		if(newsId==""){
			alert("请添加图文素材再修改");
			return;
		}
		var url = "${ctx}/picMaterial?goUpdate&id=" + newsId + "&change="
				+ Date.parse(new Date()) + "&title=${param.title}&parentId=${param.templateId}";
		$("#showframe").attr("src", url);
	}

	function goDelete(newsId) {

		var url = "${ctx}/picMaterial?doDel&id=" + newsId;
		$.ajax({
			url : url,
			dataType : "JSON",
			method : "Get",
			success : function(data) {
				if (data.success) {
					parent.$.messager.alert('提示', data.msg, 'info');
				}
				location.reload();
			}
		});
	}

	function refresh() {
		location.reload();
	}
</script>
</html>
