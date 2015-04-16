<%@page language="java" pageEncoding="UTF-8"%>
<%@include file="/resources/inc.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<style>
body{
	font-size: 13px;
	margin-left: 10px;
	margin-top: 10px;
	margin-bottom: 0;
	margin-right: 10px;
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
			    url:RootPath+"/checkresult/detail.action",
			    data: {"bean.id": "<%=request.getParameter("id")%>"},
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
			<table style="width: 100%">
				<tr>
					<td width="20%" align="right">
						资源IP:
					</td>
					<td width="50%">
						<input id="resourceip" name="bean.resourceip" class="easyui-validatebox" type="text" data-options="required:true" style="width: 100%;background-color: #E6E4DA" readonly="readonly"/>
					</td>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						文件路径:
					</td>
					<td width="50%">
						<input id="filepath" name="bean.filepath" class="easyui-validatebox" type="text" data-options="required:true" style="width: 100%;background-color: #E6E4DA" readonly="readonly"/>
					</td>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						变更类型:
					</td>
					<td width="50%">
						<input id="changetype_desc" name="bean.changetype_desc" class="easyui-validatebox" type="text" data-options="required:true" style="width: 100%;background-color: #E6E4DA" readonly="readonly"/>
					</td>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						变更时间:
					</td>
					<td width="50%">
						<input id="changetime" name="bean.changetime" class="easyui-validatebox" type="text" data-options="required:true" style="width: 100%;background-color: #E6E4DA" readonly="readonly"/>
					</td>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td colspan="4" height="100%"></td>
				</tr>
			</table>
		</form>
	</body>
</html>
