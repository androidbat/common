package com.m.rabbit.httpprovider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.os.SystemClock;

import com.google.gson.Gson;
import com.m.rabbit.DataListener;
import com.m.rabbit.cache.DiskCache;
import com.m.rabbit.cache.engine.behavior.ICacheElement;
import com.m.rabbit.net.http.HttpStringTask;
import com.m.rabbit.net.http.HttpTask;
import com.m.rabbit.task.MeAsyncTask;
import com.m.rabbit.utils.LLog;

/**
 * The actual AsyncTask that will asynchronously process the image.
 */
public class StringTask extends MeAsyncTask<Object, Void, Object>{
	private final static String TAG =  StringTask.class.getSimpleName();
    private byte[] data;
    private boolean useCache;
    private DataListener mDataListener;
    private int responseState = -1;
    String tempData = null;
    private HttpTask httpTask;

    public StringTask(boolean useCache,DataListener listener1) {
    	this.useCache = useCache;
    	this.mDataListener = listener1;
    }
    
    public void setData(byte[] data) {
		this.data = data;
	}

	/**
     * Background processing.
     */
    @Override
    protected Object doInBackground(Object... params) {
    	String url = (String) (params[0]);
    	
    	LLog.d("string"," url "+url);
    	
    	String result = null;
    	
    	if (useCache) {
    		if (!isCancelled() && !exitTaskEarly) {
    			result = getCache(url);
			}
		} 
    	
    	
    	if (result == null) {
    		if (!isCancelled() && !exitTaskEarly) {
    			result = getHttpData(url);
    		}
    	}
    	
		
		if (result != null) {
			LLog.d(TAG," end "+result.toString());
			if (!isCancelled() && !exitTaskEarly) {
				try {
					if (mDataListener != null && mDataListener.mType != null) {
						return mDataListener.resolve(result);
					}else{
						return result;
					}
				} catch (Exception e) {
					if (LLog.SHOW_LOG) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return null;
		
    }

    private String getCache(String url) {
    	try {
			ICacheElement element = DiskCache.getStringCache().getCacheElement(url.hashCode());
			if (element != null) {
				if (!element.isExpired()) {
					return (String) element.getVal();
				} else {
					tempData = (String) element.getVal();
				}
			}
		} catch (Exception e) {
			if (LLog.SHOW_LOG) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private String getHttpData(String url) {
    	long start = SystemClock.uptimeMillis();
    	httpTask = new HttpTask();
		if (!isCancelled()) {
			ByteArrayOutputStream bao = httpTask.getHttpData(url, null);
			responseState = httpTask.responseState;
			if (bao != null) {
				String result = bao.toString();
				try {
					bao.close();
				} catch (IOException e1) {
				}
				
				if (result != null && useCache) {
					try {
						DiskCache.getStringCache().put(url.hashCode(), result);
					} catch (Exception e) {
						if (LLog.SHOW_LOG) {
							e.printStackTrace();
						}
					}
				}
				
				LLog.d(TAG,"httpTask "+(SystemClock.uptimeMillis() - start));
				return result;
			}
			
		}
		return null;
	}

	/**
     * Once the image is processed, associates it to the imageView
     */
    @Override
    protected void onPostExecute(Object value) {
        // if cancel was called on this task or the "exit early" flag is set then we're done
        if (isCancelled() || exitTaskEarly) {
        	LLog.d(TAG,"isCancelled()");
            return;
        }
        
        if (mDataListener != null) {
        	mDataListener.onTaskComplete(value);
        	if (value != null) {
        		mDataListener.onDataReady(value);
        	}else{
        		mDataListener.onNoData(responseState == HttpTask.SUCCESS?HttpTask.DATA_NULL:responseState);
        	}
		}
    }
    
    @Override
    protected void onCancelled() {
    	super.onCancelled();
    }
    
    @Override
    public void cancel() {
    	if (httpTask != null) {
    		httpTask.cancel();
		}
    	super.cancel();
    }
}