package com.alnie.tc.system.interceptor;

import org.apache.log4j.Logger;
import com.alnie.tc.po.AjaxResult;
import com.alnie.tc.system.common.BaseAction;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
public class AjaxExceptionInterceptor extends AbstractInterceptor {
	private Logger logger = Logger.getLogger(AjaxExceptionInterceptor.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		try {
			String result = invocation.invoke();
			return result;
		} catch (Exception e) {
			logger.error("系统产生AJAX异常");
			e.printStackTrace();
			AjaxResult ajaxData=new AjaxResult(AjaxResult.RESULT_CODE_FAIL,e.getMessage());
			BaseAction bAction=(BaseAction)invocation.getAction();
			bAction.setAjaxData(ajaxData);
			return BaseAction.AJAXERROR;
			//throw new RunException(3,"ExceptionInterceptor","intercept","系统异常",e);
		}
	}
}
