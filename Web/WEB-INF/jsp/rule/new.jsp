<%@page language="java" pageEncoding="UTF-8"%>
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
.readonly{
 background:#CCCCCC;
}
</style>

<%
	//String deviceip = (String)request.getParameter("deviceip");
	String deviceip = "192.168.1.100";  //just for test
%>

<html>
	<head>
		<title></title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
	</head>
	<script type="text/javascript">
		//var succFileMap={};
		$(document).ready(function(){
			loading("页面加载中，请稍候...");
			init();
		});
		function init(){
			var data = $('#direction').combobox('getData');
			if (data.length > 0) {
				$('#direction').combobox('select',data[0].value);
			}
			data = $('#protocol').combobox('getData');
			if (data.length > 0) {
				$('#protocol').combobox('select',data[0].value);
			}
			data = $('#action').combobox('getData');
			if (data.length > 0) {
				$('#action').combobox('select',data[0].value);
			}
		
			closeLoading();
		}
		function save(){
			var re=$("#addfrm").form('validate');
		    if(re){
            	$.messager.confirm('提示', '确定保存?', function(r){
					if (r) {
            			saveFunction('/rule/saveNew.action');
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
			$("#addfrm").form('reset');
		}
	</script>
	<body>
		<form id="addfrm" name="addfrm" method="post">
			<table style="width: 100%">
			    <tr>
					<td width="20%" align="right">
						设备IP:
					</td>
					<td width="30%">
						<input id="deviceip" name="bean.deviceip" value=<%=deviceip%> class="readonly"></input> 
					</td>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						<span class=s>*</span>方向:
					</td>
					<td width="30%">
						<input id="direction" 
						name="bean.direction" 
						class="easyui-combobox" 
						data-options="valueField:'value' ,textField:'lable', data:[{lable:'inbound',value:'inbound'},{lable:'outbound',value:'outbound'}]"
						/>
					</td>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						<span class=s>*</span>action:
					</td>
					<td width="30%">
						<input id="action" 
						name="bean.action" 
						class="easyui-combobox" 
						data-options="valueField:'value' ,textField:'lable', data:[{lable:'allow',value:'allow'},{lable:'deny',value:'deny'}]"
						/>
					</td>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						<span class=s>*</span>协议:
					</td>
					<td width="30%">
						<input id="protocol" 
						name="bean.protocol" 
						class="easyui-combobox" 
						data-options="valueField:'value' ,textField:'lable', data:[{lable:'tcp',value:'tcp'},{lable:'udp',value:'udp'}]"
						/>
					</td>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
			    <tr>
					<td width="20%" align="right">
						<span class=s>*</span>端口:
					</td>
					<td width="30%">
						<input id="port" name="bean.port" class="easyui-validatebox" type="text" data-options="required:true" style="width: 100%;"/>
					</td>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
			    <tr>
					<td width="20%" align="right">
						远端IP:
					</td>
					<td width="30%">
						<input id="remoteIp" name="bean.remoteIp" class="easyui-validatebox" type="text" data-options="required:false" style="width: 100%;"/>
					</td>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
			    <tr>
					<td width="20%" align="right">
						远端端口:
					</td>
					<td width="30%">
						<input id="remotePort" name="bean.remotePort" class="easyui-validatebox" type="text" data-options="required:false" style="width: 100%;"/>
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
