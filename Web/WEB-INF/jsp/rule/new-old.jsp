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
			$("#searchDevice").bind("click",searchDevice);
			
			$("#addto").bind("click",function(){ 				
 				var strqueryresult = $("#queryresult").val();
 				if(strqueryresult!=null){
 					strdevs=$("#devices").val();
 					if(strdevs!=null){
 						$("#devices").val(strqueryresult+strdevs);
 					}else{
 						$("#devices").val(strqueryresult);
 					}
 				}
			});
			
			
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
		
		function saveAndDown(){
			var re=$("#addfrm").form('validate');
		    if(re){
            	$.messager.confirm('提示', '确定保存并下发?', function(r){
					if (r) {
            			saveFunction('/rule/saveAndDown.action');
					}else{
						return false;
					}
				});
            }else return false;
		}
		
		function searchDevice(){
			var params = {'bean.ip': $("#ip").val()};
			var url=RootPath+'/device/list.action?bean.ip='+$("#ip").val()+'&page=1&rows=500';
			$.ajax({
					type: 'get',
					url: url,
					data: {},
					dataType: 'text',
					success:function(data, textStatus) {
						var str="";
						//var jdata=eval(data);
						var jdata=$.parseJSON(data);
						var drows=jdata.rows;
						for (var i = 0; i < jdata.total; i++) {
							str= str + drows[i].ip + "\n";
						}
						$("#queryresult").val(str);
					},
					error:function() { 
						showMsg("删除失败，请联系管理员!");
					}
			});
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
		        		showMsg("操作成功！",null,null,function(){
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
		
	</script>
	<body>
		<form id="addfrm" name="addfrm" method="post">
<table style="width: 504">
  <tr>
    <td width="101">输入设备IP：</td>
    <td width="168" ><label>
      <input type="text" id="ip" name="bean.ip" class="easyui-validatebox" />
    </label></td>
    <td width="90"><label>
    	<a href="javascript:void(0)"  id="searchDevice"  class="easyui-linkbutton" iconCls="icon-search">查询</a>
    </label></td>
    <td width="159">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="2">设备查询结果</td>
    <td colspan="2">待下发设备列表</td>
  </tr>
  <tr>
    <td height="86"  colspan="2"><label>
      <textarea id="queryresult" name="queryresult" style=" height: 100px" ></textarea>
    </label><input type="button" id="addto" value=">" /></td>
    <td colspan="2"><textarea  id="devices" name="devices"  style=" height: 100px" ></textarea></td>
  </tr>
  <tr>
    <td colspan="4">策略内容</td>
  </tr>
  <tr>
    <td height="48" colspan="4"><textarea name="textarea3" style=" height: 100px;width:400px" ></textarea></td>
  </tr>
</table>
		</form>
		<div id="buttonDiv" style="text-align: center; padding: 5px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="save()">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="saveAndDown()">保存并下发</a>
		</div>
	</body>
</html>
