package com.m.rabbit.utils;



import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;


import android.app.Service;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

public class NetUtils {
	private static Context context;
	public static final String NET_UNAVAILABLE = "None";
    public static final String NET_TYPE_WIFI = "WiFi";
    public static final String TYPE_MOBILE = "Mobile";

    public static final int NETWORK_UNKNOWN = -1;
    public static final int NETWORK_NONE = 0;
    public static final int NETWORK_WIFI = 1;
    public static final int NETWORK_MOBILE = 2;
    public static final int NETWORK_2G = 3;
    public static final int NETWORK_3G = 4;
    
    public static final String UNKNOWN = "Unknown";
    public static final String G3 = "3G";
    public static final String G2 = "2G";

    public static final String WAP_3G = "3gwap";
    public static final String UNIWAP = "uniwap";
    
    public static final String UNICOM_3GWAP = "3gwap";
    public static final String CTWAP = "ctwap";
    public static final String CMWAP = "cmwap";
    public static Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");
    
    /**
     * 获取网络类型：-1、未知，0、无网络，1、WiFi，2、移动网络，3、2G（移动网络），4、3G（移动网络）
     * */
    public static int checkNetState(){
        // TODO: Rewrite this method, which is in chaos !
        int networkType = NETWORK_UNKNOWN;
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        ConnectivityManager cManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return NETWORK_NONE;
        }

        if(wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED
                && networkInfo.getType() == ConnectivityManager.TYPE_WIFI){ //wifi可用
            return NETWORK_WIFI;
        }else{
            TelephonyManager mTelephonyManager=(TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
            if(mTelephonyManager.getSimState()!=TelephonyManager.SIM_STATE_READY) //SIM卡没有就绪
            {
                return NETWORK_NONE;
            }else{
                if (networkInfo.isAvailable()){
                    //能联网
                    int netType = mTelephonyManager.getNetworkType();
                    if (netType == TelephonyManager.NETWORK_TYPE_UNKNOWN) {
                        networkType = NETWORK_UNKNOWN;
                    }
                    if (netType == TelephonyManager.NETWORK_TYPE_GPRS
                            || netType == TelephonyManager.NETWORK_TYPE_EDGE) {
                        networkType = NETWORK_2G;
                    } else {
                        networkType = NETWORK_3G;
                    }
                     return networkType;
               }else{
                    //do something
                    //不能联网
                   return NETWORK_NONE;
               } 
                
            }
        }
    }
    public static String getNetworkStringByType(int networkType) {
        String networkString = "Unknown";
        switch (networkType) {
            case NETWORK_NONE:
                networkString = NET_UNAVAILABLE;
                break;
            case NETWORK_WIFI:
                networkString = NET_TYPE_WIFI;
                break;
            case NETWORK_MOBILE:
                networkString = TYPE_MOBILE;
                break;
            case NETWORK_2G:
                networkString = G2;
                break;
            case NETWORK_3G:
                networkString = G3;
                break;
            default:
                networkString = UNKNOWN;
                break;
        }
        return networkString;
    }
    
    private static String getWifiNetName() {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        return wifiManager.getConnectionInfo().getSSID();
    }
    private static String getMobileNetName() {
        String strNetName = null;

        Uri PREFERAPN_URI = android.net.Uri.parse("content://telephony/carriers/preferapn");
        Cursor cursor = context.getContentResolver().query(PREFERAPN_URI, null, null, null, null);
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex("apn");
            strNetName = cursor.getString(index);
        }
        if (cursor != null) {
            cursor.close();
        }
        // The phone which support double-SIMCard need to check it again
        Uri PREFERAPN_URI_2 = android.net.Uri.parse("content://telephony/carriers/preferapn2");
        cursor =  context.getContentResolver().query(PREFERAPN_URI_2, null, null, null, null);
        if (cursor != null && cursor.getCount() != 0) {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex("apn");
            strNetName = cursor.getString(index);
        }
        if (cursor != null) {
            cursor.close();
        }

