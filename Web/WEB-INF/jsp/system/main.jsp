<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/resources/inc.jsp"%>
<link rel="stylesheet" type="text/css" media="all" href="<%=RootPath%>/resources/css/alink.css" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<style>
body{
	font-size: 13px;
	margin-left: 15px;
	margin-top: 15px;
	margin-bottom: 15px;
	margin-right: 15px;
}
</style>
<head>
	<title>首页</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
</head>
<script type="text/javascript">


	function myinfo(){
		parent.addTab('资料修改','3010',"/system/myinfo.action");
	}
	function todo(){
		parent.addTab('待办任务','1010',"/workflow/todo.action");
	}
	function doclick() {
		var content = '<iframe scrolling="auto" frameborder="0" src="'+RootPath+'/system/mypwd.action" style="width:100%;height:100%;overflow-x:hidden;overflow:auto;"></iframe>';
	   	parent.$('#winframe').window({
	   		title: '修改密码',
		    width: 430,
		    height: 230,
		    content: content,
		    minimizable: false,
		    maximizable: false,
		    closable: true,
		    modal:true
		});
		parent.$('#winframe').window('center');
	    parent.$('#winframe').window('open');
	}
	
	function orderTodo(processInstanceId){
		parent.closeTab('待办任务');
		var turl="/workflow/todo.action?processInstanceId="+encodeURI(encodeURI(processInstanceId))+ "&time=" + (new Date());
	   	parent.addTab('待办任务',1010,turl);
	}
</script>
<body>
	<div id="win" class="easyui-window" data-options="closed:true"></div>
	<div id="cc" class="easyui-layout" style="width:100%;height:400px;">  
	  	<div class="easyui-panel"  region="west" split="true" title="欢迎" style="width:200px;height:350px;padding:10px;background:#fafafa; " iconCls="icon-tip"  closable="false" collapsible="true" minimizable="false" maximizable=false>
	  		<p>账号：<%=loginOperator.getOperatorName()%></p>    
		    <p>姓名：<%=loginOperator.getOperatorRealname()%></p>  
		    <p>系统角色：<%=loginOperator.getRoleName()%></p>  
		    <p>上次登录：<%=loginOperator.getLastLogintimeStr()%></p>  
		    <p>手机：<%=loginOperator.getMobile()%></p>  
		    <p>邮箱：<%=loginOperator.getEmail()%></p>  
		    <p align="center"><a class="a" onclick="myinfo()" href="javascript:void(0)">修改资料</a>
		    <a class="a" onclick="doclick()" href="javascript:void(0)">修改密码</a></p>  
		</div>  
		<div  class="easyui-panel" region="center" split="true" title="待办事项（0条）" style="width:200px;height:350px;padding:10px;background:#fafafa; " iconCls="icon-redo"  closable="false" collapsible="true" minimizable="false" maximizable=false>  
		</div>  
	</div>  
</body>
</html>
