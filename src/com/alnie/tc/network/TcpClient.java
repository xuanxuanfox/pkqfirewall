package com.alnie.tc.network;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.log4j.Logger;

public class TcpClient implements NetworkClient{
	static int TIMEOUT = 5000;
	static Logger logger = Logger.getLogger(TcpClient.class);
	int maxLenRecvBuffer = 10240;  //最大接收数据大小，最大为1也50(pageSize)条策略

	public String sendAndReceive(String host, int port, String strSend)
			throws Exception {
		int nRead = 0;
		DataOutputStream dos = null;
		InputStream in = null;
		InetSocketAddress addr = new InetSocketAddress( host, port ) ;
		Socket clientSocket = new Socket();
		clientSocket.connect( addr , TIMEOUT );
		dos = new DataOutputStream( clientSocket.getOutputStream() );
		dos.write( strSend.getBytes() );
		dos.flush();
		byte[] buffer = new byte[maxLenRecvBuffer];
		in = clientSocket.getInputStream();
		nRead = in.read(buffer, 0, maxLenRecvBuffer);
		String strRecv = new String(buffer, 0, nRead);
		logger.debug("UdpClient recv:" + strRecv);
		
		clientSocket.close();
		return strRecv;
	}

	public void sendNoBack(String host, int port, String strSend)
			throws Exception {
		DataOutputStream dos = null;
		InputStream in = null;
		InetSocketAddress addr = new InetSocketAddress( host, port ) ;
		Socket clientSocket = new Socket();
		clientSocket.connect( addr , TIMEOUT );
		dos = new DataOutputStream( clientSocket.getOutputStream() );
		dos.write( strSend.getBytes() );
		dos.flush();
		clientSocket.close();
	}

}
