package com.alnie.tc.system.interceptor;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.alnie.tc.po.AjaxResult;
import com.alnie.tc.system.common.BaseAction;
import com.alnie.tc.system.common.Constants;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
public class AjaxLoginInterceptor extends AbstractInterceptor {
	Logger logger = Logger.getLogger(AjaxLoginInterceptor.class);
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		logger.info("开始登陆验证");
		// 如果用户未登录
		if (ServletActionContext.getRequest().getSession().getAttribute(Constants.LOGIN_USER_ID) == null) {
			logger.error("登陆验证失败，您还未登陆或登陆超时，请重新登陆！");
			AjaxResult ajaxData=new AjaxResult(AjaxResult.RESULT_CODE_FAIL,"登陆验证失败，您还未登录或登陆超时，请重新登陆！");
			BaseAction bAction=(BaseAction)invocation.getAction();
			bAction.setAjaxData(ajaxData);
			return BaseAction.AJAXERROR;
		}
		logger.info("登陆验证成功");
		String result = invocation.invoke();
		return result;
	}

}
