package com.alnie.tc.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.PropertyUtils;

import sun.misc.BASE64Decoder;
import task.Task;

import com.pkq.firewall.message.request.AddRuleRequest;
import com.pkq.firewall.message.request.DeleteRuleRequest;
import com.pkq.firewall.message.request.GetDefaultRuleRequest;
import com.pkq.firewall.message.request.GetRulesRequest;
import com.pkq.firewall.message.request.UpdateRequest;
import com.pkq.firewall.message.response.GetDefaultRuleResponse;
import com.pkq.firewall.message.response.GetRulesResponse;
import com.pkq.firewall.message.response.Response;
import com.pkq.firewall.model.Rule;
import com.alibaba.fastjson.JSON;
import com.alnie.tc.network.NetworkOp;
import com.alnie.tc.po.AjaxResult;
import com.alnie.tc.po.Page;
import com.alnie.tc.po.PageData;
import com.alnie.tc.po.UploadInfo;

import com.alnie.tc.po.SysLogs;
import com.alnie.tc.system.common.BaseService;
import com.alnie.tc.system.common.Constants;
import com.alnie.tc.system.utils.CommonUtil;
import com.alnie.tc.system.utils.UploadUtil;
import com.ibatis.sqlmap.client.SqlMapSession;

public class RuleService  extends BaseService{
	public PageData List(Page page,HashMap baseMap)throws Exception{
		PageData pageData=null;
		List<Map<String, Object>> resultList= new ArrayList<Map<String, Object>>();
		try {
			//if(baseMap==null)baseMap=new HashMap();
			//baseMap.put("start_row", page.getStart());
			//baseMap.put("end_row", page.getLimit());
			//List<Map<String, Object>> resultList= (List)this.getSqlMapClientTemplate().queryForList("Rule.list",baseMap);
			//改为从主机代理获取
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
			int total_size=response.getTotal();
			pageData=new PageData(total_size,resultList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			pageData=new PageData(0,resultList);
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
		    rule.setRemotePort((String)baseMap.get("remotePort"));
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
			String ruleid=baseMap.get("id").toString();
			String deviceip=baseMap.get("deviceip").toString();
			request.setHost(deviceip);
			request.setId(ruleid);
			Response response = NetworkOp.deleteRule(request);
			conn = this.getSqlMapClient().getDataSource().getConnection();//conn
		    conn.setAutoCommit(false);//conn
		    
			session.insert("system.newLog", new SysLogs(Constants.TRANS_LOG_RULE,"删除策略","策略："+deviceip+":"+ruleid));
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
			result.setResult_msg(response.getPolicy());  //把默认策略值放到setResult_msg属性里面
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			this.getSqlMapClientTemplate().insert("system.newLog",new SysLogs(Constants.TRANS_LOG_RULE,0,"获取默认策略", e.getMessage() ));
			return new AjaxResult(AjaxResult.RESULT_CODE_FAIL,e.getMessage());
		}
		return result;
		
	}
	
	/**
	 * 接收上传的版本文件，并添加版本信息到数据库中
	 * @param baseMap
	 * @param upInfo
	 * @return
	 * @throws Exception
	 */
	public AjaxResult updateAgent(HashMap baseMap,UploadInfo upInfo) throws Exception{
		AjaxResult result;
		//--------
		String strEncode = (String)baseMap.get("updatekey");
		String jsonString = getFromBASE64(strEncode);
		Map<String,Object> map = (Map<String,Object>)JSON.parse(jsonString);
		HashMap hm = mapToHashMap(map);
		//--------
		UploadUtil uu = new UploadUtil(upInfo);
		uu.setUseStoreName(false);
		uu.setUploadEncoding("UTF-8");
		String uploadDir=CommonUtil.GetProConfig(Constants.UPLOAD_PATH);
		uu.setUploadDir(uploadDir);
		String upResult = uu.Upload();
		
		//--------------------
		//文件名，取上传的多个文件中的第一个文件
		String fileName = uu.getUfInfo().getFileFileName().get(0);
		//文件绝对路径
		//String fileRealName=Constants.GetRealPath(uploadDir + (String)uu.getUfInfo().getFileStoreName().get(0));
		//--------------------
		//上传完后，加入数据库
		String urlRoot =CommonUtil.GetProConfig(Constants.URL_ROOT);
		String fileURL =  urlRoot + uploadDir + fileName;
		hm.put("downUrl", fileURL);
		result = insertUpdateInfoToDb(hm);
		//
		return result;
	}
	
	public AjaxResult notifyUpdateAgent(HashMap baseMap)throws Exception{
	    
		AjaxResult result =  new AjaxResult();
		//Task.notifyNewAgent();
		return result;
		
	}

	/**
	 * BASE64解码
	*/
	public String getFromBASE64(String s) throws Exception{ 
		if (s == null) return null; 
		BASE64Decoder decoder = new BASE64Decoder(); 
		byte[] b = decoder.decodeBuffer(s); 
		return new String(b); 
	}
	
	/**
	 * 
	 * @param paraMap
	 * @return
	 */
	HashMap mapToHashMap(Map<String,Object> paraMap){
		HashMap hm = new HashMap();
		for(Map.Entry<String, Object> entry : paraMap.entrySet())    
			{    
			    //System.out.println(entry.getKey()+": "+entry.getValue()); 
			    hm.put(entry.getKey(), entry.getValue());
			}
		return hm;
	}

	

	AjaxResult insertUpdateInfoToDb(HashMap baseMap) throws Exception{
		Connection conn = null;//conn
		SqlMapSession session = null;//session
		String msg = "代理版本："+baseMap.get("version");
		try {
			conn = this.getSqlMapClient().getDataSource().getConnection();//conn
		    conn.setAutoCommit(false);//conn
		    session = this.getSqlMapClient().openSession(conn);//session
		    session.update("AgentVersion.updateOld", baseMap);  //先把之前的状态更新为old
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
	
	
	public HashMap getNewestVersion(HashMap baseMap)throws Exception{
		HashMap hm;
		try {
			List<Map<String, Object>> resultList= (List)this.getSqlMapClientTemplate().queryForList("AgentVersion.selectNewest",baseMap);
			int total_size= resultList.size();
			if(total_size>0){
				Map<String, Object> newestVersion = resultList.get(0);
				hm = mapToHashMap(newestVersion);
			}else{
				hm =  new HashMap();
				hm.put("versionIndex", 0); //如果没有最新版本，则设置versionIndex为0
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw e;
		}finally{

		}
		return hm;
	}
}
