package com.m.rabbit.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TelphoneUtils {
	/**
	 * 中国移动 2G号段：134,135,136,137,138,139,150,151,152,158,159
	 * 3G号段：157,182,183,187,188 
	 * 无线上网卡专属号段：147 
	 * 中国联通 2G号段：130,131,132,155,156
	 * 3G号段：185,186 
	 * 无线上网卡专属号段：145 
	 * 中国电信： 2G号段：133,153 3G号段180,181,189
	 */
	public static final String[] tels = new String[] { "134", "135", "136",
			"137", "138", "139", "150", "151", "152", "158", "159", "157",
			"182", "183", "187", "188", "147", "130", "131", "132", "155",
			"156", "185", "186", "145", "133", "153", "180", "181", "189" };

	public static boolean isPhoneNum(String telnum) {
		if (telnum.length() != 11) {
			return false;
		}
		for (int i = 0; i < tels.length; i++) {
			if (telnum.startsWith(tels[i])) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 手机号验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^((170)|([1][3,4,5,8][0-9]))\\d{8}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	/**
	 * 电话号码验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isPhone(String str) {
		Pattern p1 = null, p2 = null;
		Matcher m = null;
		boolean b = false;
		p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$"); // 验证带区号的
		p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$"); // 验证没有区号的
		if (str.length() > 9) {
			m = p1.matcher(str);
			b = m.matches();
		} else {
			m = p2.matcher(str);
			b = m.matches();
		}
		return b;
	}
}
