package com.m.rabbit.net.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.net.URL;

import com.m.rabbit.net.ihttp.HttpHandlerStateListener;

public class HttpImageTask extends IHttpTask {

    public HttpImageTask() {
        super();
    }
    
    @Override
    protected ByteArrayOutputStream getHttpData(String url, InetAddress address, byte[] data) {
        return getHttpData(url, address);
    }

    @Override
    protected ByteArrayOutputStream getHttpData(String url, InetAddress address) {
        HttpURLConnection conn = null;
        InputStream inputStream = null;
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
            conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setDoInput(true);
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.setRequestMethod(mHttpRequestMethod);
            for (int i = 0; i < 2; i++) {
                conn.connect();
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    try {
                        inputStream = conn.getInputStream();
                        byte[] bytes = new byte[CACHE_LEN];
                        int length = -1;
                        int downloadedSize=0;
                        while ((length = inputStream.read(bytes)) != -1) {
                            os.write(bytes, 0, length);
                            downloadedSize += length;
                            if(downloadedSize>2048*1000){
                                os=null;
                                break;
                            }
                        }
                        return os;
                    } catch (SocketTimeoutException socketTimeoutException) {
                        if (mHttpHandlerStateListener != null) {
                            mHttpHandlerStateListener.setHttpResponseState(this, HttpHandlerStateListener.SOCKET_TIMEOUT_EXCEPTION);
                        }
                        socketTimeoutException.printStackTrace();

                    } catch (OutOfMemoryError error) {
                        if (mHttpHandlerStateListener != null) {
                            mHttpHandlerStateListener.setHttpResponseState(this,
                                    HttpHandlerStateListener.OUTOF_MEMORY_ERROR);
                        }
                        error.printStackTrace();
                    } catch (IOException e) {
                        if (mHttpHandlerStateListener != null) {
                            mHttpHandlerStateListener.setHttpResponseState(this, HttpHandlerStateListener.IO_EXCEPTION);
                        }
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return null;
    }


}
