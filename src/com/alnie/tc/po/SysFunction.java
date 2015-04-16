package com.alnie.tc.po;

/**
 * TcSysFunction entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysFunction implements java.io.Serializable {

	// Fields

	private Long functionId;
	private String functionName;
	private Long parentFunctionId;
	private Long groupId;
	private String functionClass;
	private Long type;
	private String href;
	private Long functionIndex;
	private Long status;
	private String comments;
	private String title;
	private Long operatorId;

	// Constructors

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public Long getFunctionId() {
		return this.functionId;
	}

	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}

	public String getFunctionName() {
		return this.functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public Long getParentFunctionId() {
		return this.parentFunctionId;
	}

	public void setParentFunctionId(Long parentFunctionId) {
		this.parentFunctionId = parentFunctionId;
	}

	public Long getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getFunctionClass() {
		return this.functionClass;
	}

	public void setFunctionClass(String functionClass) {
		this.functionClass = functionClass;
	}

	public Long getType() {
		return this.type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getHref() {
		return this.href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public Long getFunctionIndex() {
		return this.functionIndex;
	}

	public void setFunctionIndex(Long functionIndex) {
		this.functionIndex = functionIndex;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}