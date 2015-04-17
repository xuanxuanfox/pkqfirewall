package com.alnie.tc.action;

import com.alnie.tc.po.AjaxResult;
import com.alnie.tc.po.PageData;
import com.alnie.tc.system.common.BaseAction;

public class RuleAction extends BaseAction {
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

}
