package com.alnie.tc.action;

import com.alnie.tc.po.PageData;
import com.alnie.tc.system.common.BaseAction;

/**
* 
* CopyRright (c)2014: alnie
* Project:
* Comments: 
* Author： Alnie
* Create Date： Feb 14, 2014
* Version: V1.0.0
*/
public class LogAction extends BaseAction{

	public String LoginList()throws Exception{
        this.pageData= (PageData) service.execNoTrans("LoginList",getQPage(),getBean());
		return PAGEDATA;
	}
	public String List()throws Exception{
        this.pageData= (PageData) service.execNoTrans("List",getQPage(),getBean());
		return PAGEDATA;
	}
}
