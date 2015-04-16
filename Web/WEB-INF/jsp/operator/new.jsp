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
			$('#role_id').combobox({
			    valueField:'role_id', 
			    textField:'role_name', 
			    panelHeight:'auto',
			    editable:false,
			    value:'${bean.role_id}',
			    multiple:true,
			    url:RootPath+'/system/systemRole.action',
			    onLoadSuccess:function(){
					var data = $('#role_id').combobox('getData');
					if (data.length > 0) {
	           			$('#role_id').combobox('select',data[0].role_id);
	            	}
	          		closeLoading();
				}
			}); 
		}
		function save(){
			var re=$("#addfrm").form('validate');
		    if(re){
            	$.messager.confirm('提示', '确定保存?', function(r){
					if (r) {
            			saveFunction('/operator/saveNew.action');
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
						<span class=s>*</span>账号:
					</td>
					<td width="30%">
						<input id="operator_name" name="bean.operator_name" class="easyui-validatebox" type="text" data-options="required:true" style="width: 100%;"/>
					</td>
					<td width="20%" align="right">
						<span class=s>*</span>真实姓名:
					</td>
					<td width="30%">
						<input id="operator_realname" name="bean.operator_realname" class="easyui-validatebox" type="text" data-options="required:true" style="width: 100%;"/>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						手机号:
					</td>
					<td width="30%">
						<input id="mobile" name="bean.mobile" class="easyui-validatebox" type="text" data-options="required:false" style="width: 100%;"/>
					</td>
					<td width="20%" align="right">
						邮箱:
					</td>
					<td width="30%">
						<input id="email" name="bean.email" class="easyui-validatebox" type="text" data-options="required:false" style="width: 100%;"/>
					</td>
				</tr>
				<tr>
					<td width="20%" align="right">
						<span class=s>*</span>系统角色:
					</td>
					<td colspan="3">
						<input id="role_id" name="bean.role_id" class="easyui-combobox" data-options="required:true" style="width: 400;"/><span class=s>（可多选）</span>
					</td>
				</tr>
				<tr>
					<td align="right">
						备注:
					</td>
					<td colspan="3">
						<textarea id="comments" name="bean.comments" style="width: 100%;height: 120px" data-options="required:false"  class="easyui-validatebox"></textarea>
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
