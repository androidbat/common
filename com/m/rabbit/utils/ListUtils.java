package com.m.rabbit.utils;

import java.util.List;

public class ListUtils {
	
	public static boolean hasData(List list){
		return list != null && list.size() > 0;
	}
	
	public static boolean noData(List list){
		return list == null || list.size() < 1;
	}
}
