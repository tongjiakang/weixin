<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加图片</title>
</head>
<link href="${ctx}/jslib/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link href="${ctx}/jslib/bootstrap/fileinput/css/default.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/jslib/bootstrap/fileinput/css/fileinput.css" media="all" rel="stylesheet" type="text/css" />
<script src="${ctx}/jslib/bootstrap/js/html5shiv.min.js"></script>
<script type="text/javascript">
$(function() {
	if('${pStr.isEnable}' == 'true'){
		$('#file').fileinput('enable');
	}else{
		$('#file').fileinput('disable');
		if("${pStr.isEmpty}" == 'true' ){
			$("#picDiv").hide();
			$("#picMsg").show();
		}else{
			$("#picDiv").show();
		}
	}
});
</script>
<body data-options="fit:true,border:false" style="width: 100%; height: 100%">
	<div id="picMsg" style="text-align: left; font-size: 12px; color: blue; padding: 8px; display: none;">暂无找到相关图片。</div>
	<div class="htmleaf-container" id="picDiv">
		<form enctype="multipart/form-data">
			<label style="color: red; padding: 5px;">提示：上传图片的最佳尺寸【1024像素*768像素】，其他尺寸会影响页面效果，格式【jpeg，jpg，gif，png，bmp】，大小不超过2M，可多次上传，每次最多5张</label>
			<div class="form-group">
				<input id="file" name="file" type="file" multiple class="file" data-overwrite-initial="false" data-min-file-count="1">
				<!-- 业务单据编号 -->
				<input id="bizNo" name="bizNo" type="hidden" value="${pStr.bizNo}" />
			</div>
		</form>
	</div>
</body>

<!-- <script src="http://libs.useso.com/js/jquery/2.1.1/jquery.min.js"></script> -->
<script src="${ctx}/jslib/bootstrap/fileinput/js/fileinput.js" type="text/javascript"></script>
<script src="${ctx}/jslib/bootstrap/fileinput/js/fileinput_locale_zh.js" type="text/javascript"></script>
<script src="${ctx}/jslib/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script>
if (!Array.prototype.indexOf){
		Array.prototype.indexOf = function(elt /*, from*/){
    var len = this.length >>> 0;
    var from = Number(arguments[1]) || 0;
    from = (from < 0)
         ? Math.ceil(from)
         : Math.floor(from);
    if (from < 0)
      from += len;
    for (; from < len; from++)
    {
      if (from in this &&
          this[from] === elt)
        return from;
    }
    return -1;
  };
}
var flag='0';
	$(function(){
		setInterval(function(){
			if($('.progress-bar.progress-bar-success').text().indexOf('100')>-1){
				console.log($('.progress-bar.progress-bar-success').text());
				flag='0';
				window.location.reload();
			}
		},500);
		$("[title='上传选中文件']").click(function(){
			flag='1';
		});
/* 		$("btn.btn-default.kv-fileinput-upload.fileinput-upload-button").click(function(){
			alert(1);
			flag='1';
		}); */
	});
	$("#file").fileinput({
		uploadUrl : '${ctx}/common/picture/upload?bizNo=' + $("#bizNo").val(), // you must set a valid URL here else you will get an error
		allowedFileExtensions : [ 'jpg','jpeg', 'png', 'gif','bmp' ],
		overwriteInitial : false,
		maxFileSize : 2048,
		maxFileCount : 5,
		
		showRemove : true,//是否显示移除按钮，默认true,显示
		// 		showClose : true,
		//allowedFileTypes: ['image', 'video', 'flash'],
		slugCallback : function(filename) {
			return filename.replace('(', '_').replace(']', '_');
		},
		//示例:initialPreview: ["<img src='http://localhost:8080/p/1.png' class='file-preview-image' alt='Desert' title='Desert'>"]
		initialPreview : [ ${pStr.imgStrs} ],
		initialPreviewConfig : [ ${pStr.delStrs} ]
	});
	//上传成功事件
	$('#file').on('fileuploaded', function(event, data, previewId, index) {
		var form = data.form, files = data.files, extra = data.extra, response = data.response, reader = data.reader;
 		if(flag=='0'){
 			window.location.reload();
 		}
	});
// 	$('#file-input').fileinput({
// 	    resizeImage: true,
// 	    minImageHeight : 30,
// 		minImageWidth : 30,
// 	    maxImageWidth: 200,
// 	    maxImageHeight: 200,
// 	    resizePreference: 'width'
// 	});
</script>
</html>