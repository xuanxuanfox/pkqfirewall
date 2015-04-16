package com.alnie.tc.system.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alnie.tc.po.SysOperator;
import com.alnie.tc.system.exception.LogoutException;
import com.opensymphony.xwork2.ActionContext;

/**
* 
* CopyRright (c)2014: alnie
* Project:
* Comments: 
* Author： Alnie
* Create Date： Feb 14, 2014
* Version: V1.0.0
*/
public class Constants {
	//登录用户
	public final static String LOGIN_USER_ID="tc.login_user_id";
	public final static String COOKIE_USER_ID="cookie.login_user_id";
	
	public final static int SUCESS_CODE=0; 
	public final static String SUCESS_MESSAGE="sucess"; 
	public final static int FALSE_CODE=-1; 
	
	public final static long TRANS_LOG_LOGIN=0;//"login";
	public final static long TRANS_LOG_LOGOUT=1;//"logout";
	public final static long TRANS_LOG_OPERATOR=2;//"operator";
	public final static long TRANS_LOG_DEVICE=3;//"operator";
	public final static long TRANS_LOG_RESOURCE=4;//"operator";
	public final static long TRANS_LOG_CHECKBEHAVIOR=5;//"operator";
	public final static long TRANS_LOG_CHECKRESULT=6;//"operator";
	public final static long TRANS_LOG_RULE=7;//"operator";
	
	public static final char[] ANEWLINE = {3};
	public static final char[] AFIELD = {2};
    public static final char[] ALINE = {1};
	public static final String TABLE_FIELD_FLAG = new String(AFIELD); //表格控件字段分割符
    public static final String TABLE_ROW_FLAG = new String(ALINE); //表格控件记录分割符
    public static final String TABLE_NEWROW_FLAG = new String(ANEWLINE); //表格控件记录分割符
    
	public final static String ANNEX_PATH="ANNEX_PATH";
    public final static String EXPORT_PATH = "EXPORT_PATH";
    public final static String UPLOAD_PATH = "UPLOAD_PATH";
    public final static String EMAIL_NAME = "EMAIL_NAME";
    public final static String EMAIL_PWD = "EMAIL_PWD";
    public final static String EMAIL_HOST_NAME = "EMAIL_HOST_NAME";
    public final static String EMAIL_CHARSET = "EMAIL_CHARSET";
    public final static String INTERNET_EMAIL_NAME = "INTERNET_EMAIL_NAME";
    public final static String INTERNET_EMAIL_PWD = "INTERNET_EMAIL_PWD";
    public final static String INTERNET_EMAIL_HOST_NAME = "INTERNET_EMAIL_HOST_NAME";
    public final static String INTERNET_EMAIL_CHARSET = "INTERNET_EMAIL_CHARSET";
    public final static String DEFAULT_PASSWORD = "DEFAULT_PASSWORD";
    public final static long ROLE_C = 4;
    public final static long ROLE_I = 5;
    public final static String ANNEX_UPLOAD_ENCODING = "ANNEX_UPLOAD_ENCODING";
    public final static String SYSTEM_ID = "SYSTEM_ID";
    public final static String SEPARATOR = "\n";
    public static final String SYSTEM_FILE="/config/system.properties";
    
