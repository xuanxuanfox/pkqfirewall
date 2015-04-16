package com.alnie.tc.po;

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
public class PageData {

	private long total;
	private List rows;
	
	public PageData(){
		
	}
	public PageData(long total,List rows){
		this.total=total;
		this.rows=rows;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List getRows() {
		return rows;
	}
	public void setRows(List rows) {
		this.rows = rows;
	}
}
