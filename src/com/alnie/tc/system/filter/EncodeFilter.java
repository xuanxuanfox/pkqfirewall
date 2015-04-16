package com.alnie.tc.system.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
/**
 * 编码过滤器.
 * Comments: WEB管理系统
 * Author：Alnie
 * Create Date： 05-31-2011
 * Version: V1.0.0
*/
public class EncodeFilter implements Filter {
	Logger logger = Logger.getLogger(EncodeFilter.class);
	private String encode = ""; 
	/**
	 * 过滤器初始化.
	 */
	public void init(FilterConfig config) throws ServletException {
		encode = config.getInitParameter("encode"); 
	}

	/**
	 * 过滤器功能.
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc)
			throws IOException, ServletException {
		
		req.setCharacterEncoding(encode);
		res.setCharacterEncoding(encode);
		fc.doFilter(req, res);
		
	}

	/**
	 * 过滤器销毁.
	 */
	public void destroy() {

	}

}
