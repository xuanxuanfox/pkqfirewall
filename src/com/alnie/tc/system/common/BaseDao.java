package com.alnie.tc.system.common;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
* 
* CopyRright (c)2014: alnie
* Project:
* Comments: 
* Author： Alnie
* Create Date： Feb 14, 2014
* Version: V1.0.0
*/
public class BaseDao extends SqlMapClientDaoSupport{
	protected final static String SEPARATOR = "<br>";
	protected static Logger logger = Logger.getLogger(BaseDao.class);
	protected HttpServletRequest request;
	protected BaseDao(){};
	protected static Logger getLogger() {
		return logger;
	}
    public String Array2String(Object[] objAry)throws Exception{
		StringBuilder details=new StringBuilder();
		for(Object obj:objAry){
			details.append(String.valueOf(obj)).append("|");
		}
		return details.toString();
	}
    
    public List JsonToList(String jsonStr)throws Exception{
		JSONArray baseList=JSONArray.fromObject(jsonStr);
		List list=new ArrayList();
		for(Object ttObj:baseList){
			list.add(ttObj.toString());
		}
		return list;
	}
    public boolean IsNotNull(Object value)throws Exception{
    	if(value!=null&&!"".equals(value)&&!"null".equals(String.valueOf(value)))
    		return true;
    	else return false;
    }
    public String ParseString(Object obj)throws Exception{
    	 String str=String.valueOf(obj);
    	 if("null".equals(str))str="";
    	 return str;
    }
    public Long ParseLong(Object obj)throws Exception{
    	String str=String.valueOf(obj);
   	 	if("null".equals(str))str="0";
    	return Long.parseLong(str);
    }
    public Double ParseDouble(Object obj)throws Exception{
    	String str=String.valueOf(obj);
   	 	if("null".equals(str))str="0";
    	return Double.parseDouble(str);
    }
    public Integer ParseInteger(Object obj)throws Exception{
    	String str=String.valueOf(obj);
   	 	if("null".equals(str))str="0";
    	return Integer.parseInt(str);
    }
    public Boolean ParseBoolean(Object obj)throws Exception{
    	return Boolean.parseBoolean(ParseString(obj));
    }
    protected String GetResultMsg(int sucCnt,int errCnt,String err_hint){
		StringBuilder msg=new StringBuilder();
		msg.append("成功导入数据共 ").append(sucCnt).append(" 条！").append(SEPARATOR);
		msg.append("错误导入数据共 ").append(errCnt).append(" 条！").append(SEPARATOR);
		if(errCnt>0)msg.append("错误导入数据列表：").append(SEPARATOR).append(err_hint);
		return msg.toString();
	}
}