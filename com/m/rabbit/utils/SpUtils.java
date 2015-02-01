package com.m.rabbit.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtils {
	private static String name = "shop";
	
	public static void saveString(Context context,String key,String value){
		final SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		sp.edit().putString(key, value).commit();
	}
	
	public static String getString(Context context,String key,String defValue){
		final SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		return sp.getString(key, defValue);
	}
	
	public static void saveInt(Context context,String key,int value){
		final SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		sp.edit().putInt(key, value).commit();
	}
	
	public static int getInt(Context context,String key,int defValue){
		final SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		return sp.getInt(key, defValue);
	}
	
	public static void saveBoolean(Context context,String key,boolean value){
		final SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		sp.edit().putBoolean(key, value).commit();
	}
	
	public static boolean getBoolean(Context context,String key,boolean defValue){
		final SharedPreferences sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		return sp.getBoolean(key, defValue);
	}
	
}
