<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.alnie.tc.system.utils.CommonUtil"%>
<%@include file="/resources/inc.jsp"%>
<link rel="stylesheet" type="text/css" media="all" href="<%=RootPath%>/resources/css/alink.css" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>账号管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
</head>
<body>
<table id="dg"></table>
<div id="tb">
	<div>
		账号: <input id="operator_name" name="bean.operator_name" class="easyui-validatebox" type="text" title="支持模糊匹配" data-options="required:false" style="width:150px"/>&nbsp;
		<a href="#" id="btnQuery" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
		<a href="#" id="btnAdd" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
		<a href="#" id="btnEdit" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true,disabled:true">修改</a>
		<a href="#" id="btnRemove" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true,disabled:true">删除</a>
		<a href="#" id="btnDet" class="easyui-linkbutton" data-options="iconCls:'icon-redo',plain:true,disabled:true">明细</a>
		<a href="#" id="btnReset" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true,disabled:true">重置密码</a>
	</div>
</div>
<div id="win" class="easyui-window" data-options="closed:true"></div>
<script type="text/javascript">
$(document).ready(function(){
	$('#dg').datagrid({
		title:'账号列表',
	    url:RootPath+'/operator/list.action',
	    queryParams:{
	    	'bean.operator_name': $("#operator_name").val()
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
	        {field:'operator_name',title: '账号',width: 80,align:'left'},
	        {field:'operator_realname',title:'姓名',width:80,align:'left'},
	        {field:'mobile',title:'手机号',width:60,align:'left'},
	        {field:'email',title:'邮箱',width:80,align:'left'},
	        {field:'f_create_time',title:'创建时间',width:100,align:'left'},
	        {field:'f_last_logintime',title:'最后一次登录时间',width:100,align:'left'},
	        {field:'role_name',title:'角色',width:100,align:'left'}
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
			'bean.operator_name': $("#operator_name").val()
		});
	});  
	$('#btnAdd').bind('click', function() {
	  	var opt = $('#btnAdd').linkbutton('options');
	  	if(opt.disabled)return;
	  	openwin({url:"/operator/new.action",title:"新增账号",width:750,height:320,fit:false});
	});
	
	$('#btnEdit').bind('click', function() {
	    var opt = $('#btnEdit').linkbutton('options');
	    if(opt.disabled)return;
	    var rows = $('#dg').datagrid('getSelections');
	    openwin({url:"/operator/mod.action?operator_id="+rows[0].operator_id,title:"修改账号",width:750,height:320,fit:false});
	});
	  
	$('#btnRemove').bind('click', function() {
	  	var opt = $('#btnRemove').linkbutton('options');
	  	if(opt.disabled)return;
	   	$.messager.confirm('删除账号', '确定要删除该记录吗?', function(r){
			if (r) {
				var ss = [];
				var rows = $('#dg').datagrid('getSelections');
				var params = {
					'bean.operator_id':rows[0].operator_id,
					'bean.operator_name':rows[0].operator_name
				}
				$.ajax({
					type: 'post',
					url: RootPath+'/operator/saveDel.action',
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
	    openwin({url:"/operator/det.action?operator_id="+rows[0].operator_id,title:"账号明细",width:750,height:320,fit:false});
	});
	$('#btnReset').bind('click', function() {
	  	var opt = $('#btnReset').linkbutton('options');
	  	if(opt.disabled)return;
	   	$.messager.confirm('重置密码', '确定要重置密码吗?', function(r){
			if (r) {
				var ss = [];
				var rows = $('#dg').datagrid('getSelections');
				var params = {
					'bean.operator_id':rows[0].operator_id,
					'bean.operator_name':rows[0].operator_name,
					'bean.password':"<%=CommonUtil.GetProConfig(Constants.DEFAULT_PASSWORD)%>"
				}
				$.ajax({
					type: 'post',
					url: RootPath+'/operator/saveModPwd.action',
					data: params,
					dataType: 'text',
					success:function(data, textStatus) {
						showMsg('操作成功！');
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
        $('#btnReset').linkbutton('enable');
    } else {
        $('#btnEdit').linkbutton('disable');
        $('#btnRemove').linkbutton('disable');
        $('#btnDet').linkbutton('disable');
        $('#btnReset').linkbutton('disable');
    }
}
</script>
</body>
</html>
