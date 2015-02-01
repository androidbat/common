package com.m.rabbit.constant;

import com.m.rabbit.utils.PropertiesUtils;


public class AppConstants {
    public String mPoid;
    public String mClientType;
    private String mPlatform;
    public String mPartnerNo;

    private static final AppConstants mIntance = new AppConstants();

    public static AppConstants getInstance() {
        return mIntance;
    }

    private AppConstants() {
        mPoid = PropertiesUtils.getValueA(PropertiesUtils.PROPERTIES_COMMON_PATH, PropertiesUtils.POID);
        mClientType = PropertiesUtils.getValueA(PropertiesUtils.PROPERTIES_COMMON_PATH, PropertiesUtils.CLIENT);
        mPartnerNo = PropertiesUtils.getValueA(PropertiesUtils.PROPERTIES_COMMON_PATH, PropertiesUtils.PARTNERNO);

        // Ugly hacking to make it work in eclipse (i.e. without ant)
        if ("@token@".equals(mPartnerNo)) {
            mPartnerNo = "999";
        }
    }

    public String getmPlatform() {
        if (mPlatform==null || mPlatform.equals("")) {
            mPlatform = PropertiesUtils.getValueA(PropertiesUtils.PROPERTIES_COMMON_PATH, PropertiesUtils.PLATFORM);
        }
        return mPlatform;
    }

    public void setmPlatform(String mPlatform) {
        this.mPlatform = mPlatform;
    }

}
