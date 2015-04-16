package com.alnie.tc.system.utils;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class PasswordED {
	public static void main(String[] args) {
		PasswordED p=new PasswordED();
		System.out.println(p.encPassword("0000"));
	}
	private Cipher ciper_;
	private Key key_ ;
	private byte[] keys = {0x05, 0x27, 0x73, 0x56, 0x44, 0x11, 0x32, 0x54 };
	
	public PasswordED() 
	{
		try{
		ciper_ = Cipher.getInstance("DES/ecb/PKCS5Padding");
		key_ = new SecretKeySpec(keys, "DES");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String encPassword( String password ){
		byte[] afterDesEnc = null;
		String strRet = "";
		
		afterDesEnc = doEncByDes( password );
		strRet = doEncByBase( afterDesEnc );
		
		return strRet;
	}
	
	public String decPassword( String encryptedPassword ){
		byte[] afterBaseDec = null;
		String strRet = "";
		
		afterBaseDec = doDecByBase( encryptedPassword );
		strRet = doDecByDes( afterBaseDec );
		
		return strRet;
		
	}
	
	private byte[] doEncByDes( String strPlain )
	{
		byte encrypted[] = {0};
		try {
			ciper_.init(Cipher.ENCRYPT_MODE, key_ );
			byte input[] = strPlain.getBytes();
			encrypted = ciper_.doFinal(input);

		} catch (Exception e) {
			e.printStackTrace();
		}		
		return encrypted;	
	}
		
	
	private String doDecByDes( byte[] encbytes )
	{
		String strDec = "";
		try {
			ciper_.init(Cipher.DECRYPT_MODE, key_);
			byte output[] = ciper_.doFinal( encbytes );
			strDec = new String(output);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strDec;
	}	
	
	private String doEncByBase( byte[] in ){
		String strRet = "";
		BASE64Encoder en = new BASE64Encoder();
		
		strRet = en.encode( in );		
		return strRet;
	}
	
	private byte[] doDecByBase( String in ){
		if (in == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer( in );
			return b;
		} catch (Exception e) {
			return null;
		}
	}
}
