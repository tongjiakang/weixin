<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="inc.jsp"></jsp:include>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>主页</title>
    <script type="text/javascript">
        var index_layout;
        var index_tabs;
        var index_tabsMenu;
        var layout_west_tree;
        var treeData = '';

        var sessionInfo_userId = '${sessionInfo.id}';
        if (sessionInfo_userId) {//如果没有登录,直接跳转到登录页面
        	treeData='[';
        	if('${sessionInfo.loginname}'=='admin'){
        		treeData +='{"attributes":{"url":""},"checked":false,"iconCls":"icon-folder","id":"7","pid":"","state":"close","text":"系统管理"},';
        	}
        	treeData +='{"attributes":{"url":""},"checked":false,"iconCls":"icon-folder","id":"1","pid":"","state":"close","text":"基础配置"},';
        	treeData +='{"attributes":{"url":""},"checked":false,"iconCls":"icon-folder","id":"2","pid":"","state":"close","text":"本地素材管理"},';
        	treeData +='{"attributes":{"url":""},"checked":false,"iconCls":"icon-folder","id":"8","pid":"","state":"close","text":"微信素材"},';
        	treeData +='{"attributes":{"url":""},"checked":false,"iconCls":"icon-folder","id":"3","pid":"","state":"close","text":"关注用户管理"},';
        	treeData +='{"attributes":{"url":""},"checked":false,"iconCls":"icon-folder","id":"4","pid":"","state":"close","text":"群发消息"},';
        	treeData +='{"attributes":{"url":""},"checked":false,"iconCls":"icon-folder","id":"5","pid":"","state":"close","text":"微营销"},';
        	treeData +='{"attributes":{"url":""},"checked":false,"iconCls":"icon-folder","id":"6","pid":"","state":"close","text":"统计分析"},';
        	
        	if('${sessionInfo.loginname}'=='admin'){
        		treeData +='{"attributes":{"url":"/sys/organization/manager"},"checked":false,"iconCls":"icon-folder","id":"71","pid":"7","state":"close","text":"组织机构"},';
            	treeData +='{"attributes":{"url":"/sys/user/manager"},"checked":false,"iconCls":"icon-folder","id":"72","pid":"7","state":"close","text":"微信管理员"},';
        	}
        	treeData +='{"attributes":{"url":"/account/manage"},"checked":false,"iconCls":"icon-folder","id":"11","pid":"1","state":"close","text":"公众号管理"},';
        	treeData +='{"attributes":{"url":"/welcomes/manage"},"checked":false,"iconCls":"icon-folder","id":"12","pid":"1","state":"close","text":"欢迎语"},';
        	treeData +='{"attributes":{"url":"/keyWord/manage"},"checked":false,"iconCls":"icon-folder","id":"13","pid":"1","state":"close","text":"关键字管理"},';
        	treeData +='{"attributes":{"url":"/customMenu/manage"},"checked":false,"iconCls":"icon-folder","id":"14","pid":"1","state":"close","text":"自定义菜单"},';
        	treeData +='{"attributes":{"url":"/unKnowWord/manage"},"checked":false,"iconCls":"icon-folder","id":"15","pid":"1","state":"close","text":"未识别回复语"},';

        	treeData +='{"attributes":{"url":"/news/manage"},"checked":false,"iconCls":"icon-folder","id":"21","pid":"2","state":"close","text":"消息素材"},';
        	treeData +='{"attributes":{"url":"/picMaterial/list"},"checked":false,"iconCls":"icon-folder","id":"22","pid":"2","state":"close","text":"图文素材"},';
        	treeData +='{"attributes":{"url":"/link/manage"},"checked":false,"iconCls":"icon-folder","id":"23","pid":"2","state":"close","text":"外部链接素材"},';
        	
        	treeData +='{"attributes":{"url":"/syncMaterial/manager"},"checked":false,"iconCls":"icon-folder","id":"81","pid":"8","state":"close","text":"图文素材同步"},';
        	treeData +='{"attributes":{"url":"/uploadFileMaterial/list"},"checked":false,"iconCls":"icon-folder","id":"82","pid":"8","state":"close","text":"文件素材上传"},';
        	
        	treeData +='{"attributes":{"url":"/wxusermanage/manage"},"checked":false,"iconCls":"icon-folder","id":"31","pid":"3","state":"close","text":"关注用户"},';
        	treeData +='{"attributes":{"url":"/wxgroupmanage/manage"},"checked":false,"iconCls":"icon-folder","id":"32","pid":"3","state":"close","text":"分组管理"},';
        	
        	treeData +='{"attributes":{"url":""},"checked":false,"iconCls":"icon-folder","id":"41","pid":"4","state":"close","text":"群发图文"},';
        	treeData +='{"attributes":{"url":""},"checked":false,"iconCls":"icon-folder","id":"42","pid":"4","state":"close","text":"群发预览、审核"},';
        	treeData +='{"attributes":{"url":""},"checked":false,"iconCls":"icon-folder","id":"43","pid":"4","state":"close","text":"群发记录"},';

        	treeData +='{"attributes":{"url":""},"checked":false,"iconCls":"icon-folder","id":"51","pid":"5","state":"close","text":"微营销"},';
        	
        	treeData +='{"attributes":{"url":""},"checked":false,"iconCls":"icon-folder","id":"61","pid":"6","state":"close","text":"关注用户统计"},';
        	treeData +='{"attributes":{"url":""},"checked":false,"iconCls":"icon-folder","id":"62","pid":"6","state":"close","text":"菜单点击统计"},';
        	treeData +='{"attributes":{"url":""},"checked":false,"iconCls":"icon-folder","id":"63","pid":"6","state":"close","text":"消息统计分析"}';
        	
        	treeData +=']';
        	           
      		treeData = $.parseJSON(treeData);
        } else {
            window.location.href = '${ctx}/login';
        }
        $(function () {
            index_layout = $('#index_layout').layout({
                fit: true
            });
            index_tabs = $('#index_tabs').tabs({
                fit: true,
                border: false,
                tools: [{
                    iconCls: 'icon-home',
                    handler: function () {
                        index_tabs.tabs('select', 0);
                    }
                }, {
                    iconCls: 'icon-refresh',
                    handler: function () {
                        var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
                        index_tabs.tabs('getTab', index).panel('open').panel('refresh');
                    }
                }, {
                    iconCls: 'icon-del',
                    handler: function () {
                        var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
                        var tab = index_tabs.tabs('getTab', index);
                        if (tab.panel('options').closable) {
                            index_tabs.tabs('close', index);
                        }
                    }
                }]
            });

            layout_west_tree = $('#layout_west_tree').tree({
                data:treeData,
                parentField: 'pid',
                lines: true,
                onClick: function (node) {
                    if (node.attributes && node.attributes.url) {
                        var url = '${ctx}' + node.attributes.url+"?title="+ node.text;
                        addTab({
                            url: url,
                            title: node.text,
                            iconCls: node.iconCls
                        });
                    }
                }
            });
        });

        function closeTab(closeTabName,refreshTabName) {
            //var currTab =  self.parent.$('#tabs').tabs('getSelected'); //获得当前tab
            var currTab = $('#index_tabs').tabs('getTab', refreshTabName);
            var url = $(currTab.panel('options').content).attr('src');
            $('#index_tabs').tabs('update', {
                tab: currTab,
                options: {
                    content: '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.5%;"></iframe>'
                }
            });
            $("#index_tabs").tabs('close', closeTabName);
            $("#index_tabs").tabs('select', refreshTabName);
        }
        
        function refershParentTab(closeTabName,refreshTabName) {
            //var currTab =  self.parent.$('#tabs').tabs('getSelected'); //获得当前tab
            var currTab = $('#index_tabs').tabs('getTab', refreshTabName);
            var url = $(currTab.panel('options').content).attr('src');
            $('#index_tabs').tabs('update', {
                tab: currTab,
                options: {
                    content: '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99.5%;"></iframe>'
                }
            });
//             $("#index_tabs").tabs('close', closeTabName);
//             $("#index_tabs").tabs('select', refreshTabName);
        }

        function getCurrentTab() {
            var currTab = $('#index_tabs').tabs('getSelected');
            var title = currTab.panel('options').title;
            return title;
        }
        
        function onlyCloseTab(closeTabName){
        	$("#index_tabs").tabs('close', closeTabName);
        }

        function addTab(params) {
            var iframe = '<iframe src="' + params.url + '" frameborder="0" style="border:0;width:100%;height:99.5%;"></iframe>';
            var t = $('#index_tabs');
            var opts = {
                title: params.title,
                closable: true,
                iconCls: params.iconCls,
                content: iframe,
                border: false,
                fit: true
            };
            if (t.tabs('exists', opts.title)) {
            	//modify by qianwei
                //t.tabs('select', opts.title);
            	t.tabs('close', opts.title);
            	t.tabs('add', opts);
            } else {
                t.tabs('add', opts);
            }
        }

        function logout() {
            $.messager.confirm('提示', '确定要退出?', function (r) {
                if (r) {
                    progressLoad();
                    $.post('${ctx}/logout', function (result) {
                        if (result.success) {
                            progressClose();
                            window.location.href = '${ctx}/login';
                        }
                    }, 'json');
                }
            });
        }


        function editUserPwd() {
            parent.$.modalDialog({
                title: '修改密码',
                width: 300,
                height: 250,
                href: '${ctx}/sys/user/editPwdPage',
                buttons: [{
                    text: '修改',
                    handler: function () {
                        var f = parent.$.modalDialog.handler.find('#editUserPwdForm');
                        f.submit();
                    }
                }]
            });
        }


    </script>
