package com.m.rabbit.constant;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;

import android.R.integer;

import com.m.rabbit.bean.User;
import com.m.rabbit.config.MyConfig;
import com.m.rabbit.utils.MD5Utils;
import com.m.rabbit.utils.PropertiesUtils;

public class UrlFactory {
	public static String platform;
    public static String HTTP_HEAD;
	static {
		HTTP_HEAD = PropertiesUtils.getValueA(PropertiesUtils.PROPERTIES_COMMON_PATH, PropertiesUtils.SERVER_PATH).trim();
        platform = AppConstants.getInstance().getmPlatform();
    }
	public static final String URL_LOGIN = HTTP_HEAD+ "/login?loginName=%s&pwd=%s";
	
	public static String queryBanner(int platform){
		return String.format(createUrl("/queryBanner?","type"),platform);
	}

	private static String createUrl(String head,String... fields){
		StringBuilder sb = new StringBuilder();
		sb.append(HTTP_HEAD);
		sb.append(head);
		for (int i = 0; i < fields.length; i++) {
			if (i != 0 ) {
				sb.append("&"); 
			}
			sb.append(fields[i]+"=%s");
		}
		return sb.toString();
	}

}
