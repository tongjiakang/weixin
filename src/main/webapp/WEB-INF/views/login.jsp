<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<jsp:include page="inc.jsp"></jsp:include>
<meta charset="utf-8">
<title>用户登录</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="viewport" content="width=device-width">
<link href="${ctx}/style/css/base.css" rel="stylesheet" type="text/css">
<link href="${ctx}/style/css/login.css" rel="stylesheet" type="text/css">
<link href="${ctx}/jslib/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<script type="text/javascript">
	$(document).ready(function() {
		if ($.cookie("rmbUser") == "true") {
			$("#rememberMe").prop("checked", true);
			$("#loginname").val($.cookie("loginname"));
			$("#password").val($.cookie("password"));
		}
	});

	// 记住用户名密码
	function save() {
		if ($("#rememberMe").prop("checked")) {
			var loginname = $("#loginname").val();
			var password = $("#password").val();
			$.cookie("rmbUser", "true", {
				expires : 7
			}); // 存储一个带7天期限的cookie
			$.cookie("loginname", loginname, {
				expires : 7
			});
			$.cookie("password", password, {
				expires : 7
			});
		} else {
			$.cookie("rmbUser", "false", {
				expire : -1
			});
			$.cookie("loginname", "", {
				expires : -1
			});
			$.cookie("password", "", {
				expires : -1
			});
		}
	};
</script>

<script>
	var sessionInfo_userId = '${sessionInfo.id}';
	if (sessionInfo_userId) {//如果登录,直接跳转到index页面
		window.location.href = '${ctx}/login';
	}
	function progressLoad() {
		$("<div class=\"datagrid-mask\" style=\"position:absolute;z-index: 9999;\"></div>").css({
			display : "block",
			width : "100%",
			height : $(window).height()
		}).appendTo("body");
		$("<div class=\"datagrid-mask-msg\" style=\"position:absolute;z-index: 9999;\"></div>").html("正在登录").appendTo(
				"body").css({
			display : "block",
			left : ($(document.body).outerWidth(true) - 190) / 2,
			top : ($(window).height() - 45) / 2
		});
	}
	// 	$(function() {
	// 		$('#loginform')
	// 				.form(
	// 						{
	// 							url : '${ctx}/console/login',
	// 							onSubmit : function() {
	// 								progressLoad();
	// 								var isValid = $(this).form('validate');
	// 								if (!isValid) {
	// 									progressClose();
	// 								}
	// 								return isValid;
	// 							},
	// 							success : function(data) {
	// 								var result = $.parseJSON("[" + data + "]")[0];
	// 								// var result = $.parseJSON(data);
	// 								progressClose();
	// 								if (result.success) {
	// 									window.location.href = '${ctx}/console/index';
	// 									save();
	// 								} else {
	// 									$.messager
	// 											.show({
	// 												title : '提示',
	// 												msg : '<div class="light-info"><div class="light-tip icon-tip"></div><div>'
	// 														+ result.msg
	// 														+ '</div></div>',
	// 												showType : 'show'
	// 											});
	// 								}
	// 							}
	// 						});
	// 	});

	function submitForm() {
		//$('#loginform').submit();
		if ($('#loginform').form('validate')) {
			progressLoad();
			$.post('${ctx}/login', $('#loginform').serialize(), function(data) {
				progressClose();
				var result = $.parseJSON(data);
				if (result.success) {
					window.location.href = '${ctx}/login';
					save();
				} else {
					$.messager.show({
						title : '提示',
						msg : '<div class="light-info"><div class="light-tip icon-tip"></div><div>' + result.msg
								+ '</div></div>',
						showType : 'show'
					});
				}
			});
		}
	}

	function clearForm() {
		$('#loginform').form('clear');
	}

	//回车登录
	function enterlogin() {
		if (event.keyCode == 13) {
			event.returnValue = false;
			event.cancel = true;
			submitForm();
		}
	}
</script>
</head>
<body onkeydown="enterlogin();">
	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div class="login-panel panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">系统登录</h3>
					</div>
					<div class="panel-body">
						<fieldset>
							<form role="form" id="loginform" method="post">
								<div class="form-group">
									<input class="form-control" id="loginname" placeholder="用户名" name="loginname" type="text" autofocus>
								</div>
								<div class="form-group">
									<input class="form-control" id="password" placeholder="密码" name="password" type="password">
								</div>
								<div class="checkbox">
									<label> <input id="rememberMe" name="remember" type="checkbox" value="Remember">记住密码
									</label>
								</div>
							</form>
							<button class="btn btn-lg btn-success btn-block" type="button" onclick="submitForm()">登录</button>
						</fieldset>

					</div>
				</div>
			</div>
		</div>
	</div>

	<!--[if lte IE 8]>
	<div id="ie6-warning"><p>您正在使用 低版本浏览器，在本页面可能会导致部分功能无法使用。建议您升级到 <a href="http://www.microsoft.com/china/windows/internet-explorer/" target="_blank">Internet Explorer 9</a> 或以下浏览器：
	<a href="http://www.mozillaonline.com/" target="_blank">Firefox</a> / <a href="http://www.google.com/chrome/?hl=zh-CN" target="_blank">Chrome</a> / <a href="http://www.apple.com.cn/safari/" target="_blank">Safari</a> / <a href="http://www.operachina.com/" target="_blank">Opera</a></p></div>
	<![endif]-->

	<style>
/*ie6提示*/
#ie6-warning {
	width: 100%;
	position: absolute;
	top: 0;
	left: 0;
	background: #fae692;
	padding: 5px 0;
	font-size: 12px
}

#ie6-warning p {
	width: 960px;
	margin: 0 auto;
}
</style>
</body>
</html>