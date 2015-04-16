package com.alnie.tc.system.utils;


public class TestPasswdED {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		PasswordED od = new PasswordED();
		System.out.println( "decrypt result: \n" + od.decPassword("dc+UA/Stsmw=") );
		System.out.println( "encrypt result: \n" + od.encPassword("ddddd") );
	}

}
