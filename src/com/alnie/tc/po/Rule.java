package com.alnie.tc.po;

public class Rule {
	String direction = ""; //inbound, outbound
	String action = ""; //allow,deny
	String protocol = ""; //网络协议：tcp,udp
	String port = ""; //本地端口
	String remoteIp = ""; //远端ip
	String remotePort = ""; //远端端口
	String process = "";  //进程
	
	int id=0;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getRemoteIp() {
		return remoteIp;
	}
	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}
	public String getRemotePort() {
		return remotePort;
	}
	public void setRemotePort(String remotePort) {
		this.remotePort = remotePort;
	}
	
	public String toString(){
		String ret = null;
		
		ret = String.format(" rule{ dir:%s,act:%s,pro:%s,port:%s,rIp:%s,rPort:%s }", direction,action,protocol,port,remoteIp,remotePort);
		
		return ret;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
}
