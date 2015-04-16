package com.alnie.tc.service;
import java.sql.Connection;
import java.util.HashMap;
import com.alnie.tc.po.AjaxResult;
import com.alnie.tc.po.SysLogs;
import com.alnie.tc.po.SysOperator;
import com.alnie.tc.system.common.BaseService;
import com.alnie.tc.system.common.Constants;
import com.alnie.tc.system.utils.PasswordED;
import com.ibatis.sqlmap.client.SqlMapSession;
/**
* 
* CopyRright (c)2014: alnie
* Project:
* Comments: 
* Author： Alnie
* Create Date： Feb 14, 2014
* Version: V1.0.0
*/
public class LoginService extends BaseService{
	public String CheckLogin(HashMap bean)throws Exception{
		String checkMsg=null;
		String operatorName=ParseString(bean.get("operatorName"));
		String passwd=ParseString(bean.get("password")); 
		String remember=ParseString(bean.get("remember")); 
		
		if(operatorName==null||"".equals(operatorName))return "登陆账号不可为空！";
		if(passwd==null||"".equals(passwd))return "密码不可为空！";
		SysOperator eOperator= (SysOperator)this.getSqlMapClientTemplate().queryForObject("login.getSysOperator",operatorName);
		if(eOperator==null){
			return "该登陆账号不存在！";
		}else{
			if(eOperator.getStatus()!=1)return "该登陆账号已被禁用，请与系统管理员联系！";
			PasswordED od = new PasswordED();
			if(!passwd.equals(od.decPassword(eOperator.getPassword()))){
				this.getSqlMapClientTemplate().insert("system.newLoginLog",new SysLogs(Constants.TRANS_LOG_LOGIN,eOperator.getOperatorId(),0,"用户密码错误"));
				return "用户密码错误，请重新输入！";
			}else{
				//效验通过
				AjaxResult ar = Login(eOperator);
				if(ar.isSuccess()){
					Constants.getWebSession().setAttribute(Constants.LOGIN_USER_ID, eOperator);
					return "登陆成功";
				}else{
					return ar.getUncut_result_msg().toString();
				}
			}
		}
	}
	public AjaxResult Login(SysOperator eOperator)throws Exception{
		Connection conn = null;//conn
		SqlMapSession session = null;//session
		try {
			conn = this.getSqlMapClient().getDataSource().getConnection();//conn
		    conn.setAutoCommit(false);//conn
		    session = this.getSqlMapClient().openSession(conn);//session
			session.update("operator.modLastLoginTime", eOperator);
			session.insert("system.newLoginLog", new SysLogs(Constants.TRANS_LOG_LOGIN,eOperator.getOperatorId(),1,""));
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			if(null != conn)conn.rollback();//conn
			this.getSqlMapClientTemplate().insert("system.newLoginLog",new SysLogs(Constants.TRANS_LOG_LOGIN,eOperator.getOperatorId(),0,e.getMessage()));
			throw e;
		}finally{
			if(null != session)session.close();//session
			if(null != conn)conn.close();//conn
		}
		return new AjaxResult();
	}
	public AjaxResult Logout()throws Exception{
		try {
			this.getSqlMapClientTemplate().insert("system.newLoginLog",new SysLogs(Constants.TRANS_LOG_LOGOUT,"登出","人员："+Constants.GetLoginOperator(null).getOperatorRealname()));
			Constants.getWebSession().removeAttribute(Constants.LOGIN_USER_ID);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			this.getSqlMapClientTemplate().insert("system.newLoginLog",new SysLogs(Constants.TRANS_LOG_LOGOUT,0,"登出",e.getMessage()));
			throw e;
		}finally{
		}
		return new AjaxResult();
	}
}
