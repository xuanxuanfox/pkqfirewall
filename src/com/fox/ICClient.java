package com.fox;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;
import java.util.Map;

import com.alnie.tc.system.common.BaseService;

public class ICClient extends BaseService{
	int port = 10514;

	public void changePolicy(String ip,List<Map<String, Object>> resultList)
	{
		String policyText = getPolicyString(resultList);
		send(ip, policyText);
	}
	
	String getPolicyString(List<Map<String, Object>> resultList)
	{
		String filepath,filetype;
		String strPolicyOut="";
		
		int ii=resultList.size();
		try{
			String strPolicyTemplate= FileOp.readTextFile( "twpol.txt" );
			String strOnefileTemplate= FileOp.readTextFile( "onefile.txt" );
			String strOnefileOut="";
			String strParameter="";
			String strAll="";

			for(int i=0;i<ii;i++){
				strAll = strOnefileTemplate;
				Map<String, Object> o = resultList.get(i);
				filepath=(String)o.get("filepath");
				filetype=(String)o.get("filetype");
				strAll = strAll.replaceAll("((FILE_NAME))", filepath);
				strParameter = getParameter(filetype);
				strAll = strAll.replaceAll("((ARGUMENT))", strParameter);
				strOnefileOut = strOnefileOut + strAll + "\n";
			}
			
			strPolicyOut = strPolicyTemplate;
			strPolicyOut = strPolicyOut.replaceAll("((ONEFILE))", strOnefileOut);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return strPolicyOut;
	}
	
	String getParameter(String filetype){
		String strRet= "";
		if(filetype=="1"){
			strRet=" -> $(Dynamic) -i ";
		}else
		{
			strRet=" -> $(IgnoreNone) -SHa ";
		}
		return strRet;
	
	}
	
	
	void send(String host, String msg) {
		DatagramSocket s = null;
		try {
			s = new DatagramSocket();
			byte[] buffer;
			buffer = msg.getBytes();
			InetAddress ia = InetAddress.getByName(host);
			DatagramPacket dgp = new DatagramPacket(buffer, buffer.length, ia,
					port);
			s.send(dgp);
		} catch (IOException e) {
			//System.out.println(e.toString());
		} finally {
			if (s != null)
				s.close();
		}

	}
}
