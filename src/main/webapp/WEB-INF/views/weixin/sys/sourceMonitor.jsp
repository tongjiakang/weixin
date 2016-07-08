<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../../inc.jsp"></jsp:include>
<meta http-equiv="X-UA-Compatible" content="edge" />
<title>系统监控</title>
<script type="text/javascript">
var daySelect;
var myLineChart;
$(function(){
	search('0');
});

function search(str){
	$('#myChart').remove();
	$('#canvasDiv').append('<canvas id="myChart" width="900" height="400"></canvas>');
	$('#myChartConn').remove();
	$('#canvasDivConn').append('<canvas id="myChartConn" width="900" height="400"></canvas>');
	var day;
	if(str == '0'){
		day = '${date.day}';
	}else{
		if(daySelect!=undefined){
			day = daySelect;
		}else{
			day = '${date.day}';
		}
		
	}
	var data = {
			hour:$('#hour').val(),
			day:day,
			showStyle:$('#showStyle').val()
		};
		$.ajax({ type: "POST",
		         //url: "/sysMonitor/chart?hour="+$('#hour').val()+"&day="+"${date.day}"+"&showStyle="+$('#showStyle').val(), 
		         url:"${ctx}/sysMonitor/chart",
		         data:JSON.stringify(data),
		         contentType: "application/json",
		         dataType: "json", 
		         success: function(text) {
		         var data = {
		        			labels : text.xAxis,
		        			datasets : [
		        				{
		        					fillColor : "rgba(220,220,220,0.5)",
		        					strokeColor : "rgba(220,220,220,1)",
		        					pointColor : "rgba(220,220,220,1)",
		        					pointStrokeColor : "#fff",
		        					data : text.memoryData
		        				},
		        				{
		        					fillColor : "rgba(151,187,205,0.5)",
		        					strokeColor : "rgba(151,187,205,1)",
		        					pointColor : "rgba(151,187,205,1)",
		        					pointStrokeColor : "#fff",
		        					data : text.cpuData
		        				}
		        			]
		        		}	
		         
		         
		         var dataConn = {
		        			labels : text.xAxis,
		        			datasets : [
		        				{
		        					fillColor : "rgba(213, 166, 189,0.5)",
		        					strokeColor : "rgba(213, 166, 189,1)",
		        					pointColor : "rgba(213, 166, 189,1)",
		        					pointStrokeColor : "#fff",
		        					data : text.connectionData
		        				}
		        			]
		        		}	
		         
		         
		         
		         var ctx = document.getElementById("myChart").getContext("2d");
		        myLineChart =   new Chart(ctx).Line(data, {
		 			responsive: true
		 		}); 
		        
		        var ctxConn = document.getElementById("myChartConn").getContext("2d");
		        myLineChartConn =   new Chart(ctxConn).Line(dataConn, {
		 			responsive: true
		 		});
		         
		         },
		         error: function(xhr) {}});
}
function pickedFunc(){  
	daySelect = $dp.cal.getDateStr();
	//alert($dp.cal.getDateStr());
	};     
</script>
</head>
<body>
	<label>显示方式:</label>
	<select id= "showStyle" >
		<option value = "SHOW_STYLE_HOUR" >小时内显示</option>
		<option value = "SHOW_STYLE_DAY" selected>天内显示</option>
		<option value = "SHOW_STYLE_MONTH">月内显示</option>
		<option value = "SHOW_STYLE_YEAR">年内显示</option>
	</select>
	<label>日期:</label>
	<input class="Wdate" type="text" id="d15" id="day" value="${date.day}"  style="height:14px" onFocus="WdatePicker({isShowClear:false,readOnly:true,onpicked:pickedFunc})"/>
	<label>小时:</label>
	<select id = "hour">
		<option value = "00">请选择</option>
		<option value = "00">00</option>
		<option value = "01">01</option>
		<option value = "02">02</option>
		<option value = "03">03</option>
		<option value = "04">04</option>
		<option value = "05">05</option>
		<option value = "06">06</option>
		<option value = "07">07</option>
		<option value = "08">08</option>
		<option value = "09">09</option>
		<option value = "10">10</option>
		<option value = "11">11</option>
		<option value = "12">12</option>
		<option value = "13">13</option>
		<option value = "14">14</option>
		<option value = "15">15</option>
		<option value = "16">16</option>
		<option value = "17">17</option>
		<option value = "18">18</option>
		<option value = "19">19</option>
		<option value = "20">20</option>
		<option value = "21">21</option>
		<option value = "22">22</option>
		<option value = "23">23</option>
	</select>
	<input type="button" value='查询' onclick="search('1');return false;"/>
	<div style="heigth:420px;width:900px">

	<table>
	<tr><td>CPU(%)</td><td style="background:rgba(151,187,205,0.5);width:60px"></td></tr>
	<tr><td>内存(%)</td><td style="background:rgba(220,220,220,0.5);width:60px"></td></tr>
	</table>
	<div id = "canvasDiv">
	<canvas id="myChart" width="900" height="400"></canvas>
	</div>
	<br/>
	<br/>
	<br/>
	<table>
	<tr><td>DB连接数(n)</td><td style="background:rgba(213, 166, 189,0.5);width:60px"></td></tr>
	</table>
	<div id = "canvasDivConn">
	<canvas id="myChartConn" width="900" height="400"></canvas>
	</div>
	</div>
</body>
</html>