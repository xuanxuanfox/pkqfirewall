<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.alnie.tc.system.common.Constants"%>
<%@page import="com.alnie.tc.system.utils.CommonUtil"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/easyui/jquery-1.8.0.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/easyui/themes/icon.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/easyui/locale/easyui-lang-zh_CN.js"></script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
</head>
<style>
body,tr,td {
	font-size: 13px;
	margin: 0;
}
</style>
  
<body id="bodylayout" class="easyui-layout">
	<div id="northPanel" data-options="region:'north',border:false" style="height:65px;">
		<div class="easyui-panel" fit="true" border="false">
			<table cellpadding="0" cellspacing="0" style="width:100%; height:100%;">
				<tr>
					<td style="background:url(<%=request.getContextPath()%>/resources/images/top-1_03.jpg);width:30%; height:100% "></td>
					<td style="background:url(<%=request.getContextPath()%>/resources/images/top-1_02.jpg);width:70%; height:100% ">
						<form action="" method="post">
							<table style="position:absolute;right:10px;bottom:5px">
								<tr>
									<td><font color="#FFFFFF" style="font-weight: bold;">欢迎您,<%=Constants.GetLoginOperator(session).getOperatorRealname()%></font></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td><a onclick="logout()" href="javascript:void(0)" style="text-decoration: none;">退出登录</a></td>
									<td><a id="modfiyPwd" href="javascript:void(0);" onclick="doclick()" style="text-decoration: none;">修改密码</a></td>
								</tr>
							</table>
						</form>
					</td>
				</tr>
			</table>
   		</div>
	</div>
	<div id="westPanel" data-options="region:'west',border:true,split:true,collapsed:false,title:'工作台'" style="width:200px;padding:10px;">
		<ul id="functionTree"></ul>
	</div>
	<div id="centerPanel" data-options="region:'center',border:true,title:'功能位置: 首页'" >
		<div id="tt" class="easyui-tabs" data-options="tabPosition:'top',fit:true,border:false,plain:true">  
		   
		</div> 
	</div>
	<div id="winframe" class="easyui-window" data-options="closed:true"></div>
	<div id="topframe" class="easyui-window" data-options="closed:true"></div>
</body>
<script type="text/javascript">
	var SystemName="<%=CommonUtil.GetProConfig("SYSTEM_NAME")%>";
	document.title=SystemName+'-主页面';
	RootPath="<%=request.getContextPath()%>";
	$(document).ready(function(){
		var content = '<iframe scrolling="auto" frameborder="0" src="<%=request.getContextPath()%>/system/main.action" style="width:100%;height:100%;overflow-x:hidden;overflow:auto;" id="tabMain"></iframe>';
		$('#tt').tabs('add',{
			id:'tabMain',
			title: '首页',
			content:content,
			selected: true,
			closable: false
		});
		$('#centerPanel').panel('setTitle','功能位置: 首页');
		
		$('#functionTree').tree({
		    url:RootPath+'/system/menu.action',
		    animate: true,
		    checkbox:false,
		    onLoadSuccess:function(){
		    	$('#functionTree').tree("expandAll");
		    },
		    onClick: function(node) {
				if(node.attributes.isLeaf) {
					addTab(node.text,node.id,node.attributes.url);
				} 
			}
		});
	
		$('#tt').tabs({
		    onSelect: function(title,index) {
		    	var tab = $('#tt').tabs('getTab',index);
		    	var options = tab.panel('options');
		    	if("tabMain" != options.id) {
		    		tabTitle(title);
		    	} else {
		    		$('#centerPanel').panel('setTitle','功能位置: 首页')
		    	}
		    } 
		});
	});
	
	function doclick() {
		var content = '<iframe scrolling="auto" frameborder="0" src="'+RootPath+'/system/mypwd.action" style="width:100%;height:100%;overflow-x:hidden;overflow:auto;"></iframe>';
	   	$('#winframe').window({
	   		title: '修改密码',
		    width: 430,
		    height: 230,
		    content: content,
		    minimizable: false,
		    maximizable: false,
		    closable: true,
		    modal:true
		});
		$('#winframe').window('center');
	    $('#winframe').window('open');
	}
	function logout(){
		$.messager.confirm('提示', '确定退出?', function(r){
			if (r) {
				$.messager.progress({ 
					title:'请稍后', 
					msg:'处理中...'
				}); 
          		top.window.location.href=RootPath+"/system/logoutAction.action";
			}else{
				return false;
			}
			
		});
	}
	function tabTitle(title){
		$('#centerPanel').panel('setTitle','功能位置: ' + title);
	}
	function closeTab(text){
		$('#tt').tabs('close',text);
	}
	function addTab(text,tid,turl){
	   	var tab = $('#tt').tabs('getTab',text);
		if(null !== tab) {
			$('#tt').tabs('select',text);
		} else {
			var content = '<iframe scrolling="auto" frameborder="0" src="'+RootPath+turl+'" style="width:100%;height:100%;overflow-x:hidden;overflow:auto;" id="'+tid+'"></iframe>';
			$('#tt').tabs('add',{
				id: 3010,
				title: text,
				content:content,
				selected: true,
				closable: true
			});
		}
		tabTitle(text);
	}
</script>
</html>