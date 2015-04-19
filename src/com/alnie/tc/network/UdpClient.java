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
	
	static String sendAndReceive(String host, int port, String strSend)
			throws Exception {
		DatagramSocket s = null;
		byte[] bufferReceive = null;
		String strReceive = null;
		s = new DatagramSocket();
		s.setSoTimeout(TIMEOUT);
		int lenRecv = 10240;
		byte[] buffer = strSend.getBytes();
		InetAddress ia = InetAddress.getByName(host);
		logger.debug("UdpClient send:" + strSend);
		DatagramPacket dgp = new DatagramPacket(buffer, buffer.length, ia, port);
		s.send(dgp);
		DatagramPacket receivePacket = new DatagramPacket(new byte[lenRecv], lenRecv);
		s.receive(receivePacket);
		bufferReceive = receivePacket.getData();
		strReceive = new String(bufferReceive,0,receivePacket.getLength());
		logger.debug("UdpClient recv:" + strReceive);
		if (s != null) {
			s.close();
		}
		return strReceive;
	}
}