    public static Object getAC(String beanName){
		return getACBase(getWebRequest().getSession().getServletContext(),beanName);
	}
    public static Object getACBase(ServletContext sc,String beanName){
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
		return ctx.getBean(beanName);
	}
	public static HttpServletRequest getWebRequest(){
		HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);
		return request;
	}
	public static HttpServletResponse getWebRespone(){
		HttpServletResponse response = (HttpServletResponse)ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_RESPONSE);
		return response;
	}
	public static HttpSession getWebSession(){
		return getWebRequest().getSession();
	}
	public static String GetRealPath(String path){
		String realpath = getWebSession().getServletContext().getRealPath(path);
		realpath = realpath.replace('\\', '/');
		return realpath;
	}
	public static void GoToLogout(HttpServletRequest request,HttpServletResponse response) throws IOException{
		PrintWriter out= response.getWriter();
		String strurl = "top.window.location.href=\""+request.getContextPath()+ URL_LOGOUT+"\"";
		out.print("<script>");
		out.print(strurl);			
		out.print("</script>");
		out.flush();
		out.close();
	}
	public static String getDateTime(String fromFormat){
    	SimpleDateFormat  format = new SimpleDateFormat (fromFormat);
    	TimeZone zone = TimeZone.getTimeZone("GMT+8");
    	format.setTimeZone(zone);
    	Date myDate = new Date();
    	return format.format(myDate);
    } 
	
	//页面地址
	public static final String URL_MESSAGE="/system/message.jsp";
	public static final String URL_INDEX="/system/index.jsp";
	public static final String URL_MAIN="/system/main.jsp";
	public static final String URL_LOGOUT="/system/logout.jsp";
	public static final String URL_LOGIN="/login.jsp";
	
	/**
	 * 返回值.
	 * @param sucCnt
	 * @param errCnt
	 * @param err_hint
	 * @return
	 */
	public static String GetResultMsg(int totCnt,int sucCnt,int errCnt,String err_hint){
		StringBuilder msg=new StringBuilder();
		msg.append(Constants.SEPARATOR);
		msg.append("操作数据共 ").append(totCnt).append(" 条！其中：").append(Constants.SEPARATOR);
		msg.append("操作成功数据共 ").append(sucCnt).append(" 条！").append(Constants.SEPARATOR);
		msg.append("操作失败数据共 ").append(errCnt).append(" 条！").append(Constants.SEPARATOR);
		if(errCnt>0)msg.append("操作失败数据列表：").append(Constants.SEPARATOR).append(err_hint);
		return msg.toString();
	}
	public static SysOperator GetLoginOperator(HttpSession session)throws Exception{
		if(session==null)session=getWebSession();
		Object object = session.getAttribute(Constants.LOGIN_USER_ID);
		if(object==null){
			throw new LogoutException("登陆验证失败，您还未登录或登录超时，请重新登录！");
		}
		else return (SysOperator)object;
	}
	public static long GetLoginOperatorId()throws Exception{
		SysOperator opt=GetLoginOperator(null);
		return opt.getOperatorId();
	}
	
	/**
	 * 时间格式yyyy-MM-dd HH:mm:ss
	 */
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 时间格式yyyyMMddHHmmss
	 */
	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	/**
	 * 时间格式yyyy-MM-dd HH:mm:ss.S
	 */
	public static final String YYYY_MM_DD_HH_MM_SS_S = "yyyy-MM-dd HH:mm:ss.S";

	/**
	 * ORACLE时间格式yyyy-MM-dd HH24:mi:ss
	 */
	public static final String ORACLE_YYYY_MM_DD_HH24_MI_SS = "yyyy-MM-dd HH24:mi:ss";

	/**
	 * ORACLE时间格式yyyyMMdd HH24:mi:ss
	 */
	public static final String ORACLE_YYYYMMDDHH24MISS = "yyyyMMdd HH24:mi:ss";

	/**
	 * 时间格式yyyyMMdd
	 */
	public static final String YYYYMMDD = "yyyyMMdd";

	/**
	 * 系统文件分隔符
	 */
//	public static final String SEPARATOR = System.getProperty("file.separator");

	/**
	 * 服务应用根目录
	 */
	public static final String ROOT = System.getProperty("user.dir");

	/**
	 * UTF-8编码
	 */
	public static final String UTF8 = "UTF-8";

	/**
	 * ISO-8859-1编码
	 */
	public static final String ISO8859_1 = "ISO-8859-1";

	/**
	 * GB2312编码
	 */
	public static final String GB2312 = "GB2312";

	/**
	 * GBK编码
	 */
	public static final String GBK = "GBK";

	/**
	 * IO流使用时的buffer size
	 */
	public static final int IO_BUFFER_SIZE = 1024;

	/**
	 * IO流文件读取结束标志位
	 */
	public static final int END_OF_FILE = -1;

	/**
	 * 公共成功页面或失败页面结果集合的key
	 */
	public static final String RESULT_FORM = "resultForm";

	/**
	 * 升序排序
	 */
	public static final String ORDER_ASC = "ASC";

	/**
	 * 降序排序
	 */
	public static final String ORDER_DESC = "DESC";

	/**
	 * 页面表单文件上传控件的默认名称 upload,<br>
	 * 即 <input name="upload" id="upload" type="file" /> 的name名称
	 */
	public static final String UPLOAD_FORM_FILE_DEFAULT_NAME = "upload";

	/**
	 * 文件上传的默认目录 /upload
	 */
	public static final String UPLOAD_FILE_DEFAULT_PATH = "uploads";

	/**
	 * 文件下载的默认目录
	 */
	public static final String DOWNLOAD_FILE_DEFAULT_PATH = "download";

	/**
	 * 下载文件是否支持在线打开方式
	 */
	public static final boolean IS_ONLINE = false;

	/**
	 * 公共失败页面
	 */
	public static final String COMM_FAIL_PAGE = "common.fail";

	/**
	 * 公共成功页面
	 */
	public static final String COMM_SUCCESS_PAGE = "common.success";

	/**
	 * WEBSERVICE服务名
	 */
	public static final String WEB_SERVICE_NAME = "serviceName";

	/**
	 * 默认库路由
	 */
	public static final String DS_DEFAULT = "DS_DEFAULT";

	/**
	 * SESSION关键字,用户信息
	 */
	public static final String USER_INFO = "USER_INFO";
	
	/**
	 * SESSION关键字,用户权限
	 */
	public static final String USER_PRIVILEGE = "USER_PRIVILEGE";
	
	public static final String AUTO_REFULSH_TIME = "AUTO_REFULSH_TIME";
	public static final String AUTO_REFULSH_TYPE = "AUTO_REFULSH_TYPE";
}
