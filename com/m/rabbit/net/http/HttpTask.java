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
import java.util.HashMap;
import java.util.Set;

import android.R.integer;

import com.m.rabbit.net.ihttp.HttpHandlerStateListener;
import com.m.rabbit.task.MeAsyncTask;
import com.m.rabbit.utils.LLog;

public class HttpTask {
	
	public static final int SUCCESS=0;
    public static final int SOCKET_TIMEOUT_EXCEPTION=1;
    public static final int OUTOF_MEMORY_ERROR=2;
    public static final int IO_EXCEPTION=3;
    public static final int DATA_NULL=4;

	protected static final int CONNECTION_TIMEOUT = 7000;   //7s
    protected static final int CACHE_LEN = 4096;  //4KB buffer
    protected String mHttpRequestMethod="GET";
    private boolean isActive = true;
    private byte[] data;
    
    public void cancel(){
    	isActive = false;
    }
    
    public int responseState = -1;
    public HttpTask() {
        super();
    }
    
    public void setRequestMethod(String requestMethod){
    	mHttpRequestMethod = requestMethod;
    }
    
    public ByteArrayOutputStream getHttpData(String url, InetAddress address) {
    	if (url == null) {
			return null;
		}
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
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
            if(data != null && data.length > 0){
            	conn.setRequestMethod("POST");
            }else {
            	conn.setRequestMethod(mHttpRequestMethod);
			}
			for (int i = 0; i < 2; i++) {
				try {
					conn.connect();
					
		            if(data != null && data.length > 0){
		            	conn.setDoInput(true);
		                conn.setDoOutput(true);
		                if(data != null && data.length > 0){
		                    outputStream = conn.getOutputStream();
		                    outputStream.write(data);
		                    outputStream.flush();
		                }
		            }
		            
		            
					if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
						ByteArrayOutputStream os = new ByteArrayOutputStream();
						inputStream = conn.getInputStream();
						byte[] bytes = new byte[CACHE_LEN];
						int length = -1;
						int downloadedSize = 0;
						int totel = conn.getContentLength();
						int progress = 0;
						while (isActive && (length = inputStream.read(bytes)) != -1) {
							os.write(bytes, 0, length);
							downloadedSize += length;
							int p = (int) ((float)downloadedSize*100/totel);
							if (totel > 0 && p != progress) {
								progress = p;
								if (mEventHandler != null) {
									mEventHandler.onProgressChanged(progress);
								}
							}
							if (downloadedSize > 3*1024*1024) {
								os = null;
								break;
							}
						}
						
						responseState = SUCCESS;
						if (!isActive) {
							return null;
						}
						return os;
					}
				} catch (SocketTimeoutException socketTimeoutException) {
					responseState = SOCKET_TIMEOUT_EXCEPTION;
					if (LLog.SHOW_LOG) {
						LLog.d(" httptask SOCKET_TIMEOUT_EXCEPTION");
						socketTimeoutException.printStackTrace();
					}
				} catch (OutOfMemoryError error) {
					responseState = OUTOF_MEMORY_ERROR;
					if (LLog.SHOW_LOG) {
						LLog.d(" httptask OUTOF_MEMORY_ERROR");
						error.printStackTrace();
					}
				} catch (IOException e) {
					responseState = IO_EXCEPTION;
					if (LLog.SHOW_LOG) {
						LLog.d(" httptask IO_EXCEPTION");
						e.printStackTrace();
					}
				} catch (Exception e) {
					responseState = IO_EXCEPTION;
					if (LLog.SHOW_LOG) {
						LLog.d(" httptask EXCEPTION");
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
            
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return null;
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
    
	public void setHttpEventHandler(HttpEventHandler mEventHandler) {
		this.mEventHandler = mEventHandler;
	}

	private HttpEventHandler mEventHandler;
    public interface  HttpEventHandler{
    	void onProgressChanged(int progress); 
    }

}
