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
		$(document).ready(function(){
			loading("页面加载中，请稍候...");
			$.ajax({
			    type:'POST',
			    async:false,
			    dataType: 'json',
			    url:RootPath+"/operator/detail.action",
			    data: {"bean.operator_id": "<%=request.getParameter("operator_id")%>"},
		  		success: function(_data) {
		  			init(_data);
				},
				error:function(_data) {
					var data=jQuery.parseJSON(_data);
					closeLoading();
					showMsg("操作失败！");
				}
			});
		});
		function init(_data){
			var _dataEx={};
  			$.each( _data, function(index, content){
				if(content==null||content=='null')content='';
				_dataEx["bean."+index]=content;
			});
			$("#addfrm").form('load',_dataEx);
			closeLoading();
		}
	</script>
	<body>
		<form id="addfrm" name="addfrm" method="post">
			<input type="hidden" id="operator_id" name="bean.operator_id"/>
			<table style="width: 100%;height: 100%">
				<tr>
					<td width="20%" align="right">
						<span class=s>*</span>账号:
					</td>
					<td width="30%">
						<input id="operator_name" name="bean.operator_name" class="easyui-validatebox" type="text" data-options="required:true" style="width: 100%;background-color: #E6E4DA" readonly="readonly"/>
					</td>
					<td width="20%" align="right">
						<span class=s>*</span>真实姓名:
					</td>
					<td width="30%">
						<input id="operator_realname" name="bean.operator_realname" class="easyui-validatebox" type="text" data-options="required:true" style="width: 100%;background-color: #E6E4DA" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						手机号:
					</td>
					<td width="30%">
						<input id="mobile" name="bean.mobile" class="easyui-validatebox" type="text" data-options="required:false" style="width: 100%;background-color: #E6E4DA" readonly="readonly"/>
					</td>
					<td width="20%" align="right">
						邮箱:
					</td>
					<td width="30%">
						<input id="email" name="bean.email" class="easyui-validatebox" type="text" data-options="required:false" style="width: 100%;background-color: #E6E4DA" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						<span class=s>*</span>系统角色:
					</td>
					<td colspan="3">
						<input id="role_name" name="bean.role_name" class="easyui-validatebox" type="text" data-options="required:true" style="width: 100%;background-color: #E6E4DA" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						备注:
					</td>
					<td colspan="3">
						<textarea id="comments" name="bean.comments" style="width: 100%;height: 120px;background-color: #E6E4DA" readonly="readonly" data-options="required:false"  class="easyui-validatebox"></textarea>
					</td>
				</tr>
				<tr>
				<td colspan="4" height="100%"></td>
				</tr>
			</table>
		</form>
	</body>
</html>
