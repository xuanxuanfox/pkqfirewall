package com.alnie.tc.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.apache.log4j.Logger;

import com.alnie.tc.system.common.BaseService;

public class UdpClient {
	static int TIMEOUT = 5000;
	static Logger logger = Logger.getLogger(UdpClient.class);
	
	/**
	 * 只发送
	 * @param host  对方地址
	 * @param port 对方端口
	 * @param strSend 要发送的消息
	 * @throws Exception
	 */
	static void sendNoBack(String host, int port, String strSend) throws Exception{
		DatagramSocket s = null;
		byte[] bufferReceive = null;
		String strReceive = null;
		s = new DatagramSocket();
		s.setSoTimeout(TIMEOUT);
		byte[] buffer = strSend.getBytes();
		StringBuffer sb = new StringBuffer();
		InetAddress ia = InetAddress.getByName(host);
		logger.debug("UdpClient send:" + strSend);
		DatagramPacket dgp = new DatagramPacket(buffer, buffer.length, ia, port);
		s.send(dgp);
	}
	
	/**
	 * 发送并接收
	 * @param host 对方地址
	 * @param port 对方端口
	 * @param strSend 要发送的消息
	 * @return 接收到的消息
	 * @throws Exception
	 */
	static String sendAndReceive(String host, int port, String strSend)
			throws Exception {
		DatagramSocket s = null;
		byte[] bufferReceive = null;
		String strReceive = null;
		s = new DatagramSocket();
		s.setSoTimeout(TIMEOUT);
		int maxLenRecvBuffer = 10240;  //最大接收数据大小，最大为1也50(pageSize)条策略
		int lenReceived;
		byte[] buffer = strSend.getBytes();
		StringBuffer sb = new StringBuffer();
		InetAddress ia = InetAddress.getByName(host);
		logger.debug("UdpClient send:" + strSend);
		DatagramPacket dgp = new DatagramPacket(buffer, buffer.length, ia, port);
		s.send(dgp);
		DatagramPacket receivePacket = new DatagramPacket(new byte[maxLenRecvBuffer], maxLenRecvBuffer);
		//do{
			s.receive(receivePacket);
			bufferReceive = receivePacket.getData();
			lenReceived = receivePacket.getLength();
			strReceive = new String(bufferReceive,0,lenReceived);
			sb.append(strReceive);
		//}
		//while(lenReceived==maxLenRecvBuffer);
		
		logger.debug("UdpClient recv:" + sb.toString());
		if (s != null) {
			s.close();
		}
		return sb.toString();
	}
	
	
	
}
