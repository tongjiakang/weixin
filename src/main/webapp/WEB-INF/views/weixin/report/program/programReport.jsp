<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: PanJM
  Date: 2016/3/3
  Time: 11:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>方案报表</title>
    <jsp:include page="../../../inc.jsp"></jsp:include>
    <script type="text/javascript" src="${ctx}/jslib/echart/echarts-all.js"></script>
    <script type="text/javascript" src="${ctx}/jslib/echart/eChartReport.js"></script>
    <script type="text/javascript">
        var chartData;
        $(function () {
            $.ajax({
                type: "post",
                url: "${ctx}/biz/program/report",
                dataType: "json",
//                async: false,
                /* data: {
                 "str": str
                 },*/
                success: function (data) {
                    chartData = data;
                    var cData = getChartDatas(data,'bar');
                    loadChart("main", "报表演示样例", cData,"bar");
                },
                error: function () {
                }
            });

            $("#chartType").change(function () {
                var typeVal = $(this).val();
                var cData = getChartDatas(chartData,typeVal);
                loadChart("main", "报表演示样例", cData,typeVal);
            });
        });
    </script>
</head>
<body>
<div>
    <select id="chartType" style="width: 150px;height: 20px">
        <option value="bar">柱形图</option>
        <option value="pie">饼图</option>
        <option value="line">折线图</option>
    </select>
</div>
<div id="main" style="width: 800px;height:600px;"></div>
</body>
</html>
