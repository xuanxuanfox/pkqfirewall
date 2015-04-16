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
		});
		function init(){
			$.ajax({
			    type:'POST',
			    async:false,
			    dataType: 'json',
			    url:RootPath+"/resource/detail.action",
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
            			saveFunction('/resource/saveMod.action');
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
						<input id="ip" name="bean.ip" class="easyui-combobox"   data-options="url:'<%=RootPath%>/device/cbList.action', valueField:'ip', textField:'ip',panelHeight:'100',editable:false,required:true,onLoadSuccess:function(){
							var data = $('#ip').combobox('getData');
							 if (data.length > 0) {
		                        $('#ip').combobox('select',data[0].ip);
		                    }
						}" style="width: 150px"/>
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
						<input id="filepath" name="bean.filepath" class="easyui-validatebox" type="text" data-options="required:true" style="width: 100%;"/>
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
						<input id="filetype" name="bean.filetype" class="easyui-combobox"   data-options="url:'<%=RootPath%>/system/systemConfigList.action?bean.configGroupId=100001&bean.needAll=false', valueField:'configId', textField:'configValue',panelHeight:'150',editable:false,required:true,onLoadSuccess:function(){
							init();
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
						<textarea id="description" name="bean.description" style="width: 100%;height: 110px" data-options="required:false"  class="easyui-validatebox"></textarea>
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
