<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.alnie.tc.system.common.Constants"%>
<%@page import="com.alnie.tc.po.SysOperator"%>
<%
	String BodyColor="#F4F7F8";//E6E4DA
	String RootPath=request.getContextPath();
	String WebRootPath=request.getContextPath();
	String ImgPath=request.getContextPath()+"/resources/images";
	SysOperator loginOperator=Constants.GetLoginOperator(session);
	//loginOperator.setOperatorId(2);
%>
<script>
	RootPath="<%=RootPath%>";
	ImgPath="<%=ImgPath%>";
	Operator={};
	Operator.operatorId="<%=loginOperator.getOperatorId()%>";
	Operator.operatorName="<%=loginOperator.getOperatorName()%>";
	Operator.operatorRealname="<%=loginOperator.getOperatorRealname()%>";
	Operator.loginTime="<%=Constants.getDateTime("yyyy-MM-dd HH:mm:ss")%>";
	Operator.role="<%=loginOperator.getRole()%>";
	Operator.roleName="<%=loginOperator.getRoleName()%>";
</script>	
<script type="text/javascript" src="<%=RootPath%>/resources/easyui/jquery-1.8.0.min.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="<%=RootPath%>/resources/css/main.css" />

<link rel="stylesheet" type="text/css" href="<%=RootPath%>/resources/easyui/themes/cupertino/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=RootPath%>/resources/easyui/themes/icon.css" />
<script type="text/javascript" src="<%=RootPath%>/resources/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=RootPath%>/resources/easyui/datagrid-detailview.js"></script>
<script type="text/javascript" src="<%=RootPath%>/resources/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=RootPath%>/resources/js/common/common.js"></script>
<script>
	Constant={};
	Constant.page={};
</script>
