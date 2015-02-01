package com.m.rabbit.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;


public class NotificationUtil {
	
	public static void sendNotification(Context context, String title,
            String message, Bundle extras,Class activity,int iconId) {
        Intent mIntent = new Intent(context, activity);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mIntent.putExtras(extras);

        int requestCode = (int) System.currentTimeMillis();

        PendingIntent mContentIntent = PendingIntent.getActivity(context,
                requestCode, mIntent, 0);

        Notification mNotification = new NotificationCompat.Builder(context)
                .setContentTitle(title).setSmallIcon(iconId)
                .setContentIntent(mContentIntent).setContentText(message)
                .build();
        mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
        mNotification.defaults = Notification.DEFAULT_ALL;

        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(requestCode, mNotification);
    }
}
