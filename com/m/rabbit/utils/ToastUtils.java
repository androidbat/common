package com.m.rabbit.utils;



import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 弹出toast提示工具类
 * @author songwenxuan
 */
public class ToastUtils {
    
    private static Toast shortToast;
    private static Toast lenthToast;
    
    public static void showCustomToast(Context context,String str,int layoutId,int textId){
    	Toast toast = new Toast(context);
    	View tview = LayoutInflater.from(context).inflate(layoutId, null);
    	toast.setDuration(Toast.LENGTH_SHORT);
    	toast.setView(tview);
		toast.setGravity(Gravity.CENTER, 0, 0);
    	TextView text = (TextView) toast.getView().findViewById(textId);
    	text.setText(str);
    	toast.show();
    }
    
    public static void showToast(Context context,String message){
    	showToast(context, message, false);
    }
    
    public static void showToast(Context context,int resId){
    	String message = context.getResources().getString(resId);
    	showToast(context, message, false);
    }
    
    public static Toast showToast(Context context, String message, boolean longLength){
        Toast toast;
        
        if(longLength) {
        	if (lenthToast != null) {
        		toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
			}
        	toast = lenthToast;
        } else {
        	if (shortToast == null) {
        		shortToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
			}
        	toast = shortToast;
        }
        toast.setText(message);
        toast.show();
        return toast;
    }
}
