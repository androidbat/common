package com.m.rabbit.constant;

import java.util.UUID;

import com.m.rabbit.config.MyConfig;
import com.m.rabbit.utils.AppUtils;
import com.m.rabbit.utils.NetUtils;
import com.m.rabbit.utils.StringUtils;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;


public class DeviceConstants {
    public static final String PREFERENCES_KEY_UID="uid";
    public int mScreenWidth = 480;
    public int mScreenHeight = 800;

    private String mUID;
    public String mPID;
    public String mMac;
    public int mNetType;
    public String mNetName;
    public String mDeviceName;
    public String mVersion;
    public String mSystemVersion;
    public String mHasSim;

    private static DeviceConstants mInstance;

    /**
     * 如果需要全局单例，并且多线程并发，请做同步
     * @param context
     * @return
     */
    public static DeviceConstants getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DeviceConstants(context);
        }
        return mInstance;
    }

    private DeviceConstants(Context context) {
        mUID=initUid(context);
        mPID = NetUtils.getDeviceId();
        mMac = NetUtils.getLocalMacAddress();
        mNetType = NetUtils.checkNetState();
        mNetName = NetUtils.getNetworkStringByType(mNetType);
        mDeviceName = Build.MODEL;
        mVersion = AppUtils.getVersionName();
        mSystemVersion = Build.VERSION.RELEASE;

        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
    }
    
    private String initUid(Context c){
        String uid = MyConfig.getUserId();
        if(StringUtils.isEmpty(uid)||"0".equals(uid.trim())){
            uid = UUID.randomUUID().toString();
        }
        MyConfig.setUserId(uid);
        return uid;
    }

    public String getmUID() {
        return mUID;
    }

    public void setmUID(String mUID) {
        this.mUID = mUID;
    }
    
    
}
