package com.m.rabbit.receiver;

import com.m.rabbit.utils.NetUtils;
import com.m.rabbit.utils.NetworkObservable;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetWorkBroadCastReceiver extends BroadcastReceiver {
    
    public static int currentNetStaus;
    @Override
    public void onReceive(Context context, Intent intent) {
        currentNetStaus=NetUtils.checkNetState();
        NetworkObservable.getInstance().setNetType(currentNetStaus);
    }
}
