package com.m.rabbit.net.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.net.URL;

import com.m.rabbit.net.ihttp.HttpHandlerStateListener;


public class HttpStringTask extends IHttpTask{
    public HttpStringTask() {
        super();
    }
    
    protected String getHttpData(String url,InetAddress address){
        return getHttpData(url, address, null);
    }
    /**
     * http request
     * 
     * @param url
     * @return String
     */
    protected String getHttpData(String url,InetAddress address, byte[] body) {       
        if(url == null){
            return null;
        }       
        InputStream inputStream = null;
        OutputStream outputStream = null;
        HttpURLConnection conn = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            URL httpUrl = new URL(url);
            Proxy proxy = null;                 
            if (address != null) {
                proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(address, 80));
            }
            
            if (proxy != null) {
                conn = (HttpURLConnection) httpUrl.openConnection(proxy);
            } else {
                conn = (HttpURLConnection) httpUrl.openConnection();
            }
            conn.setDoInput(true);
            if(body != null && body.length > 0){
                conn.setDoOutput(true);
            }
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.setReadTimeout(CONNECTION_TIMEOUT);
            conn.setRequestMethod(mHttpRequestMethod);            
            for (int i = 0; i < 2; i++) {
                conn.connect();
                if(body != null && body.length > 0){
                    outputStream = conn.getOutputStream();
                    outputStream.write(body);
                    outputStream.flush();
                }
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    
                    inputStream = conn.getInputStream();
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] bytes = new byte[CACHE_LEN];
                    int length = -1;
                    int downloadedSize=0;
                    while ((length = inputStream.read(bytes)) != -1) {
                        byteArrayOutputStream.write(bytes,0,length);
                        downloadedSize=downloadedSize+length;
                        if(downloadedSize>2048*1000){
                            byteArrayOutputStream.reset();
                            break;
                        }
                    }
                    if (inputStream != null) {
                        inputStream.close();
                        inputStream = null;
                    }
                    
                    if (conn != null) {
                        conn.disconnect();
                        conn = null;
                    }
                    
                    return byteArrayOutputStream.toString();
                }
            }
        } catch (SocketTimeoutException socketTimeoutException) {
            if(mHttpHandlerStateListener!=null){
                mHttpHandlerStateListener.setHttpResponseState(this, HttpHandlerStateListener.SOCKET_TIMEOUT_EXCEPTION);
            }
            socketTimeoutException.printStackTrace();
               
        } catch (OutOfMemoryError error) {
            if(mHttpHandlerStateListener!=null){
                mHttpHandlerStateListener.setHttpResponseState(this, HttpHandlerStateListener.OUTOF_MEMORY_ERROR);
            }
            error.printStackTrace();             
        }catch(IOException e){
            if(mHttpHandlerStateListener != null){
                mHttpHandlerStateListener.setHttpResponseState(this, HttpHandlerStateListener.IO_EXCEPTION);
            }
            e.printStackTrace();              
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {                   
                    e.printStackTrace();
                }
                inputStream = null;
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {                   
                    e.printStackTrace();
                }
                outputStream = null;
            }
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                byteArrayOutputStream = null;
            }           
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }       
        return null;
    }

}
