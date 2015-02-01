package com.m.rabbit.utils;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.Map;
import java.util.Map.Entry;

/**
 * <h2>Utility methods for common Activity code</h2>
 *
 * <h3>Common uses:</h3>
 * <code>ActivityUtils.{@link #launchActivity launchActivity}(this, SomeNewActivity.class);</code><br />
 * <code>ActivityUtils.{@link #launchActivity launchActivity}(this, SomeNewActivity.class, extraParamsMap);</code><br />
 */
public class ActUtils {

	/**
	 * Launch an Activity.
	 * 
	 * @param context The current Context or Activity that this method is called from.
	 * @param activity The new Activity to open.
	 * @param params Parameters to add to the intent as a Bundle.
	 */
	public static void launchActivity(Context context, Class<? extends Activity> activity, Bundle bundle){
		Intent intent = new Intent(context, activity);
		if(bundle!=null){
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
 	}
	
	
	public static void launchActivity(Context context, Class<? extends Activity> activity){
		ActUtils.launchActivity(context, activity, null);
	}
	
	public static void launchActivityForResult(Activity context, Class<? extends Activity> activity,int requestCode,Bundle bundle){
		Intent intent = new Intent(context, activity);
		if(bundle!=null){
			intent.putExtras(bundle);
		}
		context.startActivityForResult(intent, requestCode);
	}
	
	/**
	 * Used to get the parameter values passed into Activity via a Bundle.
     *
	 * @return param Parameter value
	 */
    public static String getExtraString(Activity context, String key) {
    	String param = "";
    	Bundle bundle = context.getIntent().getExtras();
    	if(bundle!=null){
    		param = bundle.getString(key);
    	}
    	return param;
	}

	/**
	 * Used to get the parameter values passed into Activity via a Bundle.
     *
     * @param context The current Context or Activity that this method is called from
     * @param key Extra key name.
	 * @return param Parameter value
	 */
    public static Object getExtraObject(Activity context, String key) {
    	Object param = null;
    	Bundle bundle = context.getIntent().getExtras();
    	if(bundle!=null){
    		param = bundle.get(key);
    	}
    	return param;
	}
    
    /**
     * Force screen to turn on if the phone is asleep.
     *
     * @param context The current Context or Activity that this method is called from
     */
    public static void turnScreenOn(Activity context){
		try{
			Window window = context.getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
			window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
			window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		} catch(Exception ex){
            Log.e("PercolateAndroidUtils", "Unable to turn on screen for activity " + context);
		}
	}
    
    /**
     * Set activity FullScreen flag
     * @param activity
     * @param isFullScreen Whether activity is fullscreen
     */
	public static void setFullSreen(Activity activity, boolean isFullScreen) {
		if (isFullScreen) {
			activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		} else {
			final WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
			attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
			activity.getWindow().setAttributes(attrs);
			activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		}
	}
	
}
