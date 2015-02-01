package com.m.rabbit.utils;

import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;

/**
 * Need to increase uses-permission <uses-permission
 * android:name="com.android.launcher.permission.INSTALL_SHORTCUT"/>
 */
public class ShortcutUtils {
    private Context mContext;
    private final String ISCREATED = "isCreated";
    private final String ISNOTICE = "isNotice";
    private final String TIME = "time";
    private final String TIMES = "times";
    private String shareName = "joysee_tv_shortcut";
    private long duration = 3600 * 24 * 7 * 1000;
    private int maxTimes = 3;

    public ShortcutUtils(Context context) {
        this.mContext = context;
    }

    public void createShortcut(int appNameId, int appIconId) {
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");

        if (appNameId > 0) {
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, mContext.getString(appNameId));
        }
        if (appIconId > 0) {
            Parcelable icon = Intent.ShortcutIconResource.fromContext(mContext, appIconId);
            shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        }
        shortcut.putExtra("duplicate", false);
        Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(mContext.getPackageName());
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);

        mContext.sendBroadcast(shortcut);
        saveInfo(true, false);
    }

    public boolean isNeedCreate() {
        SharedPreferences preferences = mContext.getSharedPreferences(shareName, Context.MODE_PRIVATE);
        boolean iscreatd = preferences.getBoolean(ISCREATED, false);
        boolean isNotice = preferences.getBoolean(ISNOTICE, true);
        long time = preferences.getLong(TIME, 0L);
        int times = preferences.getInt(TIMES, 1);
        return !iscreatd && isNotice && (time <= 0 || (new Date().getTime() - time) >= duration) && times <= maxTimes;
    }

    public void saveInfo(boolean isCreated, boolean isNotice) {
        SharedPreferences preferences = mContext.getSharedPreferences(shareName, Context.MODE_PRIVATE);
        int times = preferences.getInt(TIMES, 1);
        preferences.edit().putBoolean(ISCREATED, isCreated).putBoolean(ISNOTICE, isNotice)
                .putLong(TIME, new Date().getTime()).putInt(TIMES, ++times).commit();
    }
}
