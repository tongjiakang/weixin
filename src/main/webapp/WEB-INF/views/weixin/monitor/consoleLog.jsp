<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/3/1
  Time: 14:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Title</title>
    <jsp:include page="../../inc.jsp"></jsp:include>
    <script type="text/javascript">
        $(function () {
            $.ajax({
                type: "post",
                url: "${ctx}/log/viewConsoleLog?type=console",
                dataType: "json",
//                async: false,
               /* data: {
                    "str": str
                },*/
                success: function (data) {
                    $("#consoleLog").html(data);
                },
                error: function () {

                }
            });
        });
    </script>
</head>
<body>
    <div style="background-color: #0f0f0f"><span id="consoleLog" style="color: #0F0">加载中...</span></div>
</body>
</html>
