package org.iitwf.healthcare.mmp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

	
	public static void main(String[] args) {
		
		getFutureDate("dd-MMMMM-yy HH:mm:ss",0,"PST");
		//getFutureDate("dd-MM-yyyy HH:mm:ss",0);
		
	}
	public static String getFutureDate(String pattern,int n)
	{
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, n);
		Date todaysDate= cal.getTime();
		System.out.println("todaysDate::: " + todaysDate);
		
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String formattedDate = sdf.format(todaysDate);
		return formattedDate;
	}
	public static String getFutureDate(String pattern,int n,String timeZone)
	{
		 
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, n);
		Date todaysDate= cal.getTime();
		System.out.println("todaysDate:::" + todaysDate);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		TimeZone timeZoneValue = TimeZone.getTimeZone(timeZone);
		sdf.setTimeZone(timeZoneValue);
		String formattedDate = sdf.format(todaysDate);
		System.out.println("Formatted Date:: " + formattedDate+" TimeZone:: "+ sdf.getTimeZone().getDisplayName());
		return formattedDate;
	 
	}
}
