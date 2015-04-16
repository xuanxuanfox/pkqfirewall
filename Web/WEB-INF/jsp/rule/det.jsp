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
			    url:RootPath+"/resource/detail.action",
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
						<span class=s>*</span>资源IP:
					</td>
					<td width="30%">
						<input id="ip" name="bean.ip" class="easyui-validatebox" type="text" data-options="required:true" style="width: 100%;background-color: #E6E4DA" readonly="readonly"/>
					</td>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						<span class=s>*</span>文件路径:
					</td>
					<td width="30%">
						<input id="filepath" name="bean.filepath" class="easyui-validatebox" type="text" data-options="required:true" style="width: 100%;background-color: #E6E4DA" readonly="readonly"/>
					</td>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						<span class=s>*</span>文件类型:
					</td>
					<td width="30%">
						<input id="filetype_desc" name="bean.filetype_desc" class="easyui-validatebox" type="text" data-options="required:true" style="width: 100%;background-color: #E6E4DA" readonly="readonly"/>
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
						<textarea id="description" name="bean.description" style="width: 100%;height: 110px;background-color: #E6E4DA" readonly="readonly" data-options="required:false"  class="easyui-validatebox"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="4" height="100%"></td>
				</tr>
			</table>
		</form>
	</body>
</html>
