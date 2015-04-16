<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.alnie.tc.system.utils.DateUtils"%>
<%@include file="/resources/inc.jsp"%>
<link rel="stylesheet" type="text/css" media="all" href="<%=RootPath%>/resources/css/alink.css" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>检测行为查询</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
</head>
<body>
<table id="dg"></table>
<div id="tb">
	<div>
		资源IP: <input id="resourceip" name="bean.resourceip" class="easyui-validatebox" type="text" title="支持模糊匹配" data-options="required:false" style="width:150px"/>&nbsp;
		检测类型：<input id="checktype" name="bean.checktype" class="easyui-combobox"   data-options="url:'<%=RootPath%>/system/systemConfigList.action?bean.configGroupId=100002&bean.needAll=1', valueField:'configId', textField:'configValue',panelHeight:'100',editable:false,required:true,onLoadSuccess:function(){
					var data = $('#checktype').combobox('getData');
					 if (data.length > 0) {
                        $('#checktype').combobox('select',data[0].configId);
                    }
				}" style="width: 150px"/>&nbsp;
		检测时间：<input id="start_checktime" name="bean.start_checktime" class="easyui-datebox" value='<%=DateUtils.GetLastMonth("yyyy-MM-dd",DateUtils.GetNowDateStr("yyyy-MM-dd"),-1) %>' type="text" data-options="required:true" style="width: 150px"/>&nbsp;
		到：<input id="end_checktime" name="bean.end_checktime" class="easyui-datebox" value='<%=DateUtils.GetNowDateStr("yyyy-MM-dd") %>' type="text" data-options="required:true" style="width: 150px"/>&nbsp;
		<a href="#" id="btnQuery" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
		<a href="#" id="btnAdd" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
		<a href="#" id="btnEdit" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,disabled:true">修改</a>
		<a href="#" id="btnRemove" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,disabled:true">删除</a>
		<a href="#" id="btnDet" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true,disabled:true">明细</a>
	</div>
</div>
<div id="win" class="easyui-window" data-options="closed:true"></div>
<script type="text/javascript">
$(document).ready(function(){
	$('#dg').datagrid({
		title:'检测行为列表',
	    url:RootPath+'/checkbehavior/list.action',
	    queryParams:{
	    	'bean.resourceip': $("#resourceip").val(),
	    	'bean.checktype': $("#checktype").combobox('getValue'),
		    'bean.start_checktime': $("#start_checktime").datebox('getValue'),
		    'bean.end_checktime': $("#end_checktime").datebox('getValue')
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
	        {field:'checktime',title: '检测时间',width: 100,align:'left'},
	        {field:'resourceip',title:'资源IP',width:100,align:'left'},
	        {field:'checktype_desc',title:'检测类型',width:80,align:'left'}
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
	    	'bean.checktype': $("#checktype").combobox('getValue'),
		    'bean.start_checktime': $("#start_checktime").datebox('getValue'),
		    'bean.end_checktime': $("#end_checktime").datebox('getValue')
		});
	});  
	$('#btnDet').bind('click', function() {
	    var opt = $('#btnDet').linkbutton('options');
	    if(opt.disabled)return;
	    var rows = $('#dg').datagrid('getSelections');
	    openwin({url:"/checkbehavior/det.action?id="+rows[0].id,title:"检测行为明细",width:550,height:250,fit:false});
	});
	$('#btnAdd').bind('click', function() {
	  	var opt = $('#btnAdd').linkbutton('options');
	  	if(opt.disabled)return;
	  	openwin({url:"/checkbehavior/new.action",title:"新增检测行为",width:650,height:250,fit:false});
	});
	
	$('#btnEdit').bind('click', function() {
	    var opt = $('#btnEdit').linkbutton('options');
	    if(opt.disabled)return;
	    var rows = $('#dg').datagrid('getSelections');
	    openwin({url:"/checkbehavior/mod.action?id="+rows[0].id,title:"修改检测行为",width:650,height:250,fit:false});
	});
	  
	$('#btnRemove').bind('click', function() {
	  	var opt = $('#btnRemove').linkbutton('options');
	  	if(opt.disabled)return;
	   	$.messager.confirm('删除检测行为', '确定要删除该检测行为吗?', function(r){
			if (r) {
				var ss = [];
				var rows = $('#dg').datagrid('getSelections');
				var params = {
					'bean.id':rows[0].id,
					'bean.ip':rows[0].ip
				}
				$.ajax({
					type: 'post',
					url: RootPath+'/checkbehavior/saveDel.action',
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
});

function checkEditDelBtnStatus() {
    var rows = $('#dg').datagrid('getSelections');
    if (rows.length == 1) {
        $('#btnEdit').linkbutton('enable');
        $('#btnRemove').linkbutton('enable');
        $('#btnDet').linkbutton('enable');
    } else {
        $('#btnEdit').linkbutton('disable');
        $('#btnRemove').linkbutton('disable');
        $('#btnDet').linkbutton('disable');
    }
}
</script>
</body>
</html>
