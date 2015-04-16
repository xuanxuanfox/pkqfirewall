package com.alnie.tc.action;

import com.alnie.tc.po.AjaxResult;
import com.alnie.tc.system.common.BaseAction;

/**
* 
* CopyRright (c)2014: alnie
* Project:
* Comments: 
* Author： Alnie
* Create Date： Feb 14, 2014
* Version: V1.0.0
*/
public class LoginAction extends BaseAction{

	public String Login()throws Exception{
		logger.info("开始登陆...");
		try {
			String checkResult=(String)service.execNoTrans("CheckLogin", getBean());
			logger.info(checkResult);
			if("登陆成功".equals(checkResult)){
				logger.info("登陆成功!");
				this.ajaxData=new AjaxResult();
			}else{
				logger.info("登陆失败!");
				this.ajaxData=new AjaxResult(AjaxResult.RESULT_CODE_FAIL,checkResult);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			this.ajaxData=new AjaxResult(AjaxResult.RESULT_CODE_FAIL,e.getMessage());
		}
		return AJAXDATA;
	}
	public String Logout()throws Exception{
		logger.info("登出成功!");
		try {
			this.ajaxData=(AjaxResult)service.execNoTrans("Logout");
		}catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			this.ajaxData=new AjaxResult(AjaxResult.RESULT_CODE_FAIL,e.getMessage());
		}
		
		return LOGIN;
	}
}
