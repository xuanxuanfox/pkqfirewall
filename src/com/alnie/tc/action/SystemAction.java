package com.alnie.tc.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import com.alnie.tc.po.AjaxResult;
import com.alnie.tc.po.Menu;
import com.alnie.tc.system.common.BaseAction;
import com.alnie.tc.system.common.Constants;

/**
* 
* CopyRright (c)2014: alnie
* Project:
* Comments: 
* Author： Alnie
* Create Date： Feb 14, 2014
* Version: V1.0.0
*/
public class SystemAction extends BaseAction{

	private String id;
	private String fileName;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		try {
			this.fileName = new String(fileName.getBytes("GBK"),"ISO-8859-1");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String Menu()throws Exception{
		if (null == getId() || "".equals(getId())) {
			setId("0");
		}
		long operatorId=Constants.GetLoginOperatorId();
		this.listData = (List<Menu>) this.service.execNoTrans("GetMenu", Long.parseLong(getId()),operatorId);
		return LISTDATA;
	}
	public String MenuFunctionTitle()throws Exception{
		if (null == getId() || "".equals(getId())) {
			setId("0");
		}
		this.ajaxData = (AjaxResult) this.service.execNoTrans("GetMenuFunctionTitle", Long.parseLong(getId()));
		return AJAXDATA;
	}
	/**
     * 公共查询字典列表值
     * 
     * @return
     * @throws Exception
     */
    public String SystemConfigList() throws Exception {
    	this.listData = (List<Menu>) this.service.execNoTrans("SystemConfigList",getBean());
    	return LISTDATA;
    }
	/**
	 * 下载.
	 * @return
	 * @throws Exception
	 */
	public InputStream getExportStream()throws Exception{
		String fileStorePath=String.valueOf(this.getBean().get("fileStorePath"));
		String fileName=String.valueOf(this.getBean().get("fileName"));
		String fileStoreName=String.valueOf(this.getBean().get("fileStoreName"));
		this.setFileName(fileName);
		File file = new File(fileStorePath,fileStoreName); 
		InputStream is = new FileInputStream(file); 
		return is; 
	}
	public String SystemRole() throws Exception {
    	this.listData = (List) this.service.execNoTrans("SystemRole",getBean());
    	return LISTDATA;
    }
	public String SystemRolePrivilege() throws Exception {
    	this.listData = (List) this.service.execNoTrans("SystemRolePrivilege",Integer.parseInt(getBean().get("roleId")),0);
    	return LISTDATA;
    }
	public String SystemRolePrivilegeAdd()throws Exception{
		this.ajaxData = (AjaxResult) this.service.execNoTrans("SystemRolePrivilegeAdd", getBean());
		return AJAXDATA;
	}
}