        return strNetName;
    }
    
    public static String getDeviceId() {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (manager == null) {
            LLog.e("[NetTools|getDeviceId]Error, cannot get TelephonyManager!");
            return null;
        }
        return manager.getDeviceId();
    }
    
    public static String getLocalMacAddress() {
    	String macString = null;
    	
		if (macString == null) {
			WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo info = null;
			if (wifi != null) {
				info = wifi.getConnectionInfo();	
			}
			if (info != null) {
				macString = info.getMacAddress();
			}
		}
    	
        return macString;
    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
        }
        return null;
    }
    
    public static void initApplicationContext(Context context2) {
        context = context2;
    }
    
    public static Context getApplicationContext() {
        return context;
    }
    
    public static boolean isWifi() {
        return checkNetState() == NETWORK_WIFI;
    }
    
    public static String getHostbyWAP() {

        if (null == getApplicationContext()) {
            return null;
        }

        if (isWifi()) {
            return null;
        }

        try {
            String result = null;
            ConnectivityManager mag = (ConnectivityManager) getApplicationContext().getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            if (null != mag) {
                NetworkInfo mobInfo = mag.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (null != mobInfo) {

                    String extrainfo = mobInfo.getExtraInfo();
                    if (null != extrainfo) {
                        extrainfo = extrainfo.toLowerCase();
                        if (extrainfo.equals(CMWAP) || extrainfo.equals(WAP_3G) || extrainfo.equals(UNIWAP)) {// 移动
                                                                                                              // or
                                                                                                              // 联通wap代理
                            result = "10.0.0.172";
                        } else {
                            // 电信WAP判断
                            final Cursor c = getApplicationContext().getContentResolver().query(PREFERRED_APN_URI,
                                    null, null, null, null);
                            if (c != null) {
                                c.moveToFirst();
                                final String user = c.getString(c.getColumnIndex("user"));
                                if (StringUtils.isNotEmpty(user)) {
                                    if (user.toLowerCase().startsWith(CTWAP)) {
                                        result = "10.0.0.200";
                                    }
                                }
                                c.close();
                            }

                        }
                    }
                }
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }
    public static String getNetTypeForUpload() {
        String netType = NET_TYPE_WIFI;
        if (context != null) {
            int netState = checkNetState();
            if (netState == NETWORK_WIFI || netState == NETWORK_UNKNOWN || netState == NETWORK_NONE) {
                netType = NET_TYPE_WIFI;
            } else {// 2g or 3g
                String postfix = "";
                if (netState == NETWORK_2G) {
                    postfix = "2G";
                } else if (netState == NETWORK_3G) {
                    postfix = "3G";
                }
                TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (telManager != null) {
                    String imsi = telManager.getSubscriberId();
                    if (imsi != null) {
                        StringBuilder builder = new StringBuilder();
                        if (imsi.startsWith("46000") || imsi.startsWith("46002")) {// 因为移动网络编号46000下的IMSI已经用完，所以虚拟了一个46002编号，134/159号段使用了此编号
                            // 中国移动
                            netType = builder.append(imsi.substring(0, 3)).append("_").append(imsi.substring(3, 5))
                                    .append("_").append(postfix).toString();
                        } else if (imsi.startsWith("46001")) {
                            // 中国联通
                            netType = builder.append(imsi.substring(0, 3)).append("_").append(imsi.substring(3, 5))
                                    .append("_").append(postfix).toString();
                        } else if (imsi.startsWith("46003")) {
                            // 中国电信
                            netType = builder.append(imsi.substring(0, 3)).append("_").append(imsi.substring(3, 5))
                                    .append("_").append(postfix).toString();
                        } else {

                        }

                    }
                }
            }
        }
        return netType;
    }
    
    /**
     * Checks to see if the device is connected to a network (cell, wifi, etc).
     *
     * @param context The current Context or Activity that this method is called from
     * @return true if a network connection is available, otherwise false.
     */
	public static boolean isNetworkAvailable(Context context) {
	    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

    /**
     * Check if there is any connectivity to a Wifi network.
     *
     * Can be used in combination with {@link #isConnectedMobile}
     * to provide different features if the device is on a wifi network or a cell network.
     *
     * @param context The current Context or Activity that this method is called from
     * @return true if a wifi connection is available, otherwise false.
     */
    public static boolean isConnectedWifi(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    /**
     * Check if there is any connectivity to a mobile network
     *
     * Can be used in combination with {@link #isConnectedWifi}
     * to provide different features if the device is on a wifi network or a cell network.
     *
     * @param context The current Context or Activity that this method is called from
     * @return true if a mobile connection is available, otherwise false.
     */
    public static boolean isConnectedMobile(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }
    
}
