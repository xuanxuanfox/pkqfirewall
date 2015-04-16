<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/resources/inc.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<style>
body{
	font-size: 13px;
	margin-left: 100px;
	margin-top: 35px;
	margin-bottom: 0;
	margin-right: 100px;
}
</style>
<html>
	<head>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
	</head>
	<script type="text/javascript">
		$(document).ready(function(){
		
		});
		function save(){
			var re=$("#addfrm").form('validate');
		    if(re){
            	$.messager.confirm('提示', '确定保存?', function(r){
					if (r) {
            			saveFunction('/operator/saveModMyinfo.action');
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
			<input type="hidden" id="operator_id" name="bean.operator_id" value="<%=loginOperator.getOperatorId()%>"/>
			<table style="width: 100%">
				<tr>
					<td width="20%" align="right">
						<span class=s>*</span>账号:
					</td>
					<td width="30%">
						<input id="operator_name" name="bean.operator_name" value="<%=loginOperator.getOperatorName()%>" class="easyui-validatebox" type="text" data-options="required:true" style="width: 250px;background-color: #E6E4DA" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						<span class=s>*</span>真实姓名:
					</td>
					<td width="30%">
						<input id="operator_realname" name="bean.operator_realname" value="<%=loginOperator.getOperatorRealname()%>" class="easyui-validatebox" type="text" data-options="required:true" style="width: 250px"/>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						手机号:
					</td>
					<td width="30%">
						<input id="mobile" name="bean.mobile" class="easyui-validatebox" type="text" value="<%=loginOperator.getMobile()%>" data-options="required:false" style="width: 250px"/>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						邮箱:
					</td>
					<td width="30%">
						<input id="email" name="bean.email" class="easyui-validatebox" type="text" value="<%=loginOperator.getEmail()%>" data-options="required:false" style="width: 250px"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						备注:
					</td>
					<td colspan="1">
						<textarea id="comments" name="bean.comments" style="width: 250px;height: 120px"  data-options="required:false"  class="easyui-validatebox"><%=loginOperator.getComments()%></textarea>
					</td>
				</tr>
			</table>
		</form>
		<div style="text-align: center; padding: 5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="save()">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancle()">重置</a>
		</div>
	</body>
</html>
