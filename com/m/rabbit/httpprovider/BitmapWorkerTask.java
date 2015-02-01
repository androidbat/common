package com.m.rabbit.httpprovider;

import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.SystemClock;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.m.rabbit.bean.ImgParas;
import com.m.rabbit.cache.CacheAccess;
import com.m.rabbit.cache.DiskCache;
import com.m.rabbit.cache.bean.BitmapCache;
import com.m.rabbit.cache.engine.behavior.ICacheElement;
import com.m.rabbit.cache.memory.MemoryBitmapCache;
import com.m.rabbit.net.http.HttpTask;
import com.m.rabbit.net.http.HttpTask.HttpEventHandler;
import com.m.rabbit.task.MeAsyncTask;
import com.m.rabbit.utils.BitmapDecodeUtils;
import com.m.rabbit.utils.BitmapUtils;
import com.m.rabbit.utils.LLog;
import com.m.rabbit.utils.Utils;
import com.m.rabbit.utils.VUtils;

public class BitmapWorkerTask extends MeAsyncTask<Object, Integer, Bitmap> {
	
	
	public Object data;
	public final WeakReference<ImageView> imageViewReference;
	public ImgParas imgParas;
	private boolean isCancel;
	private HttpTask httpTask;

	public BitmapWorkerTask(ImageView imageView, ImgParas imgParas) {
		imageViewReference = new WeakReference<ImageView>(imageView);
		this.imgParas = imgParas;
	}

	/**
	 * Background processing.
	 */
	@Override
	protected Bitmap doInBackground(Object... params) {
		LLog.d("bitmap", this + " doInBackground "+imgParas.url);
		String url = imgParas.url;
		Bitmap bm = null;

		if (imgParas.useCache) {
			if (!isCancelled() && getAttachedImageView() != null && !isCancel && !exitTaskEarly) {
				bm = getDiskCache(url);
				LLog.d("bitmap", this + " cache bm "+bm);
			}
		}
		
		LLog.d("bitmap", this + " bm "+bm);
		
		if (bm == null) {
			if (!isCancelled() && getAttachedImageView() != null && !isCancel && !exitTaskEarly) {
				bm = getHttpImg(url);
			}
		}

		if (bm != null) {
			if (imgParas.round) {
				bm = BitmapUtils.toRoundCorner(bm,imgParas.width,imgParas.height,10);
			}
			MemoryBitmapCache.getInstance().addBitmapToMemoryCache(url, bm);
			return bm;
		}

		return null;

	}

	/**
	 * Once the image is processed, associates it to the imageView
	 */
	@Override
	protected void onPostExecute(Bitmap value) {
		// if cancel was called on this task or the "exit early" flag is set
		// then we're done
		LLog.d("bitmap", this + " isCancelled() " +isCancelled() +" exitTaskEarly"+ exitTaskEarly);
		if (isCancelled() || exitTaskEarly) {
			LLog.d("bitmap", this + "isCancelled() " +exitTaskEarly);
			return;
		}

		final ImageView imageView = getAttachedImageView();
		LLog.d("bitmap", this +" "+ (imageView == null ? " imageView = null  " : ""+ (value == null ? " value=null" : "")));
		if (value != null && imageView != null) {
			if (imgParas.isScaleImageView) {
				VUtils.setLayoutParamFromBm(imageView, imgParas.width, value);
			}
			setImageDrawable(imageView, new BitmapDrawable(imgParas.mContext.getResources(), value));
		}
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		isCancel = true;
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		int i = values[0];
		LLog.d("progress"," * " + i);
	}

