package com.alnie.tc.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.PropertyUtils;

import com.pkq.firewall.message.request.AddRuleRequest;
import com.pkq.firewall.message.request.DeleteRuleRequest;
import com.pkq.firewall.message.request.GetDefaultRuleRequest;
import com.pkq.firewall.message.request.GetRulesRequest;
import com.pkq.firewall.message.response.GetDefaultRuleResponse;
import com.pkq.firewall.message.response.GetRulesResponse;
import com.pkq.firewall.message.response.Response;
import com.pkq.firewall.model.Rule;
import com.alnie.tc.network.NetworkOp;
import com.alnie.tc.po.AjaxResult;
import com.alnie.tc.po.Page;
import com.alnie.tc.po.PageData;

import com.alnie.tc.po.SysLogs;
import com.alnie.tc.system.common.BaseService;
import com.alnie.tc.system.common.Constants;
import com.ibatis.sqlmap.client.SqlMapSession;

public class RuleService  extends BaseService{
	public PageData List(Page page,HashMap baseMap)throws Exception{
		PageData pageData=null;
		try {
			//if(baseMap==null)baseMap=new HashMap();
			//baseMap.put("start_row", page.getStart());
			//baseMap.put("end_row", page.getLimit());
			//List<Map<String, Object>> resultList= (List)this.getSqlMapClientTemplate().queryForList("Rule.list",baseMap);
			//改为从主机代理获取
			List<Map<String, Object>> resultList= new ArrayList<Map<String, Object>>();
			String hostIp=(String)baseMap.get("ip");
			String  direction = (String)baseMap.get("direction");
			GetRulesRequest request = new GetRulesRequest();
			request.setHost(hostIp);
			request.setDirection(direction);
			request.setStartRow(page.getStart());
			request.setLimit(page.getLimit());
			GetRulesResponse response = NetworkOp.getRules(request);
			List<Rule> rules = response.getRules();
			Rule rule;
			Map bm;
			for( int i=0;i<rules.size();i++){
				rule = rules.get(i);
				bm = PropertyUtils.describe(rule);
				resultList.add(bm);
			}
			//转换成页面显示所要的格式
			int total_size=response.getRules().size();
			pageData=new PageData(total_size,resultList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			pageData=new PageData(0,null);
		}finally{

		}
		return pageData;
	}

	public AjaxResult New(HashMap baseMap)throws Exception{
		Connection conn = null;//conn
		SqlMapSession session = null;//session
	    Rule rule = new Rule();
	    String hostIp="";
		try {
			
			conn = this.getSqlMapClient().getDataSource().getConnection();//conn
		    conn.setAutoCommit(false);//conn
		    session = this.getSqlMapClient().openSession(conn);//session
		    //与代理通信
		    AddRuleRequest request = new AddRuleRequest();
		    hostIp=(String)baseMap.get("deviceip");
		    request.setHost(hostIp);
		    rule.setDirection((String)baseMap.get("direction"));
		    rule.setAction((String)baseMap.get("action"));
		    rule.setProtocol((String)baseMap.get("protocol"));
		    rule.setPort((String)baseMap.get("port"));
		    rule.setRemoteIp((String)baseMap.get("remoteIp"));
		    rule.setPort((String)baseMap.get("remotePort"));
		    request.setRule(rule);
			//与代理通信
		    Response response = NetworkOp.addRule(request);
		    //写日志
			session.insert("system.newLog", new SysLogs(Constants.TRANS_LOG_RULE,"新增策略","策略：host"+hostIp + rule.toString()));
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			if(null != conn)conn.rollback();//conn
			this.getSqlMapClientTemplate().insert("system.newLog",new SysLogs(Constants.TRANS_LOG_RULE,0,"新增策略", e.getMessage() + ", 策略：host"+hostIp + rule.toString() ));
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
			DeleteRuleRequest request = new DeleteRuleRequest();
			request.setHost(baseMap.get("deviceip").toString());
			request.setId(baseMap.get("id").toString());
			Response response = NetworkOp.deleteRule(request);
			conn = this.getSqlMapClient().getDataSource().getConnection();//conn
		    conn.setAutoCommit(false);//conn
		    
			session.insert("system.newLog", new SysLogs(Constants.TRANS_LOG_RULE,"删除策略","策略："+baseMap.get("id")));
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			if(null != conn)conn.rollback();//conn
			this.getSqlMapClientTemplate().insert("system.newLog",new SysLogs(Constants.TRANS_LOG_RULE,0,"删除策略",e.getMessage()));
			return new AjaxResult(AjaxResult.RESULT_CODE_FAIL,e.getMessage());
		}finally{
			if(null != session)session.close();//session
			if(null != conn)conn.close();//conn
		}
		return new AjaxResult();
	}
	
	public AjaxResult getDefaultRule(HashMap baseMap)throws Exception{
	    String hostIp="";
		AjaxResult result =  new AjaxResult();
		GetDefaultRuleRequest request = new GetDefaultRuleRequest();
		hostIp=(String)baseMap.get("deviceip");
	    request.setHost(hostIp);
	    request.setDirection((String)baseMap.get("direction"));
		try {
		GetDefaultRuleResponse response = NetworkOp.getDefaultRule(request);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			this.getSqlMapClientTemplate().insert("system.newLog",new SysLogs(Constants.TRANS_LOG_RULE,0,"获取默认策略", e.getMessage() ));
			return new AjaxResult(AjaxResult.RESULT_CODE_FAIL,e.getMessage());
		}
		return result;
		
	}
}
