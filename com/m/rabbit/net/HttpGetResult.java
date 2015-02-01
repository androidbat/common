package com.m.rabbit.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.m.rabbit.net.http.HttpImageTask;
import com.m.rabbit.net.http.HttpStringTask;
import com.m.rabbit.net.http.IHttpTask;
import com.m.rabbit.net.ihttp.HttpConnectionListener;
import com.m.rabbit.net.ihttp.HttpHandlerStateListener;
import com.m.rabbit.task.MyAsyncTaskHandler;


public class HttpGetResult {
    public static final String TAG_STRING = "RequestStringTask";
    public static final String TAG_IMAGE = "RequestImageTask";
    private InetAddress address=null;
    private RequestListener mRequestListener;
    private int httpState=HttpHandlerStateListener.DATA_NULL;
    private Object data;
    
    protected HttpGetResult(){
    }
    
    public void getString(final String url){
        if(url==null || url.trim().equals("")){
            return;
        }
        IHttpTask http=new HttpStringTask();
        http.setRequestUrl(url);
        http.setInetAddress(address);
        http.setmHttpConnectionListener(new HttpConnectionListener(){

            @Override
            public void downloadEnd(IHttpTask http, Object data) {
                if(http.getRequestUrl().equals(url)){
                    HttpGetResult.this.data=data;
                    if(mRequestListener!=null){
                        mRequestListener.onResult(data,httpState);        
                    }
                }                       
            }
        });
        http.setmHttpHandlerStateListener(new HttpHandlerStateListener(){

            @Override
            public void setHttpResponseState(IHttpTask http, int state) {
                httpState=state;                
            }
        });
        MyAsyncTaskHandler.execute(TAG_STRING,http);
    }
    
    public void getString(final String url,final String requestMethod){
        getString(url, requestMethod, null);
    }
    
    public void getString(final String url,final String requestMethod, HashMap<String, String> formMap){
        if(url==null || url.trim().equals("")){
            return;
        }
        IHttpTask http=new HttpStringTask();
        http.setRequestUrl(url);
        http.setInetAddress(address);
        http.setmHttpRequestMethod(requestMethod);
        if(formMap != null){
            http.setPostFormBoby(formMap);
        }
        http.setmHttpConnectionListener(new HttpConnectionListener(){
            
            @Override
            public void downloadEnd(IHttpTask http, Object data) {
                if(http.getRequestUrl().equals(url)){
                    HttpGetResult.this.data=data;
                    mRequestListener.onResult(data,httpState);        
                }                       
            }
        });
        http.setmHttpHandlerStateListener(new HttpHandlerStateListener(){
            
            @Override
            public void setHttpResponseState(IHttpTask http, int state) {
                httpState=state;                
            }
        });
        MyAsyncTaskHandler.execute(TAG_STRING,http);
    }
    /**
     * 上传byte数据
     * @author mingrenhan
     * 2012-6-19
     * @param url
     * @param requestMethod
     * @param formMap
     */
    public void postByteDate(final String url,final String requestMethod, byte[] byteArray){
        if(url==null || url.trim().equals("")){
            return;
        }
        IHttpTask http=new HttpStringTask();
        http.setRequestUrl(url);
        http.setInetAddress(address);
        http.setmHttpRequestMethod(requestMethod);
        if(byteArray != null){
            http.setPostFormBoby(byteArray);
        }
        http.setmHttpConnectionListener(new HttpConnectionListener(){
            
            @Override
            public void downloadEnd(IHttpTask http, Object data) {
                if(http.getRequestUrl().equals(url)){
                    HttpGetResult.this.data=data;
                    if(mRequestListener!=null){
                        mRequestListener.onResult(data,httpState);        
                    }
                }                       
            }
        });
        http.setmHttpHandlerStateListener(new HttpHandlerStateListener(){
            
            @Override
            public void setHttpResponseState(IHttpTask http, int state) {
                httpState=state;                
            }
        });
        MyAsyncTaskHandler.execute(TAG_STRING,http);
    }
    
//    public void getBitmap(final String url,final View imageView){
//        if(url==null || url.trim().equals("") || imageView==null){
//            return;
//        }
//        HttpTask http=new HttpImageTask();
//        http.setRequestUrl(url);
//        http.setInetAddress(address);
//        http.setmHttpConnectionListener(new HttpConnectionListener(){
//
//            @Override
//            public void downloadEnd(HttpTask http, Object data) {
//                if(data!=null){
//                    if(url.equals(imageView.getTag().toString())){
//                        ByteArrayOutputStream os=(ByteArrayOutputStream)data;
//                        ByteArrayInputStream in = new ByteArrayInputStream(os.toByteArray());
//                        Drawable drawable = new BitmapDrawable(in);
//                        imageView.setBackgroundDrawable(drawable);
//                    }                    
//                }              
//            }
//            
//        });
//        http.setmHttpHandlerStateListener(new HttpHandlerStateListener(){
//
//            @Override
//            public void setHttpResponseState(HttpTask http, int state) {
//                httpState=state;                
//            }
//            
//        });
//        MyAsyncTaskHandler.execute(TAG_IMAGE,http);
//    }

//    public void getBitmap(final String url,final Map<String, Drawable> drawables,final View imageView){
//        if(url==null || url.trim().equals("") || imageView==null){
//            return;
//        }
//        HttpTask http=new HttpImageTask();
//        http.setRequestUrl(url);
//        http.setInetAddress(address);
//        http.setmHttpConnectionListener(new HttpConnectionListener(){
//
//            @Override
//            public void downloadEnd(HttpTask http, Object data) {
//                if(data!=null){
//                    if(url.equals(imageView.getTag().toString())){
//                        ByteArrayOutputStream os=(ByteArrayOutputStream)data;
//                        ByteArrayInputStream in = new ByteArrayInputStream(os.toByteArray());
//                        Drawable drawable = new BitmapDrawable(in);
//                        imageView.setBackgroundDrawable(drawable);
//                        if(drawables!=null){
//                            drawables.put(url, drawable);
//                        }
//                    }                    
//                }              
//            }
//            
//        });
//        http.setmHttpHandlerStateListener(new HttpHandlerStateListener(){
//
//            @Override
//            public void setHttpResponseState(HttpTask http, int state) {
//                httpState=state;                
//            }
//            
//        });
//        MyAsyncTaskHandler.execute(TAG_IMAGE,http);
//    }
    
    public void getBitmap(final String url,final HttpConnectionListener listener){
        if(url==null || url.trim().equals("") || listener==null){
            return;
        }
        IHttpTask http=new HttpImageTask();
        http.setRequestUrl(url);
        http.setInetAddress(address);
        http.setmHttpConnectionListener(listener);
        http.setmHttpHandlerStateListener(new HttpHandlerStateListener(){

            @Override
            public void setHttpResponseState(IHttpTask http, int state) {
                httpState=state;                
            }
            
        });
        MyAsyncTaskHandler.execute(TAG_IMAGE,http);
    }
    
    public void setInetAddress(InetAddress address){
        this.address=address;
    }

    public void setOnRequestListener(RequestListener mRequestListener) {
        this.mRequestListener = mRequestListener;
    }
    
    public Object getData(){
        return data;
    }
    
    public int getHttpState(){
        return httpState;
    }
}
