package com.m.rabbit.config;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

public class MyConfig {
	public final static String CONFIG = "config";
	public final static String USER_ACCOUNT = "user_account";
	public final static String USER_PASSWORD = "user_password";
	public final static String PERSON_IMG = "person_img";
	public final static String USER_ID = "user_id";
	
	public static Context mContext;
	
	public static void initContext(Context context){
		mContext = context;
	}
	
    public static String getUserId(){
        SharedPreferences sharePreferences = mContext.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        String uid = sharePreferences.getString(USER_ID, null);
        if (TextUtils.isEmpty(uid)) {
			return null;
		}
        return uid;
    }
    
    public static void setUserId(String useId){
        SharedPreferences sharePreferences = mContext.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        Editor edit = sharePreferences.edit();
        edit.putString(USER_ID, useId).commit();
    }
    
    public static void setKid(String kid){
        SharedPreferences sharePreferences = mContext.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        Editor edit = sharePreferences.edit();
        edit.putString("kid", kid).commit();
    }
    
    public static String getKid(){
        SharedPreferences sharePreferences = mContext.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        String uid = sharePreferences.getString("kid", null);
        if (TextUtils.isEmpty(uid)) {
			return null;
		}
        return uid;
    }
    
    public static void setKey(String key){
        SharedPreferences sharePreferences = mContext.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        Editor edit = sharePreferences.edit();
        edit.putString("key", key).commit();
    }
    
    public static void setLoginInfo(String key,String value){
    	SharedPreferences sharePreferences = mContext.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
    	Editor edit = sharePreferences.edit();
    	edit.putString(key, value).commit();
    }
    
    public static String getLoginInfo(String key){ 
        SharedPreferences sharePreferences = mContext.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        return sharePreferences.getString(key, null);
    }
    
    public static void setIsAutoLogin(boolean isAutoLogin){
        SharedPreferences sharePreferences = mContext.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        Editor edit = sharePreferences.edit(); 
        edit.putBoolean("user_auto_login", isAutoLogin);
        edit.commit();
    }
    public static boolean getIsAutoLogin(){ 
        SharedPreferences sharePreferences = mContext.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        return sharePreferences.getBoolean("user_auto_login", false);
    }
    public static void setIsLogin(boolean isLogin){
    	if (!isLogin) {
			setUserId(null);
		}
        SharedPreferences sharePreferences = mContext.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        Editor edit = sharePreferences.edit(); 
        edit.putBoolean("user_is_login", isLogin);
        edit.commit();
    }
    
    public static boolean getIsLogin(){ 
        SharedPreferences sharePreferences = mContext.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        return sharePreferences.getBoolean("user_is_login", false);
    }
    
    public static void setPersonImg(String key,int value){
    	SharedPreferences sharePreferences = mContext.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
    	Editor edit = sharePreferences.edit();
    	edit.putInt(key, value).commit();
    }
    
    public static int getPersonImg(String key,int defValue){ 
    	SharedPreferences sharePreferences = mContext.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
    	return sharePreferences.getInt(key, defValue);
    }
    
}
