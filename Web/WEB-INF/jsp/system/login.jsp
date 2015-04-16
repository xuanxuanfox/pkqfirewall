<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.alnie.tc.system.utils.CommonUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/easyui/jquery-1.8.0.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/easyui/themes/cupertino/easyui.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/easyui/themes/icon.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/easyui/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" media="all" href="<%=request.getContextPath()%>/resources/login/css/login.css" />
<html>
	<title></title>
  	<head>
	<SCRIPT>
	$(function(s){
		var SystemName="<%=CommonUtil.GetProConfig("SYSTEM_NAME")%>";
		document.title=SystemName+'-登录';
		
		$("#btn-submit").click(function(){
			var uname=$("#uname").val(),
				pwd=$("#passwd").val(),
				plength=pwd.length,
				nlength=uname.length;
			
			if (nlength==0){
				$.messager.alert("提示","请输入用户名！"); 
				return;
			}
			if (plength==0){
				$.messager.alert("提示","请输入密码！"); 
				return;
			}
			$.messager.progress({ 
				title:'请稍后', 
				msg:"系统登录中，请稍候..."
			}); 
			setTimeout("login()", 1000);
		});
		$("#btn-reset").click(function(){
			$("#uname").val("");
			$("#passwd").val("");
		});
	})

	var login=function(){
		var $params="bean.operatorName="+$("#uname").val()+"&bean.password="+$("#passwd").val();
		$.ajax({
			url:"<%=request.getContextPath()%>/system/loginAction.action",
			data: $params,
			type:'POST',
		    async:false,
		    dataType: 'json',
			success:function(_data){
				var result_code=_data.result_code;
            	if(result_code==0){
            		$.messager.alert("提示",_data.uncut_result_msg); 
            	}else{
            		window.location.href="<%=request.getContextPath()%>/system/index.action";
           		}
           		$.messager.progress('close');
			},
			error: function(_data){
				$.messager.alert("提示",_data.uncut_result_msg); 
				$.messager.progress('close');
			}
		});
	}
</SCRIPT>
</head>
<body leftmargin="0" topmargin="0" oncontextmenu="return false;">
<table width="100%" class="main"  cellpadding="0" cellspacing="0">
  <tr>
    <td>
      <div class="login">
        <div class="top">
          <div class="logo"></div>
        </div>
        <table width="468" cellpadding="0" cellspacing="0">
          <tr>
            <td width="282" style="padding-top:17px;">
              <table width="100%" cellpadding="0" cellspacing="0">
                </tr>
                <tr>
                  <td align="right" height="27">账&nbsp;&nbsp;号:&nbsp;</td>
                  <td align="left" width="161">
                    <input name="bean.operatorName" tabindex="1"  id="uname" type="text" value="${operatorName}" style="width: 100%"/>
                  </td>
                </tr>
                <tr>
                  <td align="right" height="27">密&nbsp;&nbsp;码:&nbsp;</td>
                  <td align="left" width="161">
                    <input name="bean.password" tabindex="2"  id="passwd" type="password" value="" style="width: 100%"/>
                  </td>
                </tr>
              </table>
            </td>
            <td style="padding-top:30px;">
              <table width="100%" cellpadding="0" cellspacing="0">
                <tr>
                  <td align="center" height="29">
                    <input id="btn-submit" name="submit" type="submit" value="登&nbsp;&nbsp;录" class="button" />
                  </td>
                </tr>
                <tr>
                  <td align="center" height="29">
                    <input id="btn-reset" name="reset" type="button" value="重&nbsp;&nbsp;置" class="button" />
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
        <table width="100%" cellpadding="0" cellspacing="0" style="margin-top:28px;">
          <tr>
            <td align="center"><%=CommonUtil.GetProConfig("CONPYRIGHT")%></td>
          </tr>
        </table>
      </div>
      <!--login -->
    </td>
  </tr>
</table>
</body>
</html>
