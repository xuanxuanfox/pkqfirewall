package com.alnie.tc.action;

import java.util.HashMap;
import com.alnie.tc.po.AjaxResult;
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
public class OperatorAction extends BaseAction{

	public String List()throws Exception{
        this.pageData= (PageData) service.execNoTrans("List",getQPage(),getBean());
		return PAGEDATA;
	}
	public String New() throws Exception {
		this.ajaxData = (AjaxResult) this.service.execTrans("New",getBean());
		return AJAXDATA;
	}
	public String Mod() throws Exception {
		this.ajaxData = (AjaxResult) this.service.execTrans("Mod",getBean());
		return AJAXDATA;
	}
	public String Del() throws Exception {
		this.ajaxData = (AjaxResult) this.service.execTrans("Del",getBean());
		return AJAXDATA;
	}
	public String ModPwd() throws Exception {
		this.ajaxData = (AjaxResult) this.service.execTrans("ModPwd",getBean());
		return AJAXDATA;
	}
	public String Det() throws Exception {
		this.mapData = (HashMap) this.service.execNoTrans("Det",getBean());
		return MAPDATA;
	}
	public String ModMyinfo() throws Exception {
		this.ajaxData = (AjaxResult) this.service.execTrans("ModMyinfo",getBean());
		return AJAXDATA;
	}
}
