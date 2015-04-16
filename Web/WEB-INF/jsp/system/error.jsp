<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/resources/inc.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<style>
body{
	font-size: 13px;
	margin-left: 10px;
	margin-top: 3px;
	margin-bottom: 0;
	margin-right: 60px;
}
</style>

<%
/**
	* 错误页面.
	* Comments: 
	* Author：Alnie
	* Create Date： 2011-09-14
	* Version: V1.0.0
	*/
%>

<html>
<title>错误页面</title>
<head>
<%
	response.setStatus(200);
	Exception e=pageContext.getException();
	ErrorData ed=pageContext.getErrorData();
	int statusCode=ed.getStatusCode();
	String eMsg=null;
	if(statusCode==404){
		eMsg="您访问的地址不存在，请确认您输入的URL地址";
	}else if(statusCode==500){
		eMsg="系统异常,详细原因：";
	}
	if(e!=null){
		eMsg+=e.getMessage();
	}
	eMsg=eMsg.replaceAll("\"","'").replaceAll("\n"," ").replaceAll("\r"," ");
%>
						
						
<script type="text/javascript">
	
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" bgcolor="#FFFFFF" >
<%=eMsg%>
</body>
</html>