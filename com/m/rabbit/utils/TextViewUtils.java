package com.m.rabbit.utils;

import android.graphics.Paint;
import android.widget.TextView;

public class TextViewUtils {
	private static String TAG = TextViewUtils.class.getSimpleName();
	
	public static void setUnderLine(TextView textView){
		if (textView != null) {
			textView.getPaint().setAntiAlias(true);//抗锯齿
			textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG );
		}else{
			LLog.d(TAG,"Null View given to TextViewUtils.setUnderLine");
		}
	}
	
	public static void setStrike(TextView textView){
		if (textView != null) {
			textView.getPaint().setAntiAlias(true);
			textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG );
		}else{
			LLog.d(TAG,"Null View given to TextViewUtils.setStrike");
		}
	}
	
	public static void cancelFlag(TextView textView){
		if (textView != null) {
			textView.getPaint().setFlags(0);
		}else{
			LLog.d(TAG,"Null View given to TextViewUtils.cancelFlag");
		}
	}
	
	public static void setBold(TextView textView){
		if (textView != null) {
			textView.getPaint().setFakeBoldText(true);;
		}else{
			LLog.d(TAG,"Null View given to TextViewUtils.setBold");
		}
	}
	
}
