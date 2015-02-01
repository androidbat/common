package com.m.rabbit.upload.http;


import java.net.InetSocketAddress;
import java.net.Proxy;




import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Set;

import com.m.rabbit.download.task.Task;
import com.m.rabbit.utils.LLog;
import com.m.rabbit.utils.NetUtils;

public class UploadHttpConnection {
	private static final String CHARSET = "utf-8"; // 设置编码  
    private String url;
    
    public static final String CONNECTION_ERR = "err";

    public UploadHttpConnection(String url) {
        this.url = url;

    }

    public String post() throws IOException {
        return post(null, null,false);
    }

    public String post(HashMap<String, String> headerMap) throws IOException {
        return post(headerMap, null,false);
    }

    public String post(byte[] body,boolean needProxy) throws IOException {
        return post(null, body,needProxy);
    }

    public String post(HashMap<String, String> headerMap, byte[] body,boolean needProxy)
            throws IOException {
        HttpURLConnection httpUrlConn = null;
        InputStream is = null;
        OutputStream out = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        Proxy proxy = null;   
        try {
            if(needProxy){
                proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(NetUtils.getHostbyWAP(), 80));
            }
            if (proxy != null) {
                httpUrlConn = (HttpURLConnection) new URL(url).openConnection(proxy);
            } else {
                httpUrlConn = (HttpURLConnection) new URL(url).openConnection();
            }
            httpUrlConn.setDoOutput(true);//允许输出流  
            httpUrlConn.setDoInput(true);// 允许输入流
            httpUrlConn.setUseCaches(false); // 不允许使用缓存
            httpUrlConn.setRequestMethod("POST"); // 请求方式  
//            httpUrlConn.setRequestProperty("Charset", CHARSET); // 设置编码  
            httpUrlConn.setRequestProperty("connection", "keep-alive");  
            httpUrlConn.setReadTimeout(Task.CONNECT_TIME_OUT);
            httpUrlConn.setConnectTimeout(Task.CONNECT_TIME_OUT);
            if (headerMap != null && headerMap.size() > 0) {
                Set<String> keys = headerMap.keySet();
                for (String key : keys) {
                    httpUrlConn.setRequestProperty(key, headerMap.get(key));
                }
            }else{
            	httpUrlConn.setRequestProperty("enctype", "multipart/form-data");
            	httpUrlConn.setRequestProperty("content-type", "text/html"); 
            }
            httpUrlConn.connect();
//            body="1".getBytes();
            if (body != null && body.length > 0) {
                out = httpUrlConn.getOutputStream();
                out.write(body);
                out.flush();
            }
            int code = httpUrlConn.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK ) {
                is = httpUrlConn.getInputStream();
                byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] b = new byte[1024];
                int len = -1;
                while ((len = is.read(b)) != -1) {
                    byteArrayOutputStream.write(b, 0, len);
                }
                return byteArrayOutputStream.toString();
            }else{
                return CONNECTION_ERR; 
            }

        }catch (Exception e) {
            LLog.printStackTrace(e);
            return CONNECTION_ERR; 
        } finally {
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
            if (httpUrlConn != null) {
                httpUrlConn.disconnect();
                httpUrlConn = null;
            }
        }
    }
}
