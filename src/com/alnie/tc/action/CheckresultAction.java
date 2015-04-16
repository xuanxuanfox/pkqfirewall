package com.alnie.tc.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
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
public class CheckresultAction extends BaseAction{

	public String List()throws Exception{
        this.pageData= (PageData) service.execNoTrans("List",getQPage(),getBean());
		return PAGEDATA;
	}
	public String Det() throws Exception {
		this.mapData = (HashMap) this.service.execNoTrans("Det",getBean());
		return MAPDATA;
	}
	public InputStream getStatStream()throws Exception{
		String kpiXml=(String) this.service.execNoTrans("Stat",getBean());
		InputStream is =new ByteArrayInputStream(kpiXml.getBytes()); 
		return is; 
	}
}
