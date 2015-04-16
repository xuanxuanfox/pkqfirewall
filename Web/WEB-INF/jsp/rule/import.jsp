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
            			saveFunction('/resource/batchImport.action');
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
		        	var files = document.getElementsByName("uf.file");
			    	for(var i=0; i<files.length; i++) {
			    		if(null==files[i].value || ""==files[i].value || undefined==files[i].value) {
			    			showMsg("请选择文件!");
			    			closeLoading();
			    			return false;
			    		}
			    		if(null != files[i].value && "" != files[i].value && undefined != files[i].value) {
			    			var fileType = (files[i].value.substring(files[i].value.lastIndexOf(".") + 1)).toLowerCase();
			    			if(fileType != "csv") {
			    				showMsg("上传文件类型不正确,请检查相关文件!");
			    				closeLoading();
			    				return false;
			    			}
			    		}
			    	}
		        },
		        success: function(_data) {
					var data=jQuery.parseJSON(_data);
		        	closeLoading();
		        	if(data.result_code=='1'){
		        		showMsg("保存成功！"+data.result_msg,null,null,function(){
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
		<form id="addfrm" name="addfrm" method="post" enctype="multipart/form-data">
			<table style="width: 100%">
			    <tr>
					<td width="80px" align="right">
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
					<td width="80px" align="right">
						<span class=s>*</span>导入文件:
					</td>
					<td width="60%" colspan="2">
						<input type="file" name="uf.file" style="width: 100%"/>
					</td>
					<td >
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
