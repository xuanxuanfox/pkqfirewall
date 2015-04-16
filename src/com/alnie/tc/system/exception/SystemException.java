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
public class SystemException extends Exception {
    private static final long serialVersionUID = 1L;
    public SystemException(Exception ex) {
        super(ex);
     }
    public SystemException(String frdMessage) {
       super(createFriendlyErrMsg(frdMessage));
    }
    public SystemException(Throwable throwable){
       super(throwable);
    }
    public SystemException(Throwable throwable, String frdMessage){
       super(throwable);
    }
    private static String createFriendlyErrMsg(String msgBody) {
       StringBuffer friendlyErrMsg = new StringBuffer();
       friendlyErrMsg.append(msgBody);
       return friendlyErrMsg.toString();
    }
}
