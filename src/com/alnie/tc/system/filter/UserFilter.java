package com.alnie.tc.system.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.alnie.tc.system.common.Constants;


/**
* 
* CopyRright (c)2014: alnie
* Project:
* Comments: 
* Author： Alnie
* Create Date： Feb 14, 2014
* Version: V1.0.0
*/
public class UserFilter implements Filter {
	Logger logger = Logger.getLogger(UserFilter.class);
	private String excepturl = ""; 
	/**
	 * 用户登入过滤器初始化.
	 */
	public void init(FilterConfig config) throws ServletException {
		excepturl = config.getInitParameter("excepturl"); 
	}
	/**
	 * 当判断Session中没有用户信息，			
	 * 即表示该用户还未登录，将用户踢回登录页面.
	 * @param request
	 * @param response
	 * @param chain
	 * 
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain fc) throws IOException, ServletException {

		HttpServletRequest svRequest = (HttpServletRequest) request;
		HttpServletResponse svResponse = (HttpServletResponse) response;
		HttpSession session = svRequest.getSession();
		String uri = svRequest.getRequestURI();
		if(uri.contains(excepturl)){
			fc.doFilter(svRequest, svResponse);
		}else{
			if (session.getAttribute(Constants.LOGIN_USER_ID) != null ) {
				fc.doFilter(svRequest, svResponse);
			} else {
				logger.error("登陆验证失败，详细：未登录！请重新登陆。");
				
				String ajaxTag = svRequest.getHeader("Request-By");//ExtJs   
	            if(ajaxTag == null || !ajaxTag.trim().equalsIgnoreCase("ExtJs")){   //普通请求
	            	//svResponse.sendRedirect(svRequest.getContextPath()+ Constants.URL_LOGOUT);   
	            	Constants.GoToLogout(svRequest,svResponse);
	            }else{   //ExtJs请求
	            	svResponse.addHeader("sessionstatus", "timeout");    
	                Map<String, Object> result = new HashMap<String, Object>();   
	                result.put("success", false);   
	                result.put("timeout",true);   
	                //result.put("redirectUri", svRequest.getContextPath()+ Constants.URL_LOGOUT);   
	                PrintWriter out = svResponse.getWriter();   
	                out.print(JSONObject.fromObject(result));   
	                out.flush();   
	                out.close();   
	            }  
	            
				// 当判断Session中没有用户信息，			
				// 即表示该用户还未登录，将用户踢回登录页面.	
//				PrintWriter out= response.getWriter();
//				String strurl = "top.window.location.href=\""+svRequest.getContextPath()+ Constants.URL_LOGOUT+"\"";
//				out.print("<script>");
//				out.print(strurl);			
//				out.print("</script>");
//				out.flush();
//				out.close();
				//throw new RuntimeException("对不起，您未登录或登录超时，请重新登录！");
			}
		}
	}
	/**
	 * 用户登入过滤器销毁.
	 */
	public void destroy() {

	}

}