	private Bitmap getHttpImg(String url) {
		long start = SystemClock.uptimeMillis();
		httpTask = new HttpTask();
		ByteArrayOutputStream bao = httpTask.getHttpData(url, null);
		LLog.d("bitmap",this + " HttpImageTask " + (SystemClock.uptimeMillis() - start));
		if (bao != null) {
			BitmapCache bitmapTool = new BitmapCache(bao);
			Bitmap bm = bitmapTool.getBitmap(imgParas.width, imgParas.height);
			
			if (imgParas.useCache) {
				try {
					if (imgParas.whichPage == 1) {
						DiskCache.getFirstPageImageCache().put(url.hashCode(), bitmapTool);
					} else {
						DiskCache.getOtherPageImageCache().put(url.hashCode(), bitmapTool);
					}
				} catch (Exception e) {
					e.printStackTrace();
					LLog.d("bitmap",this + " save cache failed");
				}
			}
			LLog.d("bitmap",this + " HttpImageTask2 " + (SystemClock.uptimeMillis() - start));
			return bm;
		}
		return null;
	}

	private Bitmap getDiskCache(String url) {
		try {
			long start = SystemClock.uptimeMillis();
			CacheAccess cacheAccess;
			if (imgParas.whichPage == 1) {
				cacheAccess = DiskCache.getFirstPageImageCache();
			} else {
				cacheAccess = DiskCache.getOtherPageImageCache();
			}
			
			if (cacheAccess != null && !isCancel) {
				ICacheElement ce = cacheAccess.getCacheElement(url.hashCode());
				if (ce != null && !ce.isExpired() && !isCancel) {
					BitmapCache bitmapTool = (BitmapCache) ce.getVal();
					LLog.d("bitmap",this + " getCacheElement " + (SystemClock.uptimeMillis() - start));
					if (bitmapTool != null) {
						return bitmapTool.getBitmap(imgParas.width, imgParas.height);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Returns the ImageView associated with this task as long as the
	 * ImageView's task still points to this task as well. Returns null
	 * otherwise.
	 */
	private ImageView getAttachedImageView() {
		final ImageView imageView = imageViewReference.get();
		final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

		if (this == bitmapWorkerTask) {
			return imageView;
		}

		return null;
	}
	
    public static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

        public AsyncDrawable(Resources res, Bitmap bitmap, BitmapWorkerTask bitmapWorkerTask) {
            super(res, bitmap);
            bitmapWorkerTaskReference =
                new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }
    
    private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }
    
    public static boolean cancelPotentialWork(Object data, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final Object bitmapData = bitmapWorkerTask.data;
            if (bitmapData == null || !bitmapData.equals(data)) {
                bitmapWorkerTask.cancel(true);
                LLog.d("bitmap"," cancelPotentialWork sucess");
            } else {
                // The same work is already in progress.
                return false;
            }
        }
        return true;
    }
    
    @Override
    public void cancel() {
    	LLog.d(" img cancel ");
    	if (httpTask != null) {
    		httpTask.cancel();
		}
    	super.cancel();
    }
    
	@SuppressLint("NewApi")
	private void setImageDrawable(ImageView imageView, Drawable drawable) {
        if (imgParas.mFadeInBitmap) {
        	if (imageView.getScaleType() == ScaleType.FIT_XY || imageView.getScaleType() == ScaleType.CENTER_CROP) {
        		// Transition drawwable with a transparent drawable and the final draable
        		final TransitionDrawable td =
        				new TransitionDrawable(new Drawable[] {
        						new ColorDrawable(android.R.color.transparent),
        						drawable
        				});
        		// Set background to loading bitmap
        		if (Utils.hasJellyBean()) {
        			imageView.setBackground(new BitmapDrawable(imgParas.mContext.getResources(), imgParas.mLoadingBitmap));
				}else{
					imageView.setBackgroundDrawable(new BitmapDrawable(imgParas.mContext.getResources(), imgParas.mLoadingBitmap));
				}
        		
        		imageView.setImageDrawable(td);
        		td.startTransition(FADE_IN_TIME);
        		return;
			}
        }
        imageView.setImageDrawable(drawable);
    }
    
    public static final int FADE_IN_TIME = 300;
}