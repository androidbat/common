package com.m.rabbit.net;

import java.util.HashMap;
import java.util.Map;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.m.rabbit.net.ihttp.HttpConnectionListener;

public class HttpRequestFactory {
    public static final String HTTP_GET="GET";
    public static final String HTTP_POST="POST";
    
    public static void getString(final String url,final RequestListener requestListener){
        if(url==null || url.trim().equals("")){
            return;
        }
        HttpGetResult  httpGetResult=new HttpGetResult();
        if(requestListener != null){
        	httpGetResult.setOnRequestListener(requestListener);
        }
        httpGetResult.getString(url);
    }
    
    public static void getString(final String url){
    	getString(url, null);
    }
    
    public static void getString(final String url,final RequestListener requestListener,final String requestMethod){
        getString(url, requestListener, requestMethod, null);
    }
    
    public static void getString(final String url,final RequestListener requestListener,final String requestMethod, HashMap<String, String> formMap){
        if(url==null || url.trim().equals("")){
            return;
        }
        HttpGetResult  httpGetResult=new HttpGetResult();
        httpGetResult.setOnRequestListener(requestListener);
        if(formMap != null){
            httpGetResult.getString(url,requestMethod, formMap); 
        }else{
            httpGetResult.getString(url,requestMethod);
        }
    }
    /**
     * 上传byte数据
     * @author mingrenhan
     * 2012-6-19
     * @param url
     * @param requestListener
     * @param requestMethod
     * @param formMap
     */
    public static void postByteDate(final String url,final RequestListener requestListener,final String requestMethod, byte[] byteArray){
        if(url==null || url.trim().equals("")){
            return;
        }
        if(byteArray!=null && byteArray.length>0){
            HttpGetResult  httpGetResult=new HttpGetResult();
            httpGetResult.setOnRequestListener(requestListener);
            httpGetResult.postByteDate(url,requestMethod, byteArray); 
        }
    }
    
//    public static void getBitmap(final String url,final View imageView){
//        if(url==null || url.trim().equals("") || imageView==null){
//            return;
//        }
//        HttpGetResult  httpGetResult=new HttpGetResult();
//        httpGetResult.getBitmap(url,imageView);
//    }
    
//    public static void getBitmap(final String url,Map<String, Drawable> drawables,final View imageView){
//        if(url==null || url.trim().equals("") || imageView==null){
//            return;
//        }
//        HttpGetResult  httpGetResult=new HttpGetResult();
//        httpGetResult.getBitmap(url,drawables,imageView);
//    }
    
    public static void getBitmap(final String url, HttpConnectionListener listener){
        if (url == null || url.trim().equals("")) {
            return;
        }
        HttpGetResult  httpGetResult=new HttpGetResult();
        httpGetResult.getBitmap(url, listener);
    }
}
