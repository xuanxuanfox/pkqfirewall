package com.alnie.tc.po;

/**
* 
* CopyRright (c)2014: alnie
* Project:
* Comments: 
* Author： Alnie
* Create Date： Feb 14, 2014
* Version: V1.0.0
*/
public class Page {
	private int currentPage; //当前页号 
	private int limit;//取值单次数量
	
	private int total; //总行数
	private int start;//取值起始位置
    private int totalPages; //总页数
    
    private String sql;
    
    public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Page(){
    	total=0;
    	start = 0;
    	limit =20;
    	currentPage=1;
    	totalPages=0;
    }
    
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
    public String getPageSql(){
    	StringBuilder sb=new StringBuilder();
    	sb.append(this.getSql());
    	sb.append(" limit " + getStartRow());
    	sb.append("," +getLimit());
    	return sb.toString();
    }
    public String getTotalSql(){
    	StringBuilder sb=new StringBuilder();
    	sb.append("select count(1) from (").append(this.getSql()).append(" ) cc");
    	return sb.toString();
    }
    public int getTotalPages() {
    	totalPages=total/getLimit();
	    int mod=total%getLimit();
	    if(mod>0){
	    	totalPages++;
	    }
	    return totalPages;
	}
	public int getStartRow() {
		return getStart();
	}
	public int getEndRow() {
		return  getStart()+getLimit();
	}

	public int getStart() {
		return (getCurrentPage() - 1) * getLimit();
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
}
