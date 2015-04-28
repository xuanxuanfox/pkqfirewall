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
</style>
<html>
	<head>
		<title></title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
	</head>
	<script type="text/javascript">
		var succFileMap={};
		$(document).ready(function(){
			loading("页面加载中，请稍候...");
			init();
		});
		function init(){
			closeLoading();
		}
		function save(){
			var re=$("#addfrm").form('validate');
		    if(re){
            	$.messager.confirm('提示', '确定保存?', function(r){
					if (r) {
            			saveFunction('/device/updateAgent.action');
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
						<span class=s>*</span>设备IP:
					</td>
					<td width="30%">
						<input id="ip" name="bean.ip" class="easyui-validatebox" type="text" data-options="required:true" style="width: 100%;"/>
					</td>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						<span class=s>*</span>一级设备类型:
					</td>
					<td width="30%">
						<input id="main_type" name="bean.main_type" class="easyui-combobox"   data-options="url:'<%=RootPath%>/system/systemConfigList.action?bean.configGroupId=100000&bean.needAll=false', valueField:'configId', textField:'configValue',panelHeight:'150',editable:false,required:true,onLoadSuccess:function(){
							var data = $('#main_type').combobox('getData');
							 if (data.length > 0) {
		                        $('#main_type').combobox('select',data[0].configId);
		                    }
						},onChange: function(value) {
							var _id=parseInt(value)+200000;
							var _url=RootPath+'/system/systemConfigList.action?bean.configGroupId='+_id+'&bean.needAll=false';
							$('#type').combobox({
								url:_url,
								valueField:'configValue', 
								textField:'configValue',
								onLoadSuccess:function(){
									var data = $('#type').combobox('getData');
									 if (data.length > 0) {
				                        $('#type').combobox('select',data[0].configValue);
				                    }
								}
							});
					    }" style="width: 150px"/>
					</td>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						<span class=s>*</span>设备类型:
					</td>
					<td width="30%">
						<input id="type" name="bean.type" class="easyui-combobox"   data-options="url:'', valueField:'configValue', textField:'configValue',panelHeight:'150',editable:false,required:true,onLoadSuccess:function(){
							var data = $('#type').combobox('getData');
							 if (data.length > 0) {
		                        $('#type').combobox('select',data[0].configValue);
		                    }
						}" style="width: 150px"/>
					</td>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td align="right">
						描述:
					</td>
					<td colspan="3">
						<textarea id="description" name="bean.description" style="width: 100%;height: 120px" data-options="required:false"  class="easyui-validatebox"></textarea>
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
