package com.alnie.tc.po;

import java.util.Date;

/**
 * TcSysConfig entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysConfig implements java.io.Serializable {

	// Fields

	private Long configGroupId;
	private Long configId;
	private String configValue;
	private Date insertTime;
	private String comments;
	private Boolean needAll;

	// Constructors

	/** default constructor */

	public Boolean getNeedAll() {
		if(needAll==null)needAll=false;
		return needAll;
	}

	public void setNeedAll(Boolean needAll) {
		this.needAll = needAll;
	}

	public Long getConfigGroupId() {
		return configGroupId;
	}

	public void setConfigGroupId(Long configGroupId) {
		this.configGroupId = configGroupId;
	}

	public Long getConfigId() {
		return configId;
	}

	public void setConfigId(Long configId) {
		this.configId = configId;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}