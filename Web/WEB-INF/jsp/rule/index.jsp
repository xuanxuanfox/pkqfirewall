<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/resources/inc.jsp"%>
<link rel="stylesheet" type="text/css" media="all" href="<%=RootPath%>/resources/css/alink.css" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%
	String deviceip = (String)request.getParameter("deviceip");
	//String deviceip = "192.168.1.100";  //just for test
%>
<html>
<head>
	<title>防火墙策略管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
</head>
<body>
<table id="dg"></table>
<div id="tb">
	<div>
	设备ip：<label id="deviceip"><%=deviceip%></label> &nbsp;   &nbsp;
	方向: 
		<input id="direction" name="bean.direction" class="easyui-combobox" 
			data-options="
			  valueField:'value'
			  ,textField:'lable'
			  ,data:[{lable:'inbound',value:'inbound'},{lable:'outbound',value:'outbound'}]
			"
		/>
		<a href="#" id="btnQuery" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
		<a href="#" id="btnAdd" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
		<a href="#" id="btnRemove" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,disabled:true">删除</a>
		  &nbsp;   &nbsp;  &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; 默认策略：<label id="defaultpolicy"></label>
	</div>
	
</div>

<div id="win" class="easyui-window" data-options="closed:true"></div>
<script type="text/javascript">
$(document).ready(function(){
	 var data = $('#direction').combobox('getData');
	 if (data.length > 0) {
       $('#direction').combobox('select',data[0].value);
     }
     //获取默认策略
		var direction = $("#direction").combobox('getValue');
		var params = { 'bean.direction': direction, 'bean.deviceip': '<%=deviceip%>'};
		$.ajax({
					type: 'post',
					url: RootPath+'/rule/getDefaultRule.action',
					data: params,
					dataType: 'json',
					success:function(data, textStatus) {
						$("#defaultpolicy").text(data.result_msg);
					},
					error:function() { 
						$("#defaultpolicy").text("");  //如果获取默认策略失败
					}
		});
     
	$('#dg').datagrid({
		title:'策略列表',
	    url:RootPath+'/rule/list.action',
	    queryParams:{
	    	'bean.ip': $("#deviceip").text(),
	    	'bean.direction': $("#direction").combobox('getValue')
		},
		toolbar: '#tb',
	    fitColumns: true,
	    fit: true,
	    singleSelect: true,
		loadMsg:'数据加载中，请稍候...',
		pageSize:'50',
		pagination: true,
		rownumbers: true,
		pagination:true,
		pageList:[10,20,30,40,50,200],
	    columns:[[
	        {field:'direction',title: '方向',width: 10,align:'left'},
	        {field:'action',title: 'action',width: 10,align:'left'},
	        {field:'protocol',title:'协议',width:10,align:'left'},
	        {field:'port',title:'端口',width:10,align:'left'},
	        {field:'remoteIp',title:'远端ip',width:20,align:'left'},
	        {field:'remotePort',title:'远端端口',width:10,align:'left'}
	    ]],
	    onLoadSuccess:function(bb){
	    	if(bb.result_code!=undefined)showMsg('加载出错，请与管理员联系！');
	    	else checkEditDelBtnStatus();
	    },
	    onLoadError:function(){
	    	showMsg('网络异常，请与管理员联系！');
	    },
        onClickRow: function(rowIndex, rowData) {
            checkEditDelBtnStatus();
        }
	}); 
	
	$('#btnQuery').bind('click', function(){
		var direction = $("#direction").combobox('getValue');
		var params = { 'bean.direction': direction, 'bean.deviceip': '<%=deviceip%>'};
		//获取默认策略
		$.ajax({
					type: 'post',
					url: RootPath+'/rule/getDefaultRule.action',
					data: params,
					dataType: 'json',
					success:function(data, textStatus) {
						$("#defaultpolicy").text(data.result_msg);
					},
					error:function() { 
						$("#defaultpolicy").text("");  //如果获取默认策略失败
					}
		});
		$('#dg').datagrid('load', {
			'bean.ip': $("#deviceip").text(),'bean.direction': direction
		});
	});  
	$('#btnAdd').bind('click', function() {
	  	var opt = $('#btnAdd').linkbutton('options');
	  	if(opt.disabled)return;
	  	var addurl="/rule/new.action?deviceip="+$("#deviceip").text();
	  	openwin({url:addurl,title:"新增策略",width:400,height:300,fit:false});
	});
		  
	$('#btnRemove').bind('click', function() {
	  	var opt = $('#btnRemove').linkbutton('options');
	  	if(opt.disabled)return;
	   	$.messager.confirm('删除策略', '确定要删除该策略吗?', function(r){
			if (r) {
				var ss = [];
				var rows = $('#dg').datagrid('getSelections');
				var params = {
					'bean.id':rows[0].id,'bean.deviceip': '<%=deviceip%>'
				}
				$.ajax({
					type: 'post',
					url: RootPath+'/rule/saveDel.action',
					data: params,
					dataType: 'text',
					success:function(data, textStatus) {
						$('#dg').datagrid('reload');
					},
					error:function() { 
						showMsg("删除失败，请联系管理员!");
					}
				})
			}
		});
	});
	
});

function checkEditDelBtnStatus() {
    var rows = $('#dg').datagrid('getSelections');
    if (rows.length == 1) {
        $('#btnRemove').linkbutton('enable');
    } else {
        $('#btnRemove').linkbutton('disable');
    }
}
</script>
</body>
</html>
