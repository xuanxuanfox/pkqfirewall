package com.alnie.tc.service;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.alnie.tc.po.AjaxResult;
import com.alnie.tc.po.PageData;
import com.alnie.tc.po.SysLogs;
import com.alnie.tc.system.common.Constants;
import com.ibatis.sqlmap.client.SqlMapSession;

public class AgentService extends SqlMapClientDaoSupport{

	AjaxResult insertUpdateInfoToDb(HashMap baseMap) throws Exception{
		Connection conn = null;//conn
		SqlMapSession session = null;//session
		String msg = "代理版本："+baseMap.get("version");
		try {
			conn = this.getSqlMapClient().getDataSource().getConnection();//conn
		    conn.setAutoCommit(false);//conn
		    session = this.getSqlMapClient().openSession(conn);//session
		    session.insert("AgentVersion.updateOld", baseMap);  //先把之前的状态更新为old
			session.insert("AgentVersion.new", baseMap); //添加新的
			session.insert("system.newLog", new SysLogs(Constants.TRANS_LOG_DEVICE,"新增代理版本",msg)); //记录日志
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			if(null != conn)conn.rollback();//conn
			this.getSqlMapClientTemplate().insert("system.newLog",new SysLogs(Constants.TRANS_LOG_DEVICE,0,"新增代理版本",e.getMessage()));
			return new AjaxResult(AjaxResult.RESULT_CODE_FAIL,e.getMessage());
		}finally{
			if(null != session)session.close();//session
			if(null != conn)conn.close();//conn
		}
		return new AjaxResult();
	}
	
	public PageData selectNewest()throws Exception{
		PageData pageData=null;
		try {
			List<Map<String, Object>> resultList= (List)this.getSqlMapClientTemplate().queryForList("AgentVersion.selectNewest",null);
			int total_size= resultList.size();
			pageData=new PageData(total_size,resultList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw e;
		}finally{

		}
		return pageData;

	}

}

