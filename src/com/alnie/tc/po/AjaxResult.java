package com.alnie.tc.po;

import java.util.ArrayList;
import java.util.List;
/**
* 
* CopyRright (c)2014: alnie
* Project:
* Comments: 
* Author： Alnie
* Create Date： Feb 14, 2014
* Version: V1.0.0
*/
public class AjaxResult {

	public static final String RESULT_CODE_FAIL="0";  
	public static final String RESULT_CODE_SUCCESS="1";
	
	private boolean success;
	private String result_code;//0-失败 1-成功
	private String result_msg;//提示信息
	private Object uncut_result_msg;//源信息
	private int total=0;
	private List rows=new ArrayList();
	public AjaxResult(){
		this.result_code=this.RESULT_CODE_SUCCESS;
		this.result_msg="操作成功！";
	}
	public AjaxResult(String result_code){
		this.result_code=result_code;
		this.result_msg=result_code==this.RESULT_CODE_FAIL?"操作失败！":"操作成功！";
	}
	public AjaxResult(String result_code,String result_msg){
		this.result_code=result_code;
		String code_msg="错误代码："+this.result_code;
		if(result_msg==null)result_msg="";
		this.uncut_result_msg=result_msg;
		if(!"".equals(result_msg))result_msg=" 详细信息："+result_msg;
		if(this.RESULT_CODE_SUCCESS.equals(result_code))
			this.result_msg="操作成功！"+result_msg;
		else
			this.result_msg="操作失败！"+result_msg;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getResult_msg() {
		return result_msg;
	}
	public void setResult_msg(String result_msg) {
		this.result_msg = result_msg;
	}
	public Object getUncut_result_msg() {
		return uncut_result_msg;
	}
	public void setUncut_result_msg(Object uncut_result_msg) {
		this.uncut_result_msg = uncut_result_msg;
	}
	public boolean isSuccess() {
		if(this.RESULT_CODE_SUCCESS.equals(result_code))
			return true;
		else
			return false;
	}
	public void setSuccess(boolean success) {
		
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
}
