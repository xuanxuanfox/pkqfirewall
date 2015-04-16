<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/resources/inc.jsp"%>
<script type="text/javascript" src="<%=RootPath%>/resources/js/common/fc.js"></script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>检测结果查询和统计</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
</head>
<script type="text/javascript">
var resourceip="<%=request.getParameter("resourceip")%>";
var changetype="<%=request.getParameter("changetype")%>";
var start_changetime="<%=request.getParameter("start_changetime")%>";
var end_changetime="<%=request.getParameter("end_changetime")%>";
function InitFun(){
	var params="bean.resourceip="+resourceip;
	params+="&bean.changetype="+changetype;
	params+="&bean.start_changetime="+start_changetime;
	params+="&bean.end_changetime="+end_changetime;
	var dataUrl=RootPath+"/checkresult/statStream.action?"+params;
	dataUrl=escape(dataUrl);
	var chartSWF=RootPath+"/resources/swf/Column3D.swf";
	var chartWidth=document.body.offsetWidth-30;
	var chartHeight=document.body.offsetHeight-30;
	var chart = new FusionCharts(chartSWF, "ChartId",chartWidth,chartHeight, "0", "0");
	chart.setDataURL(dataUrl);
	chart.render(document.getElementById("chartDiv"));
}
</script>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="javascript:InitFun()" background-color="#FFFFFF">
	<table id="tab_chart" width="100%" height="100%" >
		<tr valign="middle">
			<td width="100%" height="100%" id="chartDiv" align="center"></td>
		</tr>
	</table>
</body>
</html>

