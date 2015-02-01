package com.m.rabbit.net.http;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Set;

import com.m.rabbit.net.ihttp.HttpConnectionListener;
import com.m.rabbit.net.ihttp.HttpHandlerStateListener;
import com.m.rabbit.task.IMyTask;

public abstract class IHttpTask implements IMyTask{
    protected static final int CONNECTION_TIMEOUT = 7000;   //7s
    protected static final int CACHE_LEN = 4096;  //4KB buffer
    protected HttpHandlerStateListener mHttpHandlerStateListener;
    private HttpConnectionListener mHttpConnectionListener;
    protected String mHttpRequestMethod="GET";
    private InetAddress address=null;
    private String url=""; 
    private byte[] data = null;
    
    public IHttpTask(){       
    }
        
    @Override
    public Object doSomeThing() {
        if(data != null){
            return getHttpData(url, this.address, data);
        }else{
            return getHttpData(url, this.address);
        }
    }
    
    @Override
    public void onDone(Object result) {
        if(result==null || result.toString().trim().equals("")){
            mHttpConnectionListener.downloadEnd(this, result);
        }else{
            if(mHttpHandlerStateListener!=null){
                mHttpHandlerStateListener.setHttpResponseState(this, HttpHandlerStateListener.SUCCESS);
            }
            mHttpConnectionListener.downloadEnd(this, result);
        }
    }
    
    public String getmHttpRequestMethod() {
        return mHttpRequestMethod;
    }

    public void setmHttpRequestMethod(String mHttpRequestMethod) {
        if(mHttpRequestMethod!=null && (mHttpRequestMethod.equalsIgnoreCase("get")||mHttpRequestMethod.equalsIgnoreCase("post")) ){
            this.mHttpRequestMethod = mHttpRequestMethod;
        }
    }

    public void setmHttpHandlerStateListener(
            HttpHandlerStateListener mHttpHandlerStateListener) {
        this.mHttpHandlerStateListener = mHttpHandlerStateListener;
    }  

    public void setmHttpConnectionListener(
            HttpConnectionListener mHttpConnectionListener) {
        this.mHttpConnectionListener = mHttpConnectionListener;
    }

    public void setInetAddress(InetAddress address){
        this.address=address;
    }
    
    public void setPostFormBoby(HashMap<String, String> formMap){
        if (formMap != null && formMap.size() > 0) {
            StringBuffer buffer = new StringBuffer();
            Set<String> keys = formMap.keySet();
            for (String key : keys) {
                buffer.append(key);
                buffer.append("=");
                buffer.append(formMap.get(key));
                buffer.append("&");
            }
            data = buffer.substring(0, buffer.length() - 1).getBytes();
        }
    }
    /**
     * 上传byte数组类型数据
     * @author mingrenhan
     * 2012-6-19
     * @param formMap
     */
    public void setPostFormBoby(byte[] byteArray){
        if (byteArray != null && byteArray.length > 0) {
            data = byteArray;
        }
    }
    
//    public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor){
//        mThreadPoolExecutor=threadPoolExecutor;
//    }
    
    public void setRequestUrl(String requestUrl)
    {
        if(requestUrl != null)
        {
            try
            {
                url = requestUrl.trim();
                url = url.replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "");
            }
            catch(Exception ex)
            {
            }
        }
    }
    
    public String getRequestUrl(){
        return url;
    }
    /**
     * http request
     * 
     * @param url
     * @return
     */
    protected abstract  Object getHttpData(String url,InetAddress address);    

    protected abstract  Object getHttpData(String url,InetAddress address, byte[] data);
    
}
