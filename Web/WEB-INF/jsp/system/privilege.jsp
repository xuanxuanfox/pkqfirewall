<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/resources/inc.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>权限配置</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
</head>
 
<body>
	<table align="center">
		<tr>
			<td>
				系统角色:<input id="roleId" name="bean.roleId" class="easyui-combobox" data-options="required:true">
			</td>
		</tr>
		<tr>
			<td>
				<div id="target" style="border:1px solid #ccc;width:300px;height:400px;margin:5px;">  
				    <ul id="tree">
				</div>  
				
		   	</td>
		</tr>
		<tr>
			<td>
				<div style="text-align:center;padding:5px">
			    	<a href="javascript:void(0)" id="btnSubmit" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitValue()">提交</a>
			    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="resetValue()">重置</a>
			   	</div>
			</td>
		</tr>
	</table>
    
	<script>
		$('#roleId').combobox({
		    valueField:'role_id', 
		    textField:'role_name', 
		    panelHeight:'auto',
		    editable:false,
		    value:'${bean.role_id}',
		    url:RootPath+'/system/systemRole.action',
		    onSelect: function(record) {
		    	$('#tree').tree({
				    url:RootPath+'/system/systemRolePrivilege.action?bean.roleId=' + $('#roleId').combobox('getValue')
				});
		    },
		    onLoadSuccess:function(){
				var data = $('#roleId').combobox('getData');
				 if (data.length > 0) {
                       $('#roleId').combobox('select',data[0].role_id);
                   }
			}
		}); 
		
		$('#tree').tree({
		    url:RootPath+'/system/systemRolePrivilege.action?bean.roleId=' + $('#roleId').combobox('getValue'),
		    animate: true,
		    checkbox: true,
		    cascadeCheck:false
		}); 
		
		function submitValue() {
			var nodes = $('#tree').tree('getChecked');
			var privileges = [];
			for(var i=0; i< nodes.length; i++) {
				privileges.push(nodes[i].id);
			}
			var params = {
				'bean.roleId':$('#roleId').combobox('getValue'),
				'bean.privilegeStr':privileges.join('\6')
			}
			$.ajax({
				type: 'post',
				url: RootPath+'/system/systemRolePrivilegeAdd.action',
				data: params,
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
			})
		}
		
		function resetValue() {
			$('#tree').tree({
			    url:RootPath+'/system/systemRolePrivilege.action?bean.roleId=' + $('#roleId').combobox('getValue'),
			    animate: true,
			    checkbox: true
			}); 
		}
	</script>
</body>
</html>
