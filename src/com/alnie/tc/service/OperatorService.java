package com.alnie.tc.service;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.alnie.tc.po.AjaxResult;
import com.alnie.tc.po.Page;
import com.alnie.tc.po.PageData;
import com.alnie.tc.po.SysLogs;
import com.alnie.tc.system.common.BaseService;
import com.alnie.tc.system.common.Constants;
import com.alnie.tc.system.utils.CommonUtil;
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
public class OperatorService extends BaseService{
	public PageData List(Page page,HashMap baseMap)throws Exception{
		PageData pageData=null;
		try {
			if(baseMap==null)baseMap=new HashMap();
			baseMap.put("start_row", page.getStart());
			baseMap.put("end_row", page.getLimit());
			List<Map<String, Object>> resultList= (List)this.getSqlMapClientTemplate().queryForList("operator.list",baseMap);
			int total_size=ParseInteger(this.getSqlMapClientTemplate().queryForObject("operator.total",baseMap));
			pageData=new PageData(total_size,resultList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw e;
		}finally{

		}
		return pageData;
	}
	public AjaxResult New(HashMap baseMap)throws Exception{
		Connection conn = null;//conn
		SqlMapSession session = null;//session
		try {
			int total_size=ParseInteger(this.getSqlMapClientTemplate().queryForObject("operator.newCheck",baseMap));
			if(total_size>0){
				return new AjaxResult(AjaxResult.RESULT_CODE_FAIL,"当前账号已存在");
			}
			conn = this.getSqlMapClient().getDataSource().getConnection();//conn
		    conn.setAutoCommit(false);//conn
		    session = this.getSqlMapClient().openSession(conn);//session
			PasswordED od = new PasswordED();
			baseMap.put("status", 1);
			baseMap.put("password", od.encPassword(CommonUtil.GetProConfig(Constants.DEFAULT_PASSWORD)));
			int operator_id=(Integer)session.insert("operator.new", baseMap);
			baseMap.put("operator_id", operator_id);
			//添加用户角色
			session.delete("operator.delOptRole", baseMap);
			String role_id_str=ParseString(baseMap.get("role_id"));
			String[] role_id_ary=role_id_str.split(",");
			Map map=new HashMap();
			map.put("operator_id", operator_id);
			for(String role_id:role_id_ary){
				map.put("role_id", role_id);
				session.insert("operator.newOptRole", map);
			}
			session.insert("system.newLog", new SysLogs(Constants.TRANS_LOG_OPERATOR,"新增账号","账号："+baseMap.get("operator_name")));
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			if(null != conn)conn.rollback();//conn
			this.getSqlMapClientTemplate().insert("system.newLog",new SysLogs(Constants.TRANS_LOG_OPERATOR,0,"新增账号",e.getMessage()));
			return new AjaxResult(AjaxResult.RESULT_CODE_FAIL,e.getMessage());
		}finally{
			if(null != session)session.close();//session
			if(null != conn)conn.close();//conn
		}
		return new AjaxResult();
	}
	public AjaxResult Mod(HashMap baseMap)throws Exception{
		Connection conn = null;//conn
		SqlMapSession session = null;//session
		try {
			int total_size=ParseInteger(this.getSqlMapClientTemplate().queryForObject("operator.modCheck",baseMap));
			if(total_size>0){
				return new AjaxResult(AjaxResult.RESULT_CODE_FAIL,"当前账号已存在");
			}
			conn = this.getSqlMapClient().getDataSource().getConnection();//conn
		    conn.setAutoCommit(false);//conn
		    session = this.getSqlMapClient().openSession(conn);//session
		    session.update("operator.mod", baseMap);
			//添加用户角色
			session.delete("operator.delOptRole", baseMap);
			String role_id_str=ParseString(baseMap.get("role_id"));
			String[] role_id_ary=role_id_str.split(",");
			Map map=new HashMap();
			map.put("operator_id", baseMap.get("operator_id"));
			for(String role_id:role_id_ary){
				map.put("role_id", role_id);
				session.insert("operator.newOptRole", map);
			}
		    session.insert("system.newLog", new SysLogs(Constants.TRANS_LOG_OPERATOR,"修改账号","账号："+baseMap.get("operator_name")));
		    conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			if(null != conn)conn.rollback();//conn
			this.getSqlMapClientTemplate().insert("system.newLog",new SysLogs(Constants.TRANS_LOG_OPERATOR,0,"修改账号",e.getMessage()));
			return new AjaxResult(AjaxResult.RESULT_CODE_FAIL,e.getMessage());
		}finally{
			if(null != session)session.close();//session
			if(null != conn)conn.close();//conn
		}
		return new AjaxResult();
	}
	public AjaxResult Del(HashMap baseMap)throws Exception{
		Connection conn = null;//conn
		SqlMapSession session = null;//session
		try {
			conn = this.getSqlMapClient().getDataSource().getConnection();//conn
		    conn.setAutoCommit(false);//conn
		    session = this.getSqlMapClient().openSession(conn);//session
			baseMap.put("status", 0);
			session.update("operator.del", baseMap);
			session.insert("system.newLog", new SysLogs(Constants.TRANS_LOG_OPERATOR,"删除账号","账号："+baseMap.get("operator_name")));
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			if(null != conn)conn.rollback();//conn
			this.getSqlMapClientTemplate().insert("system.newLog",new SysLogs(Constants.TRANS_LOG_OPERATOR,0,"删除账号",e.getMessage()));
			return new AjaxResult(AjaxResult.RESULT_CODE_FAIL,e.getMessage());
		}finally{
			if(null != session)session.close();//session
			if(null != conn)conn.close();//conn
		}
		return new AjaxResult();
	}
	public AjaxResult ModPwd(HashMap baseMap)throws Exception{
		Connection conn = null;//conn
		SqlMapSession session = null;//session
		try {
			conn = this.getSqlMapClient().getDataSource().getConnection();//conn
		    conn.setAutoCommit(false);//conn
		    session = this.getSqlMapClient().openSession(conn);//session
		    PasswordED od = new PasswordED();
			baseMap.put("password", od.encPassword(ParseString(baseMap.get("password"))));
		    session.update("operator.modPwd", baseMap);
		    session.insert("system.newLog", new SysLogs(Constants.TRANS_LOG_OPERATOR,"修改密码","账号："+baseMap.get("operator_name")));
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			if(null != conn)conn.rollback();//conn
			this.getSqlMapClientTemplate().insert("system.newLog",new SysLogs(Constants.TRANS_LOG_OPERATOR,0,"修改密码",e.getMessage()));
			return new AjaxResult(AjaxResult.RESULT_CODE_FAIL,e.getMessage());
		}finally{
			if(null != session)session.close();//session
			if(null != conn)conn.close();//conn
		}
		return new AjaxResult();
	}
	public HashMap Det(HashMap baseMap)throws Exception{
		PageData pageData=null;
		try {
			HashMap<String, Object> map= (HashMap)this.getSqlMapClientTemplate().queryForObject("operator.det",baseMap);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw e;
		}finally{

		}
	}
	public AjaxResult ModMyinfo(HashMap baseMap)throws Exception{
		Connection conn = null;//conn
		SqlMapSession session = null;//session
		try {
			int total_size=ParseInteger(this.getSqlMapClientTemplate().queryForObject("operator.modCheck",baseMap));
			if(total_size>0){
				return new AjaxResult(AjaxResult.RESULT_CODE_FAIL,"当前账号已存在");
			}
			conn = this.getSqlMapClient().getDataSource().getConnection();//conn
		    conn.setAutoCommit(false);//conn
		    session = this.getSqlMapClient().openSession(conn);//session
		    session.update("operator.mod", baseMap);
		    session.insert("system.newLog", new SysLogs(Constants.TRANS_LOG_OPERATOR,"修改我的信息","账号："+baseMap.get("operator_name")));
		    conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			if(null != conn)conn.rollback();//conn
			this.getSqlMapClientTemplate().insert("system.newLog",new SysLogs(Constants.TRANS_LOG_OPERATOR,0,"修改我的信息",e.getMessage()));
			return new AjaxResult(AjaxResult.RESULT_CODE_FAIL,e.getMessage());
		}finally{
			if(null != session)session.close();//session
			if(null != conn)conn.close();//conn
		}
		return new AjaxResult();
	}
}
