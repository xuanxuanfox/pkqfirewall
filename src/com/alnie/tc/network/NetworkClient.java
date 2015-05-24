package com.alnie.tc.network;

public interface NetworkClient {
	void sendNoBack(String host, int port, String strSend) throws Exception;
	String sendAndReceive(String host, int port, String strSend) throws Exception;
}
