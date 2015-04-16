package com.alnie.tc.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpClient {
	static int TIMEOUT = 5000;

	static String sendAndReceive(String host, int port, String strSend)
			throws Exception {
		DatagramSocket s = null;
		byte[] bufferReceive = null;
		String strReceive = null;
		s = new DatagramSocket();
		s.setSoTimeout(TIMEOUT);
		byte[] buffer = new byte[256];

		buffer = strSend.getBytes();
		int len = buffer.length;
		InetAddress ia = InetAddress.getByName(host);
		DatagramPacket dgp = new DatagramPacket(buffer, len, ia, port);
		s.send(dgp);
		DatagramPacket receivePacket = new DatagramPacket(new byte[len], len);
		s.receive(receivePacket);
		bufferReceive = receivePacket.getData();
		strReceive = new String(bufferReceive);
		if (s != null) {
			s.close();
		}
		return strReceive;
	}
}
