package com.m.rabbit.httpprovider;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.os.SystemClock;

import com.m.rabbit.DataListener;
import com.m.rabbit.bean.ImgParas;
import com.m.rabbit.cache.CacheAccess;
import com.m.rabbit.cache.DiskCache;
import com.m.rabbit.cache.bean.BitmapCache;
import com.m.rabbit.cache.engine.behavior.ICacheElement;
import com.m.rabbit.net.http.HttpTask;
import com.m.rabbit.task.MeAsyncTask;
import com.m.rabbit.utils.LLog;

public class BitmapTask extends MeAsyncTask<Object, Void, Bitmap> {
	public Object data;
	private HttpTask httpTask;
	private DataListener listener;
	private ImgParas imgParas;

	public BitmapTask(ImgParas imgParas,DataListener listener) {
		this.listener = listener;
		this.imgParas = imgParas;
	}

	/**
	 * Background processing.
	 */
	@Override
	protected Bitmap doInBackground(Object... params) {
		LLog.d("bitmap", " doInBackground ");
		String url = imgParas.url;
		Bitmap bm = null;
		
		if (!isCancelled() && !exitTaskEarly && imgParas.useCache) {
			bm = getDiskCache(url);
			LLog.d("bitmap", " getDiskCache "+bm);
		}
		
		if (bm == null) {
			if (!isCancelled() && !exitTaskEarly) {
				bm = getHttpImg(url);
				LLog.d("bitmap", " getHttpImg "+bm);
			}
		}

		return bm;

	}
	

	/**
	 * Once the image is processed, associates it to the imageView
	 */
	@Override
	protected void onPostExecute(Bitmap value) {
		// if cancel was called on this task or the "exit early" flag is set
		// then we're done
		LLog.d("bitmap", "isCancelled() " +isCancelled() +" exitTaskEarly"+ exitTaskEarly);
		if (isCancelled() || exitTaskEarly) {
			LLog.d("bitmap", "isCancelled() " +exitTaskEarly);
			return;
		}
		
		if (listener != null) {
			listener.onDataReady(value);
		}

	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
	}

	private Bitmap getHttpImg(String url) {
		long start = SystemClock.uptimeMillis();
		httpTask = new HttpTask();
		ByteArrayOutputStream bao = httpTask.getHttpData(url, null);
		
		if (bao != null) {
			BitmapCache bitmapTool = new BitmapCache(bao);
			try {
					if (imgParas.useCache) {
						DiskCache.getOtherPageImageCache().put(url.hashCode(), bitmapTool);
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return bitmapTool.getBitmap(imgParas.width,imgParas.height);
		}
		return null;
	}

	private Bitmap getDiskCache(String url) {
		try {
			long start = SystemClock.uptimeMillis();
			CacheAccess cacheAccess;
			cacheAccess = DiskCache.getOtherPageImageCache();
			
			if (cacheAccess != null) {
				ICacheElement ce = cacheAccess.getCacheElement(url.hashCode());
				if (ce != null && !ce.isExpired()) {
					BitmapCache bitmapTool = (BitmapCache) ce.getVal();
					LLog.d("bitmap"," getCacheElement " + (SystemClock.uptimeMillis() - start));
					if (bitmapTool != null) {
						return bitmapTool.getBitmap();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

    @Override
    public void cancel() {
    	super.cancel();
    }
}