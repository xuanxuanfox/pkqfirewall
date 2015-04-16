package com.alnie.tc.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import com.alnie.tc.po.AjaxResult;
import com.alnie.tc.po.PageData;
import com.alnie.tc.po.UploadInfo;
import com.alnie.tc.system.common.BaseAction;
import com.alnie.tc.system.common.Constants;
import com.alnie.tc.system.utils.CommonUtil;

/**
* 
* CopyRright (c)2014: alnie
* Project:
* Comments: 
* Author： Alnie
* Create Date： Feb 14, 2014
* Version: V1.0.0
*/
public class ResourceAction extends BaseAction{
	private UploadInfo uf;
	public UploadInfo getUf() {
		return uf;
	}
	public void setUf(UploadInfo uf) {
		this.uf = uf;
	}
	private String fileName;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		try {
			logger.info(fileName);
			this.fileName = new String(fileName.getBytes("GBK"),"ISO-8859-1");
//			this.fileName = fileName;
			logger.info(this.fileName);
		} catch (Exception e) {
		}
	}
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
	public String Det() throws Exception {
		this.mapData = (HashMap) this.service.execNoTrans("Det",getBean());
		return MAPDATA;
	}
	public String BatchImport()throws Exception{
		this.ajaxData=(AjaxResult) this.service.execTrans("BatchImport",getBean(),this.getUf()); 
		return AJAXDATA;
	}
	public String Export() throws Exception {
		this.ajaxData=(AjaxResult)this.service.execTrans("Export", getBean());
		return AJAXDATA;
	}
	public InputStream getExportStream()throws Exception{
		String fileName="资源导出.csv";
		this.setFileName(fileName);
		String uploadDir=Constants.GetRealPath(CommonUtil.GetProConfig(Constants.EXPORT_PATH));
		String fileStoreName="resourceExport.csv";
		File file = new File(uploadDir,fileStoreName); 
		InputStream is = new FileInputStream(file); 
		return is; 
	}
}
