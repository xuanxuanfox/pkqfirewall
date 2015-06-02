<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/resources/inc.jsp"%>
<link rel="stylesheet" type="text/css" media="all" href="<%=RootPath%>/resources/css/alink.css" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
	<title>设备管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
</head>
<body>

<table id="dg"></table>
<div id="tb">
	<div>
		设备IP: 
		<input id="ip" name="bean.ip" class="easyui-validatebox" type="text" title="支持模糊匹配" 
			data-options="required:false" style="width:150px"
		/>
		设备类型: 
		<input id="type" name="bean.type" class="easyui-combobox" 
			data-options="
			  valueField:'value'
			  ,textField:'lable'
			  ,data:[{lable:'redhat',value:'redhat'},{lable:'SERVER2008',value:'SERVER2008'}]
			"
		/>
		<a href="#" id="btnQuery" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
		<a href="#" id="btnAdd" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
		<a href="#" id="btnEdit" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,disabled:true">修改</a>
		<a href="#" id="btnRemove" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,disabled:true">删除</a>
		<a href="#" id="btnDet" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true,disabled:true">明细</a>
		<a href="#" id="btnShowFirewallRole" class="easyui-linkbutton" data-options="iconCls:'icon-tip',plain:true,disabled:true">查看策略</a>
		<a href="#" id="btnAddNewAgentVersion" class="easyui-linkbutton" data-options="iconCls:'icon-sum',plain:true,disabled:false">添加新版本</a>
	</div>
</div>
<div id="win" class="easyui-window" data-options="closed:true"></div>
<script type="text/javascript">
$(document).ready(function(){
	 var data = $('#type').combobox('getData');
	 if (data.length > 0) {
       $('#type').combobox('select',data[0].value);
     }
	
	$('#dg').datagrid({
		title:'设备列表',
	    url:RootPath+'/device/list.action',
	    queryParams:{
	    	'bean.ip': $("#ip").val()
	    	,'bean.type': $("#type").combobox('getValue')
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
	    columns:[[
	        {field:'ip',title: '设备IP',width: 100,align:'left'},
	        {field:'type',title:'设备类型',width:80,align:'left'},
	        {field:'description',title:'描述',width:200,align:'left'}
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
		var thetype = $("#type").combobox('getValue');
		//alert(thetype);
		$('#dg').datagrid('load', {
			'bean.ip': $("#ip").val(),'bean.type': thetype
		});
	});  
	$('#btnAdd').bind('click', function() {
	  	var opt = $('#btnAdd').linkbutton('options');
	  	if(opt.disabled)return;
	  	openwin({url:"/device/new.action",title:"新增设备",width:750,height:300,fit:false});
	});
	
	$('#btnEdit').bind('click', function() {
	    var opt = $('#btnEdit').linkbutton('options');
	    if(opt.disabled)return;
	    var rows = $('#dg').datagrid('getSelections');
	    openwin({url:"/device/mod.action?id="+rows[0].id,title:"修改设备",width:750,height:300,fit:false});
	});

	$('#btnShowFirewallRole').bind('click', function() {
	    var opt = $('#btnShowFirewallRole').linkbutton('options');
	    if(opt.disabled)return;
	    var rows = $('#dg').datagrid('getSelections');
	    var url = "/rule/index.action?deviceip="+rows[0].ip;
	    openwin({url:url,title:"防火墙策略",width:800,height:350,fit:false});
	    
	});
	
	$('#btnNotifyUpdateAgent').bind('click', function() {
	 	var url = "/rule/NotifyUpdateAgent.action";
	    openwin({url:url,title:"通知更新",width:800,height:350,fit:false});
	    
	});
	
	$('#btnAddNewAgentVersion').bind('click', function() {
	 	var url = "/rule/updateAgentIndex.action";
	    openwin({url:url,title:"添加新版本",width:800,height:350,fit:false});
	    
	});
	  
	$('#btnRemove').bind('click', function() {
	  	var opt = $('#btnRemove').linkbutton('options');
	  	if(opt.disabled)return;
	   	$.messager.confirm('删除设备', '确定要删除该设备吗?', function(r){
			if (r) {
				var ss = [];
				var rows = $('#dg').datagrid('getSelections');
				var params = {
					'bean.id':rows[0].id
				}
				$.ajax({
					type: 'post',
					url: RootPath+'/device/saveDel.action',
					data: params,
					dataType: 'text',
					success:function(data, textStatus) {
						$('#dg').datagrid('reload');
					},
					error:function() { 
						alert("删除失败，请联系管理员!");
					}
				})
			}
		});
	});
	$('#btnDet').bind('click', function() {
	    var opt = $('#btnDet').linkbutton('options');
	    if(opt.disabled)return;
	    var rows = $('#dg').datagrid('getSelections');
	    //openwin({url:"/device/det.action?id="+rows[0].id,title:"设备明细",width:750,height:300,fit:false});
	});
});

function checkEditDelBtnStatus() {
    var rows = $('#dg').datagrid('getSelections');
    if (rows.length == 1) {
        $('#btnEdit').linkbutton('enable');
        $('#btnRemove').linkbutton('enable');
        $('#btnDet').linkbutton('enable');
        $('#btnShowFirewallRole').linkbutton('enable');
    } else {
        $('#btnEdit').linkbutton('disable');
        $('#btnRemove').linkbutton('disable');
        $('#btnDet').linkbutton('disable');
         $('#btnShowFirewallRole').linkbutton('disable');
    }
}
</script>
</body>
</html>
