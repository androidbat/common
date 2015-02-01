package com.m.rabbit.cache.memory;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

import com.m.rabbit.utils.BitmapDecodeUtils;
import com.m.rabbit.utils.LLog;

@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
public class MemoryBitmapCache {
	private static final String TAG = "memory";
	private MemoryBitmapCache(){
		int maxMemory = (int) Runtime.getRuntime().maxMemory()/1024;    
        int mCacheSize = maxMemory / 8;
        LLog.d("memory"," mcachesize "+mCacheSize);
		mLruCache = new LruCache<String, Bitmap>(mCacheSize){
			@Override  
            protected int sizeOf(String key, Bitmap bitmap) {
				int bitmapSize = getBitmapSize(bitmap);
				LLog.d("memory"," bitmapSize = "+bitmapSize);
                return bitmapSize == 0 ? 1 : bitmapSize;
            }  
			@Override
			protected void entryRemoved(boolean evicted, String key,
					Bitmap oldValue, Bitmap newValue) {
				LLog.d("memory"," entryRemoved ");
			}
		};
	}
	protected int getBitmapSize(Bitmap bitmap) {
		if (Build.VERSION.SDK_INT >= 12) {
            return bitmap.getByteCount()/1024;
        }

        return bitmap.getRowBytes() * bitmap.getHeight()/1024;  
	}
	
	private static MemoryBitmapCache instance;
	private LruCache<String, Bitmap> mLruCache;
	public static MemoryBitmapCache getInstance(){
		if (instance == null) {
			synchronized (MemoryBitmapCache.class) {
				if (instance == null) {
					instance = new MemoryBitmapCache();
				}
			}
		}
		return instance;
	}
	
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
	    if (TextUtils.isEmpty(key) || bitmap == null) {
	        return;
	    }
	    if (getBitmapFromMemCache(key) == null) {
	        synchronized (mLruCache) {
	            mLruCache.put(key, bitmap);
	        }
	    }
	}

	public Bitmap getBitmapFromMemCache(String key) {
	    if (TextUtils.isEmpty(key)) {
	        return null;
	    }

	    Bitmap bitmap;
	    synchronized (mLruCache) {
	        bitmap = mLruCache.get(key);
	        if (bitmap != null) {
	        	return bitmap;
			}
	    }
	    return null;
	}
	
	public Bitmap getResource(int id,Context context){
		Bitmap bm = getBitmapFromMemCache(id + "");
		if (bm == null) {
			LLog.d("memory"," bm == null ");
			bm = BitmapDecodeUtils.decodeResource(context, id);
			if (bm != null) {
				synchronized (mLruCache) {
					mLruCache.put(id + "", bm);
				}
			}
		}else{
			LLog.d("memory"," bm != null ");
		}
		return bm;
	}
	
	public Bitmap remove(String key){
		  synchronized (mLruCache) {
			  return mLruCache.remove(key);
		   }
	}
}
