package com.alnie.tc.po;

import java.util.Date;

/**
 * TcSysOperator entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysOperator implements java.io.Serializable {

	// Fields

	private Long operatorId;
	private String operatorName;
	private String operatorRealname;
	private String mobile;
	private String email;
	private String password;
	private String comments;
	private Long status;
	private Date lastLogintime;
	private Date createTime;
	private String lastLogintimeStr;
	private String createTimeStr;
	private String nlEmail;
	private String role;
	private String roleName;
	// Constructors

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	// Property accessors

	public Long getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return this.operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperatorRealname() {
		return this.operatorRealname;
	}

	public void setOperatorRealname(String operatorRealname) {
		this.operatorRealname = operatorRealname;
	}

	public String getMobile() {
		return this.mobile==null?"":this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getComments() {
		return this.comments==null?"":this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Date getLastLogintime() {
		return this.lastLogintime;
	}

	public void setLastLogintime(Date lastLogintime) {
		this.lastLogintime = lastLogintime;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public String getLastLogintimeStr() {
		return lastLogintimeStr;
	}

	public void setLastLogintimeStr(String lastLogintimeStr) {
		this.lastLogintimeStr = lastLogintimeStr;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getNlEmail() {
		return nlEmail;
	}

	public void setNlEmail(String nlEmail) {
		this.nlEmail = nlEmail;
	}

}