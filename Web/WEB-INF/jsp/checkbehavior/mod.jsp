<%@page language="java" pageEncoding="UTF-8"%>
<%@page import="com.alnie.tc.system.utils.DateUtils"%>
<%@include file="/resources/inc.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<style>
body{
	font-size: 13px;
	margin-left: 10px;
	margin-top: 3px;
	margin-bottom: 0;
	margin-right: 60px;
}
</style>
<html>
	<head>
		<title></title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
	</head>
	<script type="text/javascript">
		$(document).ready(function(){
			loading("页面加载中，请稍候...");
		});
		function init(){
			$.ajax({
			    type:'POST',
			    async:false,
			    dataType: 'json',
			    url:RootPath+"/checkbehavior/detail.action",
			    data: {"bean.id": "<%=request.getParameter("id")%>"},
		  		success: function(_data) {
		  			var _dataEx={};
		  			$.each( _data, function(index, content){
						if(content==null||content=='null')content='';
						_dataEx["bean."+index]=content;
					});
					$("#addfrm").form('load',_dataEx);
					closeLoading();
				},
				error:function(_data) {
					var data=jQuery.parseJSON(_data);
					closeLoading();
					showMsg("操作失败！");
				}
			});
		}
		function save(){
			var re=$("#addfrm").form('validate');
		    if(re){
            	$.messager.confirm('提示', '确定保存?', function(r){
					if (r) {
            			saveFunction('/checkbehavior/saveMod.action');
					}else{
						return false;
					}
					
				});
            }else return false;
		}
		function saveFunction(_url){
			loading();
			$("#addfrm").form('submit', {
		        url: RootPath+_url,
		        onSubmit: function() {
		        },
		        success: function(_data) {
					var data=jQuery.parseJSON(_data);
		        	closeLoading();
		        	if(data.result_code=='1'){
		        		showMsg("保存成功！",null,null,function(){
		        			parent.$('#win').window('close');
							parent.$('#dg').datagrid('reload');
		        		});
			    	}else showMsg(data.result_msg);  
				},
				error:function(_data) {
					var data=jQuery.parseJSON(_data);
					closeLoading();
					showMsg("操作失败！");
				}
		    });
		}
		function cancle(){
			init();
		}
	</script>
	<body>
		<form id="addfrm" name="addfrm" method="post">
			<input type="hidden" id="id" name="bean.id"/>
			<table style="width: 100%">
				<tr>
					<td width="20%" align="right">
						<span class=s>*</span>资源IP:
					</td>
					<td width="30%">
						<input id="resourceip" name="bean.resourceip" class="easyui-combobox"   data-options="url:'<%=RootPath%>/device/cbList.action', valueField:'ip', textField:'ip',panelHeight:'100',editable:false,required:true,onLoadSuccess:function(){
							var data = $('#resourceip').combobox('getData');
							 if (data.length > 0) {
		                        $('#resourceip').combobox('select',data[0].ip);
		                    }
						}" style="width: 150px"/>
					</td>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td width="30%" align="right">
						<span class=s>*</span>检测时间:
					</td>
					<td width="30%">
						<input id="checktime" name="bean.checktime" class="easyui-datetimebox" value='<%=DateUtils.GetNowDateStr("yyyy-MM-dd") %>' type="text" data-options="required:true" style="width: 150px"/>
					</td>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td width="30%" align="right">
						<span class=s>*</span>检测类型:
					</td>
					<td width="30%">
						<input id="checktype" name="bean.checktype" class="easyui-combobox"   data-options="url:'<%=RootPath%>/system/systemConfigList.action?bean.configGroupId=100002&bean.needAll=false', valueField:'configId', textField:'configValue',panelHeight:'100',editable:false,required:true,onLoadSuccess:function(){
							init();
						}" style="width: 150px"/>
					</td>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
			</table>
		</form>
		<div id="buttonDiv" style="text-align: center; padding: 5px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="save()">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancle()">重置</a>
		</div>
	</body>
</html>
