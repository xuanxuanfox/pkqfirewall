<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.alnie.tc.system.utils.DateUtils"%>
<%@include file="/resources/inc.jsp"%>
<link rel="stylesheet" type="text/css" media="all" href="<%=RootPath%>/resources/css/alink.css" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>检测结果查询和统计</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
</head>
<body>
<table id="dg"></table>
<div id="tb">
	<div>
		资源IP: <input id="resourceip" name="bean.resourceip" class="easyui-validatebox" type="text" title="支持模糊匹配" data-options="required:false" style="width:150px"/>&nbsp;
		变更类型：<input id="changetype" name="bean.changetype" class="easyui-combobox"   data-options="url:'<%=RootPath%>/system/systemConfigList.action?bean.configGroupId=100003&bean.needAll=1', valueField:'configId', textField:'configValue',panelHeight:'100',editable:false,required:true,onLoadSuccess:function(){
					var data = $('#changetype').combobox('getData');
					 if (data.length > 0) {
                        $('#changetype').combobox('select',data[0].configId);
                    }
				}" style="width: 150px"/>&nbsp;
		变更时间：<input id="start_changetime" name="bean.start_changetime" class="easyui-datebox" value='<%=DateUtils.GetLastMonth("yyyy-MM-dd",DateUtils.GetNowDateStr("yyyy-MM-dd"),-1) %>' type="text" data-options="required:true" style="width: 150px"/>&nbsp;
		到：<input id="end_changetime" name="bean.end_changetime" class="easyui-datebox" value='<%=DateUtils.GetNowDateStr("yyyy-MM-dd") %>' type="text" data-options="required:true" style="width: 150px"/>&nbsp;
		<a href="#" id="btnQuery" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
		<a href="#" id="btnStat" class="easyui-linkbutton" iconCls="icon-search" plain="true">统计</a>
		<a href="#" id="btnDet" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true,disabled:true">明细</a>
	</div>
</div>
<div id="win" class="easyui-window" data-options="closed:true"></div>
<script type="text/javascript">
$(document).ready(function(){
	$('#dg').datagrid({
		title:'检测结果列表',
	    url:RootPath+'/checkresult/list.action',
	    queryParams:{
	    	'bean.resourceip': $("#resourceip").val(),
	    	'bean.changetype': $("#changetype").combobox('getValue'),
		    'bean.start_changetime': $("#start_changetime").datebox('getValue'),
		    'bean.end_changetime': $("#end_changetime").datebox('getValue')
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
	    	{field:'resourceip',title:'资源IP',width:100,align:'left'},
	    	{field:'filepath',title:'文件路径',width:150,align:'left'},
	        {field:'changetype_desc',title:'变更类型',width:80,align:'left'},
	        {field:'changetime',title: '变更时间',width: 100,align:'left'}
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
		$('#dg').datagrid('load', {
			'bean.resourceip': $("#resourceip").val(),
	    	'bean.changetype': $("#changetype").combobox('getValue'),
		    'bean.start_changetime': $("#start_changetime").datebox('getValue'),
		    'bean.end_changetime': $("#end_changetime").datebox('getValue')
		});
	});  
	$('#btnDet').bind('click', function() {
	    var opt = $('#btnDet').linkbutton('options');
	    if(opt.disabled)return;
	    var rows = $('#dg').datagrid('getSelections');
	    openwin({url:"/checkresult/det.action?id="+rows[0].id,title:"检测行为明细",width:550,height:250,fit:false});
	});
	$('#btnStat').bind('click', function() {
	    var resourceip=$("#resourceip").val();
	    var changetype=$("#changetype").combobox('getValue');
		var start_changetime=$("#start_changetime").datebox('getValue');
		var end_changetime=$("#end_changetime").datebox('getValue');
		var paras=["resourceip="+resourceip,"changetype="+changetype,"start_changetime="+start_changetime,"end_changetime="+end_changetime];
	    openwin({url:"/checkresult/statIndex.action?"+paras.join("&"),title:"检测结果统计",width:850,height:420,fit:false});
	});
});

function checkEditDelBtnStatus() {
    var rows = $('#dg').datagrid('getSelections');
    if (rows.length == 1) {
        $('#btnDet').linkbutton('enable');
    } else {
        $('#btnDet').linkbutton('disable');
    }
}
</script>
</body>
</html>
