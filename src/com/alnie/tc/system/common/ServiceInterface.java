package com.alnie.tc.system.common;

/**
* 
* CopyRright (c)2014: alnie
* Project:
* Comments: 
* Author： Alnie
* Create Date： Feb 14, 2014
* Version: V1.0.0
*/
public interface ServiceInterface {

	/**
	 * �������service�ӿ�
	 * 
	 * @param context
	 *            Ӧ��������
	 * @param methodName
	 *            �������
	 * @param param
	 *            ���� Object[]
	 * @throws SvcException
	 */
	public Object execTrans(String methodName, Object... param) throws Exception;

	/**
	 * ���������service�ӿ�
	 * 
	 * @param context
	 *            Ӧ��������
	 * @param methodName
	 *            �������
	 * @param param
	 *            ���� Object[]
	 * @throws SvcException
	 */
	public Object execNoTrans(String methodName, Object... param) throws Exception;
}
