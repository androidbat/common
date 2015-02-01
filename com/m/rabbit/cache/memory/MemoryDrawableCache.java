package com.m.rabbit.cache.memory;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

import com.m.rabbit.utils.LLog;

public class MemoryDrawableCache {
	private MemoryDrawableCache(){
		int maxMemory = (int) Runtime.getRuntime().maxMemory()/1024;    
        int mCacheSize = maxMemory / 8;
        LLog.d("bitmap"," mcachesize "+mCacheSize);
		mLruCache = new LruCache<String, BitmapDrawable>(mCacheSize){
			@Override  
            protected int sizeOf(String key, BitmapDrawable bitmap) {
				int size = getBitmapSize(bitmap.getBitmap());
				LLog.d("bitmap"," size = "+size);
		        return size;  
            }  
			@Override
			protected void entryRemoved(boolean evicted, String key,
					BitmapDrawable oldValue, BitmapDrawable newValue) {
				LLog.d("bitmap"," entryRemoved ");
//	            if (oldValue != null && !oldValue.isRecycled()) {
//	                oldValue.recycle();
//	            }
			}
		};
	}
	protected int getBitmapSize(Bitmap bitmap) {
//		if (Build.VERSION.SDK_INT >= 12) {
//            return bitmap.getByteCount()/1024;
//        }

        return bitmap.getRowBytes() * bitmap.getHeight()/1024;  
	}
	private static MemoryDrawableCache instance;
	private LruCache<String, BitmapDrawable> mLruCache;
	public static MemoryDrawableCache getInstance(){
		if (instance == null) {
			synchronized (MemoryDrawableCache.class) {
				if (instance == null) {
					instance = new MemoryDrawableCache();
				}
			}
		}
		return instance;
	}
	
	public void addBitmapToMemoryCache(String key, BitmapDrawable bitmap) {
	    if (TextUtils.isEmpty(key) || bitmap == null) {
	        return;
	    }
	    if (getBitmapFromMemCache(key) == null) {
	        synchronized (mLruCache) {
	            mLruCache.put(key, bitmap);
	        }
	    }
	}

	public BitmapDrawable getBitmapFromMemCache(String key) {
	    if (TextUtils.isEmpty(key)) {
	        return null;
	    }

	    BitmapDrawable bitmap;
	    synchronized (mLruCache) {
	        bitmap = mLruCache.get(key);
	        if (bitmap != null) {
	        	return bitmap;
			}
	    }
	    return null;
	}
}
