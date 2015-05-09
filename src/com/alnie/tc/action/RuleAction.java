package com.alnie.tc.action;

import java.util.HashMap;
import java.util.Map;

import com.alnie.tc.po.AjaxResult;
import com.alnie.tc.po.PageData;
import com.alnie.tc.po.UploadInfo;
import com.alnie.tc.system.common.BaseAction;

public class RuleAction extends BaseAction {
	private UploadInfo uf;
	public UploadInfo getUf() {
		return uf;
	}
	public void setUf(UploadInfo uf) {
		this.uf = uf;
	}
	public String List()throws Exception{
        this.pageData= (PageData) service.execNoTrans("List",getQPage(),getBean());
		return PAGEDATA;
	}
	public String New() throws Exception {
		this.ajaxData = (AjaxResult) this.service.execTrans("New",getBean());
		return AJAXDATA;
	}
	public String Del() throws Exception {
		this.ajaxData = (AjaxResult) this.service.execTrans("Del",getBean());
		return AJAXDATA;
	}
	public String getDefaultRule() throws Exception {
		this.ajaxData = (AjaxResult) this.service.execTrans("getDefaultRule",getBean());
		return AJAXDATA;
	}
	public String updateAgent() throws Exception {
		UploadInfo uu = this.getUf();
		Map<String,String> ub = getBean();
		this.ajaxData = (AjaxResult) this.service.execTrans("updateAgent",ub,uu);
		return AJAXDATA;
	}

	public String notifyUpdateAgent() throws Exception {
		this.ajaxData = (AjaxResult) this.service.execTrans("notifyUpdateAgent",getBean());
		return AJAXDATA;

		
		
	}
	
	public String getNewestVersion() throws Exception {
		this.mapData = (HashMap) this.service.execNoTrans("getNewestVersion",getBean());
		return MAPDATA;
	}
}