</head>
<body>
<div id="loading"
     style="position: fixed;top: -50%;left: -50%;width: 200%;height: 200%;background: #fff;z-index: 100;overflow: hidden;">
    <img src="${ctx}/style/images/ajax-loader.gif"
         style="position: absolute;top: 0;left: 0;right: 0;bottom: 0;margin: auto;"/>
</div>
<div id="index_layout">
    <div data-options="region:'north',border:false" style=" overflow: hidden;">
        <div id="header">
			<span style="float: right; padding-right: 20px;">欢迎， <b>${sessionInfo.username}</b>&nbsp;&nbsp; <a
                    href="javascript:void(0)" onclick="logout()" style="color: #fff">安全退出</a>
        	&nbsp;&nbsp;&nbsp;&nbsp;
    		</span>
            <span class="header"></span>
        </div>
    </div>
    <div data-options="region:'west',split:true" title="菜单" style="width: 160px; overflow: hidden;overflow-y:auto;">
        <div class="well well-small" style="padding: 5px 5px 5px 5px;">
            <ul id="layout_west_tree"></ul>
        </div>
    </div>
    <div data-options="region:'center'" style="overflow: hidden;">
        <div id="index_tabs" style="overflow: hidden;">
            <div title="首页" data-options="border:false" style="overflow: hidden;">
                <div style="padding:10px 0 10px 10px">
                    <h2>系统介绍</h2>
                    <div class="light-info">
                        <div class="light-tip icon-tip"></div>
                        <div>美好家微信管理平台。</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div data-options="region:'south',border:false"
         style="height: 30px;line-height:30px; overflow: hidden;text-align: center;background-color: #eee">
        版权所有@苏州荣鼎信息技术有限公司
    </div>
</div>

<!--[if lte IE 8]>
<div id="ie6-warning"><p>您正在使用 低版本浏览器，在本页面可能会导致部分功能无法使用。建议您升级到 <a
        href="http://www.microsoft.com/china/windows/internet-explorer/" target="_blank">Internet Explorer 9</a> 或以下浏览器：
    <a href="http://www.mozillaonline.com/" target="_blank">Firefox</a> / <a
            href="http://www.google.com/chrome/?hl=zh-CN" target="_blank">Chrome</a> / <a
            href="http://www.apple.com.cn/safari/" target="_blank">Safari</a> / <a href="http://www.operachina.com/"
                                                                                   target="_blank">Opera</a></p></div>
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
<script type="text/javascript">
$(function(){
	$(".l-btn-icon.icon-refresh").click(function(){
		window.location.reload();
	});
}); 
</script>
</body>
</html>