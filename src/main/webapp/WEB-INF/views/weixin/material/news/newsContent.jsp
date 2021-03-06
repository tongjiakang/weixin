<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>
            ${newsItem.title}
        </title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0">
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <meta name="format-detection" content="telephone=no">
        <script type="text/javascript">
            document.domain = "qq.com";
            var _wxao = window._wxao || {};
            _wxao.begin = ( + new Date());
        </script>
        <link rel="stylesheet" type="text/css" href="${ctx}/jslib/weixin/client-page1e4a15.css">
        <!--[if lt IE 9]>
        <link rel="stylesheet" type="text/css" href="http://res.wx.qq.com/mmbizwap/zh_CN/htmledition/style/pc-page1ea1b6.css"
        <![endif]-->
        <link media="screen and (min-width:1000px)" rel="stylesheet" type="text/css" href="${ctx}/jslib/weixin/pc-page1ea1b6.css">
        <style>
            body{ -webkit-touch-callout: none; -webkit-text-size-adjust: none; }
        </style>
        <style>
            #nickname{overflow:hidden;white-space:nowrap;text-overflow:ellipsis;max-width:90%;}
            .page-toolarea a.random_empha{color:#607fa6;} ol,ul{list-style-position:inside;}
            #activity-detail .page-content .text{font-size:16px;}
        </style>
        <!--UEditor-->
        <script type="text/javascript" charset="utf-8" src="${ctx}/plug-in/ueditor/ueditor.config.js"></script>
        <script type="text/javascript" charset="utf-8" src="${ctx}/plug-in/ueditor/ueditor.all.min.js"></script>
    </head>
    
    <body id="activity-detail">
        <img width="12px" style="position: absolute;top:-1000px;" src="${ctx}/jslib/weixin/ico_loading1984f1.gif">
        <div class="wrp_page">
            <div class="page-bizinfo">
                <div class="header">
                    <h1 id="activity-name">
                        ${newsItem.title}
                    </h1>
                    <p class="activity-info">
                        <span id="post-date" class="activity-meta no-extra">
                            <fmt:formatDate value='${newsItem.createDate}' type="date" pattern="yyyy-MM-dd"/>
                        </span>
                       <%-- <span class="activity-meta">
                            jeecg社区
                        </span>--%>
                        <%--<a href="javascript:viewProfile();" id="post-user" class="activity-meta">
                            <span class="text-ellipsis">
                                ${newsItem.author}
                            </span>
                            <i class="icon_link_arrow">
                            </i>
                        </a>--%>
                        <span class="activity-meta">
                            ${newsItem.author}
                        </span>
                    </p>
                </div>
            </div>
            <div id="page-content" class="page-content" lang="en">
                <div id="img-content">
                    <div class="media" id="media">
                        <%--<img  src="${newsItem.imagePath}">--%>
                        ${newsItem.description}
                    </div>
                    <div class="text" id="js_content">
                        ${newsItem.content}
                       <%-- <textarea name="content" id="content">${newsItem.content}</textarea>
                        <script type="text/javascript">
                            var editor = UE.getEditor('content');
                        </script>--%>
                    </div>
                   <%-- <script type="text/javascript">
                        $("#js_content").html('${newsItem.content}');
                    </script>--%>
                </div>
            </div>
        </div>
    </body>

</html>