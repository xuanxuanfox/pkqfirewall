package com.alnie.tc.system.common;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

import com.alnie.tc.system.utils.DateUtils;
/**
* 
* CopyRright (c)2014: alnie
* Project:
* Comments: 
* Author： Alnie
* Create Date： Feb 14, 2014
* Version: V1.0.0
*/
public class BaseService extends BaseDao implements ServiceInterface {
	/**
	 * 日志
	 */
	protected static Logger logger = Logger.getLogger(BaseService.class);


	/**
	 * 调用不需要开启事务的service方法
	 */
	public Object execNoTrans(String methodName, Object... param) throws Exception {
		return this.invoke(methodName, param);
	}

	/**
	 * 调用需要开启事务的service方法
	 */
	public Object execTrans(String methodName, Object... param) throws Exception {
		return this.invoke(methodName, param);
	}

	/**
	 * 反射调用service方法
	 * 
	 * @param methodName
	 * @param param
	 * @return
	 * @throws BaseException
	 */
	private Object invoke(String methodName, Object... param) throws Exception {
		
		Class<?>[] clazz = null;
		if (param != null && param.length > 0) {
			clazz = new Class[param.length];
			int classSize = 0;
			for (int i = 0; i < param.length; i++) {
				if (param[i] != null) {
					clazz[i] = this.objectToBasic(param[i]);
					classSize = classSize + 1;
				} else {
					break;
				}
			}
		}

		Method method = this.getClass().getMethod(methodName, clazz);
		return method.invoke(this, param);
	}

	/**
	 * 基本数据类型对象转为基本数据类型class
	 * 
	 * @param obj
	 * @return
	 */
	private Class<?> objectToBasic(Object obj) {
		if (obj instanceof Boolean) {
			return boolean.class;
		} else if (obj instanceof Character) {
			return char.class;
		} else if (obj instanceof Byte) {
			return byte.class;
		} else if (obj instanceof Short) {
			return short.class;
		} else if (obj instanceof Integer) {
			return int.class;
		} else if (obj instanceof Long) {
			return long.class;
		} else if (obj instanceof Float) {
			return float.class;
		} else if (obj instanceof Double) {
			return double.class;
		} else {
			return obj.getClass();
		}
	}
	public boolean IsNotNull(Object value)throws Exception{
    	if(value!=null&&!"".equals(value)&&!"null".equals(String.valueOf(value)))
    		return true;
    	else return false;
    }
    public String ParseString(Object obj)throws Exception{
    	 String str=String.valueOf(obj);
    	 if("null".equals(str))str="";
    	 return str;
    }
    public Long ParseLong(Object obj)throws Exception{
    	String str=String.valueOf(obj).trim();
   	 	if("null".equals(str))str="0";
    	return Long.parseLong(str);
    }
    public Double ParseDouble(Object obj)throws Exception{
    	String str=String.valueOf(obj);
   	 	if("null".equals(str))str="0";
    	return Double.parseDouble(str);
    }
    public Integer ParseInteger(Object obj)throws Exception{
    	String str=String.valueOf(obj);
   	 	if("null".equals(str))str="0";
    	return Integer.parseInt(str);
    }
    public Boolean ParseBoolean(Object obj)throws Exception{
    	return Boolean.parseBoolean(ParseString(obj));
    }
    protected String createOrderId(String workflowType)throws Exception{
		return workflowType+DateUtils.GetNowDateStr(DateUtils.FORMAT_5);
	}
}
