package com.m.rabbit.utils;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources.NotFoundException;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

public class AppUtils {
	public static Context mContext;
	private static int sOsWidth = -1;
	private static int sOsHeight = -1;
	private static DisplayMetrics sDisplayMetrics;
	
	public static Context getContext(){
		return mContext;
	}

	public static void initContext(Context context) {
		mContext = context;
		sDisplayMetrics = mContext.getResources().getDisplayMetrics();
	}

	public static String getVersionName() {
		String versionName;
		try {
			versionName = mContext.getPackageManager().getPackageInfo(
					mContext.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			versionName = "Unknown";
		}
		return versionName;
	}

	public static int getVersionCode() {
		int versionCode;
		try {
			versionCode = mContext.getPackageManager().getPackageInfo(
					mContext.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			versionCode = -999;
		}
		return versionCode;
	}

	public static String getApplicationName() {
		int stringId = mContext.getApplicationInfo().labelRes;
		return mContext.getString(stringId);
	}

	public static int getDisplayHeight() {
		if (sOsHeight <= 0) {
			sOsHeight = sDisplayMetrics.heightPixels;
		}
		return sOsHeight;
	}

	public static int getDisplayWidth() {
		if (sOsWidth <= 0) {
			sOsWidth = sDisplayMetrics.widthPixels;
		}
		return sOsWidth;
	}

	public static int calculateByRatio(int width, float radio) {
		return (int) (width * radio);
	}

	public static void clear() {
		sDisplayMetrics = null;
	}

	public static String getMEID() {
		return ((TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	}

	public static int getDispalyDensityDpi() {
		return sDisplayMetrics.densityDpi;
	}

	public static String getString(int resId, Object... args) {
		try {
			return mContext.getString(resId, args);
		} catch (NotFoundException e) {
		}
		return null;
	}

	public static int getDimens(int resId) {
		try {
			return (int) mContext.getResources().getDimension(resId);
		} catch (NotFoundException e) {
		}
		return 0;
	}

	public static int dp2px(float dpValue) {
		float scale = sDisplayMetrics.density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dp(float pxValue) {
		float scale = sDisplayMetrics.density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int px2sp(float pxValue) {
		float fontScale = sDisplayMetrics.scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	public static int sp2px(float spValue) {
		float fontScale = sDisplayMetrics.scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}
	
}