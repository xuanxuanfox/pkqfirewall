package com.alnie.tc.po;

import java.util.List;
import java.util.Map;
/**
* 
* CopyRright (c)2014: alnie
* Project:
* Comments: 
* Author： Alnie
* Create Date： Feb 14, 2014
* Version: V1.0.0
*/
public class Menu {

	private String id;
	private String text;
	private String parent_id;
	private String iconCls;
	private String state = "closed";
	private Map attributes;
	private List children;
	public List getChildren() {
		return children;
	}
	public void setChildren(List children) {
		this.children = children;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Map getAttributes() {
		return attributes;
	}
	public void setAttributes(Map attributes) {
		this.attributes = attributes;
	}
	public Menu(){
		
	}
	public Menu(String id,String text,String parent_id,String iconCls){
		this.id=id;
		this.text=text;
		this.parent_id=parent_id;
		this.iconCls=iconCls;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
}
