<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="pragma" CONTENT="no-cache">
<meta http-equiv="X-UA-Compatible" content="edge" />
<link rel="shortcut icon" href="${ctx}/style/images/favicon.png" />

<!-- [my97日期时间控件] -->
<script type="text/javascript" src="${ctx}/jslib/My97DatePicker/WdatePicker.js" charset="utf-8"></script>

<!-- [jQuery] -->
<script src="${ctx}/jslib/easyui1.4.2/jquery.min.js" type="text/javascript" charset="utf-8"></script>
<!-- [EasyUI] -->
<link id="easyuiTheme" rel="stylesheet"
	href="${ctx}/jslib/easyui1.4.2/themes/<c:out value="${cookie.easyuiThemeName.value}" default="gray"/>/easyui.css" type="text/css">
<link id="easyuiTheme" rel="stylesheet" href="${ctx}/jslib/easyui1.4.2/themes/icon.css" type="text/css">
<script type="text/javascript" src="${ctx}/jslib/easyui1.4.2/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/jslib/easyui1.4.2/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>

<!-- [扩展JS] -->
<script type="text/javascript" src="${ctx}/jslib/extJs.js" charset="utf-8"></script>
<!-- 
<script type="text/javascript" charset="utf-8" src="${ctx}/jslib/ueditor1.4.3/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/jslib/ueditor1.4.3/ueditor.all.js">
	
</script>
<script type="text/javascript" charset="utf-8" src="${ctx}/jslib/ueditor1.4.3/lang/zh-cn/zh-cn.js"></script>
 -->
<!-- [扩展lightmvc样式] -->
<link rel="stylesheet" href="${ctx}/style/css/mhj.css" type="text/css">
<script type="text/javascript">

	formatterDate = function(date) {
		var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
		var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"
		+ (date.getMonth() + 1);
		return date.getFullYear() + '-' + month + '-' + day + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
	};

	if (!Array.prototype.indexOf) {
		Array.prototype.indexOf = function(elt /*, from*/) {
			var len = this.length >>> 0;
			var from = Number(arguments[1]) || 0;
			from = (from < 0) ? Math.ceil(from) : Math.floor(from);
			if (from < 0)
				from += len;
			for (; from < len; from++) {
				if (from in this && this[from] === elt)
					return from;
			}
			return -1;
		};
	}
	var ContentFrame;
	function SetWinHeight(obj) {
		ContentFrame = obj;
		ChangeHeight();
	}

	function isEmpty(obj) {
		if (obj == undefined || obj == null || obj == "") {
			return true;
		}
		return false;
	}

	function ChangeHeight() {
		var obj = ContentFrame;
		var height;
		if (document.getElementById) {
			if (obj && !window.opera) {
				if (obj.contentDocument && obj.contentDocument.body.offsetHeight) {
					height = obj.contentDocument.body.offsetHeight;//obj是iframe框架id，则使用contentDocument来指它里面的内容页
					if (height < 50)
						obj.height = 50;
					else
						obj.height = height;
				} else if (obj.Document && obj.Document.body.scrollHeight) {
					height = obj.Document.body.scrollHeight;
					if (height < 50)
						obj.height = 50;
					else
						obj.height = height;
				}
			}
		}
	}
	function fmoney(s, n) {
		n = n > 0 && n <= 20 ? n : 2;
		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
		var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
		t = "";
		for (i = 0; i < l.length; i++) {
			t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
		}
		return t.split("").reverse().join("") + "." + r;
	}
</script>

<%--[格式化时间]--%>
<script type="text/javascript">
	Date.prototype.toLocaleString = function(fmt) {
		var format = "yyyy-MM-dd hh:mm:ss";
		if (fmt != undefined) {
			format = fmt;
		}
		var o = {
			"M+" : this.getMonth() + 1, // month
			"d+" : this.getDate(), // day
			"h+" : this.getHours(), // hour
			"m+" : this.getMinutes(), // minute
			"s+" : this.getSeconds(), // second
			"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
			"S" : this.getMilliseconds()
		// millisecond
		}
		if (/(y+)/.test(format))
			format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		for ( var k in o)
			if (new RegExp("(" + k + ")").test(format))
				format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k])
						.substr(("" + o[k]).length));
		return format;
	};
	/*function formatDatebox(value) {
		if (value == null || value == '') {
			return '';
		}
		var dt;
		if (value instanceof Date) {
			dt = value;
		} else {
			dt = new Date(value);
		}
		return dt.format("yyyy-MM-dd hh:mm:ss"); //扩展的Date的format方法(上述插件实现)
	}*/
</script>
<script type="text/javascript">
	//自定义验证
	$.extend($.fn.validatebox.defaults.rules, {
		phoneRex : {
			validator : function(value) {
				var rex = /^1[3-8]+\d{9}$/;
				//var rex=/^(([0\+]\d{2,3}-)?(0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
				//区号：前面一个0，后面跟2-3位数字 ： 0\d{2,3}
				//电话号码：7-8位数字： \d{7,8
				//分机号：一般都是3位数字： \d{3,}
				//这样连接起来就是验证电话的正则表达式了：/^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/		 
				var rex2 = /^((0\d{2,3})-)(\d{7,8})(-(\d{3,}))?$/;
				if (rex.test(value) || rex2.test(value)) {
					// alert('t'+value);
					return true;
				} else {
					//alert('false '+value);
					return false;
				}

			},
			message : '请输入正确电话或手机号码'
		}
	});
</script>

<style>
 a{ TEXT-DECORATION:none }
/** 查询页面样式 **/
.divContent0 {
	width: 100%;
	margin-top: 10px;
	margin-bottom: 10px;
	overflow: hidden;
	background-color: #fff;
	/* 	text-align: center; */
}

.tabContent0 {
	width: 90%;
	height: 100%;
	text-align: center;
}

/**  **/
.divContent1 {
	width: 95%;
	margin-right: auto;
	margin-left: auto;
}

.divContent2 {
	overflow-y: auto;
	overflow-x: hidden;
	padding: 3px;
}

/**标题样式 **/
.tdTitle {
	text-align: right;
	font-size: 14px;
	color: #000000;
	width: 15%;
}
/** 内容样式 **/
.tdContent1 {
	text-align: left;
	font-size: 14px;
	color: #000000;
	width: 85%;
}

.tdContent2 {
	text-align: left;
	font-size: 14px;
	color: #000000;
	width: 35%;
}

.divBtn {
	text-align: center;
	margin-top: 20px
}

.input1 {
	width: 150px;
	height: 25px;
}
</style>

