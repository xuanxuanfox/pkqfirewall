package com.alnie.tc.system.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.alnie.tc.po.AjaxResult;
import com.alnie.tc.po.Page;
import com.alnie.tc.po.PageData;
import com.opensymphony.xwork2.ActionSupport;
/**
* 
* CopyRright (c)2014: alnie
* Project:
* Comments: 
* Author： Alnie
* Create Date： Feb 14, 2014
* Version: V1.0.0
*/
public class BaseAction extends ActionSupport {
	private int rows;//取值单次数量
	private int page;//取值起始页位置

	public final static String INDEX="index";
	public final static String LOGOUT="logout";
	public final static String LOGIN="login";
	public final static String MAIN="main";
	public final static String SUCCESS="success";
	public final static String AJAXERROR="ajaxerror";
	public final static String PAGEDATA="pagedata";
	public final static String LISTDATA="listdata";
	public final static String MAPDATA="mapdata";
	public final static String STRINGDATA="stringdata";
	public final static String OBJECTDATA="objectdata";
	public final static String AJAXDATA="ajaxdata";
	
	protected PageData pageData;
	protected Map mapData; 
	protected List listData;
	protected String stringData;
	protected AjaxResult ajaxData;
	protected Object objectData;
	private Map<String,String> bean;
	/**
	 * JSP存放目录
	 */
	private String jspFolder = "WEB-INF/jsp";
	/**
	 * 日志
	 */
	protected Logger logger= Logger.getLogger(this.getClass());

	/**
	 * 业务类接口
	 */
	protected ServiceInterface service;

	public String getJspFolder() {
		return jspFolder;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * 设置具体业务类
	 * 
	 * @param service
	 */
	public void setService(ServiceInterface service) {
		this.service = service;
	}

	public Object getObjectData() {
		return objectData;
	}
	public void setObjectData(Object objectData) {
		this.objectData = objectData;
	}
	public AjaxResult getAjaxData() {
		return ajaxData;
	}
	public void setAjaxData(AjaxResult ajaxData) {
		this.ajaxData = ajaxData;
	}
	public String IndexPage()throws Exception{
		return INDEX;
	}
	public String MainIndex()throws Exception{
		return MAIN;
	}
	public String LoginIndex()throws Exception{
		return LOGIN;
	}
	public String LogoutIndex()throws Exception{
		return LOGOUT;
	}
	public String ErrorIndex()throws Exception{
		return ERROR;
	}
	public PageData getPageData() {
		return pageData;
	}
	public void setPageData(PageData pageData) {
		this.pageData = pageData;
	}
	public Map getMapData() {
		return mapData;
	}
	public void setMapData(Map mapData) {
		this.mapData = mapData;
	}
	public List getListData() {
		return listData;
	}
	public void setListData(List listData) {
		this.listData = listData;
	}
	public String getStringData() {
		return stringData;
	}
	public void setStringData(String stringData) {
		this.stringData = stringData;
	}

	public Map<String, String> getBean() {
		if(bean==null)bean=new HashMap<String, String>();
		return bean;
	}

	public void setBean(Map<String, String> bean) {
		this.bean = bean;
	}
	protected Page getQPage(){
		Page pageData=new Page();
		pageData.setCurrentPage(page);
		pageData.setLimit(rows);
		return pageData;
	}
	protected static String getFileExtention(String fileName)  {   
		int pos = fileName.lastIndexOf( "." );   
		return fileName.substring(pos);   
	} 
}
