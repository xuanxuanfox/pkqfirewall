<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.alnie.tc.system.utils.DateUtils"%>
<%@include file="/resources/inc.jsp"%>
<link rel="stylesheet" type="text/css" media="all" href="<%=RootPath%>/resources/css/alink.css" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>操作日志</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
</head>
<body>
<table id="dg"></table>
<div id="tb">
	<div>
		用户名: <input id="operator_realname" name="bean.operator_realname" class="easyui-validatebox" type="text" title="支持模糊匹配" data-options="required:false" style="width:150px"/>&nbsp;
		操作结果：<input id="trans_result" name="bean.trans_result" class="easyui-combobox"   data-options="url:'<%=RootPath%>/system/systemConfigList.action?bean.configGroupId=100005&bean.needAll=1', valueField:'configId', textField:'configValue',panelHeight:'100',editable:false,required:true,onLoadSuccess:function(){
					var data = $('#trans_result').combobox('getData');
					 if (data.length > 0) {
                        $('#trans_result').combobox('select',data[0].configId);
                    }
				}" style="width: 150px"/>&nbsp;
		操作业务：<input id="trans_type" name="bean.trans_type" class="easyui-combobox"   data-options="url:'<%=RootPath%>/system/systemConfigList.action?bean.configGroupId=100004&bean.needAll=1', valueField:'configId', textField:'configValue',panelHeight:'100',editable:false,required:true,onLoadSuccess:function(){
					var data = $('#trans_type').combobox('getData');
					 if (data.length > 0) {
                        $('#trans_type').combobox('select',data[0].configId);
                    }
				}" style="width: 150px"/>&nbsp;
		登录时间：<input id="start_trans_time" name="bean.start_trans_time" class="easyui-datebox" value='<%=DateUtils.GetLastMonth("yyyy-MM-dd",DateUtils.GetNowDateStr("yyyy-MM-dd"),-1) %>' type="text" data-options="required:true" style="width: 150px"/>&nbsp;
		到：<input id="end_trans_time" name="bean.end_trans_time" class="easyui-datebox" value='<%=DateUtils.GetNowDateStr("yyyy-MM-dd") %>' type="text" data-options="required:true" style="width: 150px"/>&nbsp;
		<a href="#" id="btnQuery" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
	</div>
</div>
<div id="win" class="easyui-window" data-options="closed:true"></div>
<script type="text/javascript">
$(document).ready(function(){
	$('#dg').datagrid({
		title:'检测行为列表',
	    url:RootPath+'/log/list.action',
	    queryParams:{
	    	'bean.operator_realname': $("#operator_realname").val(),
	    	'bean.trans_result': $("#trans_result").combobox('getValue'),
	    	'bean.trans_type': $("#trans_type").combobox('getValue'),
		    'bean.start_trans_time': $("#start_trans_time").datebox('getValue'),
		    'bean.end_trans_time': $("#end_trans_time").datebox('getValue')
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
	        {field:'operator_realname',title: '用户名',width: 100,align:'left'},
	        {field:'trans_time',title:'操作时间',width:100,align:'left'},
	        {field:'trans_type_desc',title:'操作业务',width:80,align:'left'},
	        {field:'trans_sub_type',title:'操作类别',width:80,align:'left'},
	        {field:'trans_result_desc',title:'操作结果',width:80,align:'left'},
	        {field:'trans_details',title:'操作内容',width:150,align:'left',formatter: function(value,row,rowIndex) {
	        	if(row.trans_details.length>10)
            		return "<span title=\"" + replaceAll(row.trans_details,"\"","") + "\">" + row.trans_details.substring(0, 50) + "...</span>";
            	else return row.trans_details;
	        }}
	    ]],
	    onLoadSuccess:function(bb){
	    	if(bb.result_code!=undefined)showMsg('加载出错，请与管理员联系！');
	    },
	    onLoadError:function(){
	    	showMsg('网络异常，请与管理员联系！');
	    }
	}); 
	$('#btnQuery').bind('click', function(){
		$('#dg').datagrid('load', {
			'bean.operator_realname': $("#operator_realname").val(),
			'bean.trans_result': $("#trans_result").combobox('getValue'),
	    	'bean.trans_type': $("#trans_type").combobox('getValue'),
		    'bean.start_trans_time': $("#start_trans_time").datebox('getValue'),
		    'bean.end_trans_time': $("#end_trans_time").datebox('getValue')
		});
	});  
});

</script>
</body>
</html>
