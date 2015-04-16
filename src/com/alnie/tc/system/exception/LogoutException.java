package com.alnie.tc.system.exception;
/**
* 
* CopyRright (c)2014: alnie
* Project:
* Comments: 
* Author： Alnie
* Create Date： Feb 14, 2014
* Version: V1.0.0
*/
public class LogoutException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public LogoutException(String frdMessage) {
       super(createFriendlyErrMsg(frdMessage));
    }
    public LogoutException(Throwable throwable){

       super(throwable);

    }
    public LogoutException(Throwable throwable, String frdMessage){

       super(throwable);

    }
    private static String createFriendlyErrMsg(String msgBody) {
       StringBuffer friendlyErrMsg = new StringBuffer();
       friendlyErrMsg.append(msgBody);
       return friendlyErrMsg.toString();
    }
}
