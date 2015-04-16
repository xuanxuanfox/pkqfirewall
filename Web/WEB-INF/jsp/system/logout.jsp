<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String RootPath=request.getContextPath();
%>
<link rel="stylesheet" type="text/css" media="all" href="<%=RootPath%>/resources/css/main.css" />
<%
/**
	* 登出页面.
	* Comments:  
	* Author：Alnie
	* Create Date： 2011-09-14
	* Version: V1.0.0
	*/
%>

<html>
<title>登录超时页面</title>
<head>
						
						
<script type="text/javascript">
	function logout(){
		top.window.location.href="<%=RootPath%>/system/login.action";
	}
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" bgcolor="#DFE8F6" >
<table width="100%" height="100%" align="center">
<tr width="100%" height="100%" align="center" valign="middle">
<td width="100%" height="100%" id="form_area">对不起，您未登录或登录超时，请
	<a href='#' onclick='javascript:logout()'>重新登录</a>
</td>
</tr>
</table>
</body>
</html>