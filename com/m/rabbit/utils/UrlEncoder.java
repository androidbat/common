package com.m.rabbit.utils;

import java.net.URLEncoder;

public class UrlEncoder {
	public static String encode(String str){
		try {
			return URLEncoder.encode(str);
		} catch (Exception e) {
		}
		return "";
	}
}
