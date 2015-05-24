package com.alnie.tc.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pkq.firewall.model.Rule;
import com.alnie.tc.system.common.Constants;
import com.alibaba.fastjson.JSON;
import com.pkq.firewall.message.request.AddRuleRequest;
import com.pkq.firewall.message.request.DeleteRuleRequest;
import com.pkq.firewall.message.request.GetDefaultRuleRequest;
import com.pkq.firewall.message.request.GetRulesRequest;
import com.pkq.firewall.message.request.UpdateRequest;
import com.pkq.firewall.message.response.Response;
import com.pkq.firewall.message.response.GetRulesResponse;
import com.pkq.firewall.message.response.GetDefaultRuleResponse;

public class NetworkOp {
	static int PORT = 7709;
	//static NetworkClient networkClient = new UdpClient();
	static NetworkClient networkClient = new TcpClient();
	
	public static GetRulesResponse getRules(GetRulesRequest req) throws Exception{
		GetRulesResponse response = null;
		
		String jsonStringSend = JSON.toJSONString(req);
		String jsonStringReceive = networkClient.sendAndReceive(req.getHost(),PORT,jsonStringSend);
		if( jsonStringReceive!=null ){
			response = JSON.parseObject(jsonStringReceive, GetRulesResponse.class);
		}
		if(response.getResultCode()!=Constants.SUCESS_CODE){
	    	throw new Exception(response.getResultMessage());
	    }
		return response;
	}
	
	public static Response addRule(AddRuleRequest req) throws Exception{
		Response response = null;
		
		String jsonStringSend = JSON.toJSONString(req);
		String jsonStringReceive = networkClient.sendAndReceive(req.getHost(),PORT,jsonStringSend);
		if( jsonStringReceive!=null ){
			response = JSON.parseObject(jsonStringReceive, Response.class);
		}
		if(response.getResultCode()!=Constants.SUCESS_CODE){
	    	throw new Exception(response.getResultMessage());
	    }
		//end just for test		
		return response;
	}
	
	public static Response deleteRule(DeleteRuleRequest req) throws Exception{
		Response response = null;
		
		String jsonStringSend = JSON.toJSONString(req);
		String jsonStringReceive = networkClient.sendAndReceive(req.getHost(),PORT,jsonStringSend);
		if( jsonStringReceive!=null ){
			response = JSON.parseObject(jsonStringReceive, Response.class);
		}
		if(response.getResultCode()!=Constants.SUCESS_CODE){
	    	throw new Exception(response.getResultMessage());
	    }
		//end just for test		
		return response;
	}

	public static GetDefaultRuleResponse getDefaultRule(GetDefaultRuleRequest req) throws Exception{
		GetDefaultRuleResponse response = null;
		
		String jsonStringSend = JSON.toJSONString(req);
		String jsonStringReceive = networkClient.sendAndReceive(req.getHost(),PORT,jsonStringSend);
		if( jsonStringReceive!=null ){
			response = JSON.parseObject(jsonStringReceive, GetDefaultRuleResponse.class);
		}
		if(response.getResultCode()!=Constants.SUCESS_CODE){
	    	throw new Exception(response.getResultMessage());
	    }
		//end just for test		
		return response;
	}

	/**
	 * 想各个设备发送新版本信息
	 * 设备上的代理程序会根据此消息判断是否要更新
	 * 如果要更新，则取出消息中的版本下载路径，下载
	 * @param ip
	 * @param req
	 * @throws Exception
	 */
	public static void newAgentNotify(String ip,Map<String, Object> req) throws Exception{
		String jsonStringSend = JSON.toJSONString(req);
		networkClient.sendNoBack(ip, PORT, jsonStringSend);
	}

}
