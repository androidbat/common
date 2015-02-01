package com.m.rabbit.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;


/**
 * 
 * 	format_1 = "yyyy-MM-dd HH:mm:ss";
 *  format_2 = "yyyy年MM月dd日 HH时mm分ss秒";
 *  format_3 = "yyyy年MM月dd日";
 *  format_4 = "MM月dd日";
 *  format_5 = "yyyy-MM-dd HH:mm";
 *  format_6 = "yyyy-MM-dd";
 *  format_7 = "HH:mm";
 *  format_8 = "MM月dd日";
 *  format_9 = "MM/dd";
 * @author wg
 */
public class DateUtils {
	public static final String format_1 = "yyyy-MM-dd HH:mm:ss";
    public static final String format_2 = "yyyy年MM月dd日 HH时mm分ss秒";
    public static final String format_3 = "yyyy年MM月dd日";
    public static final String format_4 = "MM月dd日";
    public static final String format_5 = "yyyy-MM-dd HH:mm";
    public static final String format_6 = "yyyy-MM-dd";
    public static final String format_7 = "HH:mm";
    public static final String format_8 = "MM月dd日";
    public static final String format_9 = "MM/dd";
    public static final String format_10= "HH:mm:ss";

    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    public static String longToString(long longTime, String formatType) {
        Date date  = new Date(longTime);
        String strTime = dateToString(date, formatType); 
        return strTime;
    }

    public static Date stringToDate(String strTime, String formatType){
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    
    public static String stringToString(String strTime, String formatType){
    	Date da=stringToDate(strTime,format_1);
        return dateToString(da,formatType);
    }

    public static Date longToDate(long currentTime, String formatType){
        Date dateOld = new Date(currentTime); 
        String sDateTime = dateToString(dateOld, formatType); 
        Date date = stringToDate(sDateTime, formatType); 
        return date;
    }

    public static long stringToLong(String strTime, String formatType)
    {
        Date date;
        date = stringToDate(strTime, formatType);
        if (date == null) {
            return 0;
        } else {
            return date.getTime();
        }
    }

    /**
     * 根据日期获得所在周的日期 
     * @param mdate
     * @return
     */
    public static List<Date> dateToWeek(Date mdate) {
        int b = mdate.getDay();
        Date fdate;
        List<Date> list = new ArrayList<Date>();
        Long fTime = mdate.getTime() - b * 24 * 3600000;
        for (int a = 1; a <= 7; a++) {
            fdate = new Date();
            fdate.setTime(fTime + (a * 24 * 3600000));
            list.add(a-1, fdate);
        }
        return list;
    }
    
	/**
	  * 根据一个日期，返回是星期几的字符串
	  *
	  */
	public static String getWeekStr(String sdate, String formatType) {

		Date date = stringToDate(sdate, format_6);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DAY_OF_WEEK);
		if (Calendar.MONDAY == day) {
			return "星期一";
		} else if (Calendar.TUESDAY == day) {
			return "星期二";
		} else if (Calendar.WEDNESDAY == day) {
			return "星期三";
		} else if (Calendar.THURSDAY == day) {
			return "星期四";
		} else if (Calendar.FRIDAY == day) {
			return "星期五";
		} else if (Calendar.SATURDAY == day) {
			return "星期六";
		} else {
			return "星期日";
		}
	}
	/**
	  * 得到当前时间
	  *
	  * @return
	  */
	public static Date getNow() {
	  Date currentTime = new Date();
	  return currentTime;
	}
	/**
	  * 获得一个日期所在的周的星期几的日期，如要找出2012年2月3日所在周的星期一是几号
	  *
	  * @param sdate
	  * @param num
	  * @return
	  */
	public static String getWeekBynum(String sdate, String num,String formatType) {
	  // 再转换为时间
	  Date dd = stringToDate(sdate,formatType);
	  Calendar c = Calendar.getInstance();
	  c.setTime(dd);
	  if (num.equals("1")) // 返回星期一所在的日期
	   c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	  else if (num.equals("2")) // 返回星期二所在的日期
	   c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
	  else if (num.equals("3")) // 返回星期三所在的日期
	   c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
	  else if (num.equals("4")) // 返回星期四所在的日期
	   c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
	  else if (num.equals("5")) // 返回星期五所在的日期
	   c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
	  else if (num.equals("6")) // 返回星期六所在的日期
	   c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
	  else if (num.equals("0")) // 返回星期日所在的日期
	   c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
	  
	  return dateToString(c.getTime(),formatType);	
	  
	}
	/**
	 * 获得本周几日期
	 * @param num
	 * @return
	 */
	public static String getWeekBynum(String num,String formatType) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		if (num.equals("1")) // 返回星期一所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		else if (num.equals("2")) // 返回星期二所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		else if (num.equals("3")) // 返回星期三所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		else if (num.equals("4")) // 返回星期四所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		else if (num.equals("5")) // 返回星期五所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		else if (num.equals("6")) // 返回星期六所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		else if (num.equals("0")) // 返回星期日所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		
		return dateToString(c.getTime(),formatType);	
		
	}
	
	/**
	 *  获得与当前时间相差diff天的时间
	 * @param num
	 * @return
	 */
	public static String getBeforeSevenDay(String formatType,int diff) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		c.add(Calendar.DAY_OF_MONTH, diff);
		return dateToString(c.getTime(),formatType);	
	}
	
}
