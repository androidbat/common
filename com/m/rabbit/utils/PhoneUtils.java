package com.m.rabbit.utils;

import android.content.Context;
import android.provider.Settings;

/**
 * Common phone utility methods
 *
 */
public class PhoneUtils {

    /**
     * Checks to see if the user has rotation enabled/disabled in their phone settings.
     *
     * @param context The current Context or Activity that this method is called from
     * @return true if rotation is enabled, otherwise false.
     */
	public static boolean isRotationEnabled(Context context) {
        return android.provider.Settings.System.getInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0) == 1;
    }

}
