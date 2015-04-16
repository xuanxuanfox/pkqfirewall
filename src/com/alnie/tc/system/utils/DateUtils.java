package com.alnie.tc.system.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtils {
	public final static String FORMAT_1="yyyy-MM-dd HH:mm:ss";
	public final static String FORMAT_2="yyyy-MM-dd";
	public final static String FORMAT_3="yyyy-MM";
	public final static String FORMAT_4="yyyy";
	public final static String FORMAT_5="yyyyMMddHHmmss";
	public final static String FORMAT_6="yyyyMMdd";
	public final static String FORMAT_7="yyyyMM";
	public final static String FORMAT_8="yyyy/MM/dd";
	public static String GetStringDate(String fromFormat) throws Exception{
		SimpleDateFormat format = new SimpleDateFormat(fromFormat);
		TimeZone zone = TimeZone.getTimeZone("GMT+8");
		format.setTimeZone(zone);
		Date myDate = new Date();
		return format.format(myDate);
	}

	public static Date GetNowDate(String fromFormat) throws Exception{
		SimpleDateFormat format = new SimpleDateFormat(fromFormat);
		TimeZone zone = TimeZone.getTimeZone("GMT+8");
		format.setTimeZone(zone);
		Date myDate = new Date();
		return format.parse(format.format(myDate));
	}
	public static String GetNowDateStr(String fromFormat) throws Exception{
		SimpleDateFormat format = new SimpleDateFormat(fromFormat);
		TimeZone zone = TimeZone.getTimeZone("GMT+8");
		format.setTimeZone(zone);
		Date myDate = new Date();
		return format.format(myDate);
	}
	//"yyyy-MM-dd"
	public static String FormatDate(String fromFormat, Date dateString)throws Exception{
		if(dateString==null)return "";
		SimpleDateFormat sdf = new SimpleDateFormat(fromFormat);
		String date = sdf.format(dateString);
		return date;
	}
	public static Date GetFormatDate(String fromFormat,String dateString) throws Exception{
		SimpleDateFormat format = new SimpleDateFormat(fromFormat);
		return format.parse(dateString);
	}
	
	public static String GetLastMonth(String format,String dateStr,int month){
        Calendar calendar = new GregorianCalendar();
        if( dateStr != null && dateStr.trim().length() == 6){
            format = "yyyyMMdd";
        }
        SimpleDateFormat bartDateFormat = new SimpleDateFormat(format);
        try {
            java.util.Date date = bartDateFormat.parse(dateStr);
            calendar.setTime(date);
        }catch (Exception ex) {System.out.println(ex.getMessage());}
        calendar.add(Calendar.MONTH, month);
        return bartDateFormat.format(calendar.getTime()).toString();
    }
	public static String GetLastDate(String format,String dateStr,int day){
        Calendar calendar = new GregorianCalendar();
        if( dateStr != null && dateStr.trim().length() == 6){
            format = "yyyyMMdd";
        }
        SimpleDateFormat bartDateFormat = new SimpleDateFormat(format);
        try {
            java.util.Date date = bartDateFormat.parse(dateStr);
            calendar.setTime(date);
        }catch (Exception ex) {System.out.println(ex.getMessage());}
        calendar.add(Calendar.DATE, day);
        return bartDateFormat.format(calendar.getTime()).toString();
    }
	public static boolean checkExpired(String expiredDate) {
        if (expiredDate == null||expiredDate.equals("")||expiredDate.equals("0")) return false;
        
    	Date expDate = parseDate(expiredDate);
    	Date curDate = new java.util.Date();
    	
    	Calendar cal = new GregorianCalendar();
    	cal.setTime(expDate);
    	Calendar calCur = new GregorianCalendar();
    	calCur.setTime(curDate);
    	
    	if (calCur.compareTo(cal)>=0) {    	
    	    return false;
    	} else {
    		return true;
    	}
    }
    public static Date parseDate(String dateStr) {
        if (dateStr == null ||dateStr.equals("")) return null;
    	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sf.parse(dateStr);
        return date;
        } catch (Exception ex) {
           ex.printStackTrace();
           return null;
        }
    }
    public static int dateCompare(String date,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        cal.roll(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime().compareTo(new Date());
    }
    public static void main(String[] args) {
    	System.out.println(dateCompare("20140310111600",FORMAT_5));
    	
	}
}
