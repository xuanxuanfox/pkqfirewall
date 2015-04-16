package com.alnie.tc.service;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.alnie.tc.po.AjaxResult;
import com.alnie.tc.po.Page;
import com.alnie.tc.po.PageData;
import com.alnie.tc.po.SysLogs;
import com.alnie.tc.system.common.BaseService;
import com.alnie.tc.system.common.Constants;
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
public class CheckbehaviorService extends BaseService{
	public PageData List(Page page,HashMap baseMap)throws Exception{
		PageData pageData=null;
		try {
			if(baseMap==null)baseMap=new HashMap();
			baseMap.put("start_row", page.getStart());
			baseMap.put("end_row", page.getLimit());
			List<Map<String, Object>> resultList= (List)this.getSqlMapClientTemplate().queryForList("checkbehavior.list",baseMap);
			int total_size=ParseInteger(this.getSqlMapClientTemplate().queryForObject("checkbehavior.total",baseMap));
			pageData=new PageData(total_size,resultList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw e;
		}finally{

		}
		return pageData;
	}
	public HashMap Det(HashMap baseMap)throws Exception{
		PageData pageData=null;
		try {
			HashMap<String, Object> map= (HashMap)this.getSqlMapClientTemplate().queryForObject("checkbehavior.det",baseMap);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw e;
		}finally{

		}
	}
	public AjaxResult New(HashMap baseMap)throws Exception{
		Connection conn = null;//conn
		SqlMapSession session = null;//session
		try {
			int total_size=ParseInteger(this.getSqlMapClientTemplate().queryForObject("checkbehavior.newCheck",baseMap));
			if(total_size>0){
				return new AjaxResult(AjaxResult.RESULT_CODE_FAIL,"当前检测行为已存在");
			}
			conn = this.getSqlMapClient().getDataSource().getConnection();//conn
		    conn.setAutoCommit(false);//conn
		    session = this.getSqlMapClient().openSession(conn);//session
		    baseMap.put("id",UUID.randomUUID().toString());
			session.insert("checkbehavior.new", baseMap);
			session.insert("system.newLog", new SysLogs(Constants.TRANS_LOG_CHECKBEHAVIOR,"新增检测行为","检测行为："+baseMap.get("ip")));
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			if(null != conn)conn.rollback();//conn
			this.getSqlMapClientTemplate().insert("system.newLog",new SysLogs(Constants.TRANS_LOG_CHECKBEHAVIOR,0,"新增检测行为",e.getMessage()));
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
			int total_size=ParseInteger(this.getSqlMapClientTemplate().queryForObject("checkbehavior.modCheck",baseMap));
			if(total_size>0){
				return new AjaxResult(AjaxResult.RESULT_CODE_FAIL,"当前检测行为已存在");
			}
			conn = this.getSqlMapClient().getDataSource().getConnection();//conn
		    conn.setAutoCommit(false);//conn
		    session = this.getSqlMapClient().openSession(conn);//session
		    session.update("checkbehavior.mod", baseMap);
		    session.insert("system.newLog", new SysLogs(Constants.TRANS_LOG_CHECKBEHAVIOR,"修改检测行为","检测行为："+baseMap.get("ip")));
		    conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			if(null != conn)conn.rollback();//conn
			this.getSqlMapClientTemplate().insert("system.newLog",new SysLogs(Constants.TRANS_LOG_CHECKBEHAVIOR,0,"修改检测行为",e.getMessage()));
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
			session.update("checkbehavior.del", baseMap);
			session.insert("system.newLog", new SysLogs(Constants.TRANS_LOG_CHECKBEHAVIOR,"删除检测行为","检测行为："+baseMap.get("ip")));
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			if(null != conn)conn.rollback();//conn
			this.getSqlMapClientTemplate().insert("system.newLog",new SysLogs(Constants.TRANS_LOG_CHECKBEHAVIOR,0,"删除检测行为",e.getMessage()));
			return new AjaxResult(AjaxResult.RESULT_CODE_FAIL,e.getMessage());
		}finally{
			if(null != session)session.close();//session
			if(null != conn)conn.close();//conn
		}
		return new AjaxResult();
	}
}
