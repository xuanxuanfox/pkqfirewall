<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/resources/inc.jsp"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>修改密码</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
</head>
 
<body>
	<div style="padding:10px 0 10px 60px">
	    <form id="ff" method="post">
	    	<input type="hidden" id="operator_id" name="bean.operator_id" value="<%=loginOperator.getOperatorId()%>"/>
			<input type="hidden" id="password" name="bean.password" value="<%=loginOperator.getPassword()%>"/>
			<input type="hidden" id="operator_name" name="bean.operator_name" value="<%=loginOperator.getOperatorName()%>"/>
	    	<table>
	    		<tr>
	    			<td><span class="s">*</span>新密码:</td>
	    			<td><input id="password1"  value="" class="easyui-validatebox" type="password"  data-options="required:true,missingMessage:'请输入新密码!'"></input></td>
	    		</tr>
	    		<tr>
	    			<td><span class="s">*</span>确认密码:</td>  
	 			<td><input id="password2" value="" class="easyui-validatebox" type="password"  data-options="required:true,missingMessage:'请输入确认密码!',validType:'confrmPwd'"></input></td>
	    		</tr>
	    	</table>
	    	
	    </form>
    </div>
    <div style="text-align:center;padding:5px">
    	<a href="javascript:void(0)"  id="btnSubmit" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitForm()">提交</a>
    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="clearForm()">取消</a>
    </div>
	<script>
	     $.extend($.fn.validatebox.defaults.rules, {
		    confrmPwd: {
		        validator: function(value, param) {
		            return value==$('#password1').val();  
		        },
		        message: '两次密码输入不一致!'  
		    }
		});
	
		function submitForm(){
			$('#ff').form('submit', { 
			    url:RootPath+'/operator/saveModPwd.action',  
			    onSubmit: function(){
			        if(!$('#password1').validatebox('isValid')) {
						return false;
					 }
					 
					 if(!$('#password2').validatebox('isValid')) {
						return false;
					 }
			      $('#password').val($('#password1').val());
			      $('#btnSubmit').linkbutton('disable');
			    },  
			    success:function(_data){
			        var data=jQuery.parseJSON(_data);
			        if(data.result_code=='1'){
		        		showMsg("密码修改成功！",null,null,function(){
		        			parent.$('#winframe').window('close');
		        		});
		        	} else {
			              showMsg(data.result_msg);
			              $('#btnSubmit').linkbutton('enable');
			         } 
			       }  
			}); 
		}
		
		function clearForm() {
			parent.$('#winframe').window('close');
		}
	</script>
</body>
</html>
