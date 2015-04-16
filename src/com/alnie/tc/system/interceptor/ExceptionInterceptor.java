package com.alnie.tc.system.interceptor;


import org.apache.log4j.Logger;

import com.alnie.tc.system.exception.SystemException;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ExceptionInterceptor extends AbstractInterceptor {
	private Logger logger = Logger.getLogger(ExceptionInterceptor.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		try {
			String result = invocation.invoke();
			return result;
		}  catch (Exception ex) {
			logger.error("系统产生异常！"+ex.getMessage());
			ex.printStackTrace();
			throw new SystemException(ex);
		}
	}
}
