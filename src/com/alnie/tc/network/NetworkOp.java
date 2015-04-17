package com.alnie.tc.network;

import java.util.ArrayList;
import java.util.List;

import com.pkq.firewall.model.Rule;
import com.alnie.tc.system.common.Constants;
import com.alibaba.fastjson.JSON;
import com.pkq.firewall.message.request.AddRuleRequest;
import com.pkq.firewall.message.request.DeleteRuleRequest;
import com.pkq.firewall.message.request.GetDefaultRuleRequest;
import com.pkq.firewall.message.request.GetRulesRequest;
import com.pkq.firewall.message.response.Response;
import com.pkq.firewall.message.response.GetRulesResponse;
import com.pkq.firewall.message.response.GetDefaultRuleResponse;

public class NetworkOp {
	static int PORT = 7709;
	
	public static GetRulesResponse getRules(GetRulesRequest req) throws Exception{
		GetRulesResponse response = null;
		
		String jsonStringSend = JSON.toJSONString(req);
		String jsonStringReceive = UdpClient.sendAndReceive(req.getHost(),PORT,jsonStringSend);
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
		String jsonStringReceive = UdpClient.sendAndReceive(req.getHost(),PORT,jsonStringSend);
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
		String jsonStringReceive = UdpClient.sendAndReceive(req.getHost(),PORT,jsonStringSend);
		if( jsonStringReceive!=null ){
			response = JSON.parseObject(jsonStringReceive, GetDefaultRuleResponse.class);
		}
		if(response.getResultCode()!=Constants.SUCESS_CODE){
	    	throw new Exception(response.getResultMessage());
	    }
		//end just for test		
		return response;
	}
}
