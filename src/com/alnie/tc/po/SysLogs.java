package com.alnie.tc.po;

import java.util.Date;

import com.alnie.tc.system.common.Constants;

/**
 * TcSysLogs entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysLogs implements java.io.Serializable {

	// Fields

	private Long logId;
	private Long operatorId;
	private Long transType;
	private int transResult;
	private String transSubType;
	private String transDetails;
	private Date transTime;

	// Constructors
	public SysLogs(Long transType,Long operatorId,int transResult,Object transDetails)throws Exception{
		this.transType=transType;
		this.transResult=transResult;
		this.transDetails=String.valueOf(transDetails);
		this.operatorId=operatorId;
	}
	public SysLogs(Long transType,String transSubType,Object transDetails)throws Exception{
		this.transType=transType;
		this.transResult=1;
		this.transSubType=transSubType;
		this.transDetails=String.valueOf(transDetails);
		this.operatorId=Constants.GetLoginOperatorId();
	}
	public SysLogs(Long transType,int transResult,String transSubType,Object transDetails)throws Exception{
		this.transType=transType;
		this.transResult=transResult;
		this.transSubType=transSubType;
		this.transDetails=String.valueOf(transDetails);
		this.operatorId=Constants.GetLoginOperatorId();
	}

	// Property accessors

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Long getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public Long getTransType() {
		return this.transType;
	}

	public void setTransType(Long transType) {
		this.transType = transType;
	}


	public int getTransResult() {
		return transResult;
	}

	public void setTransResult(int transResult) {
		this.transResult = transResult;
	}

	public String getTransSubType() {
		return transSubType;
	}

	public void setTransSubType(String transSubType) {
		this.transSubType = transSubType;
	}

	public String getTransDetails() {
		return transDetails;
	}

	public void setTransDetails(String transDetails) {
		this.transDetails = transDetails;
	}

	public Date getTransTime() {
		return this.transTime;
	}

	public void setTransTime(Date transTime) {
		this.transTime = transTime;
	}

}