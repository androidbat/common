package com.m.rabbit;

import java.util.LinkedList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.m.rabbit.bean.ImgParas;
import com.m.rabbit.cache.memory.MemoryBitmapCache;
import com.m.rabbit.httpprovider.BitmapTask;
import com.m.rabbit.httpprovider.BitmapWorkerTask;
import com.m.rabbit.httpprovider.BitmapWorkerTask.AsyncDrawable;
import com.m.rabbit.httpprovider.StringTask;
import com.m.rabbit.pool.ThreadPoolManager;
import com.m.rabbit.task.IMyTask;
import com.m.rabbit.task.MeAsyncTask;
import com.m.rabbit.task.MeAsyncTask.OnTaskCompletedListener;
import com.m.rabbit.utils.Utils;
import com.m.rabbit.utils.VUtils;
import com.m.rabbit.view.DynamicHeightImageView;

@SuppressWarnings("rawtypes")
public class DataProvider {
	private Context mContext;
	private int reqImgWidth = 1080;
	private int reqImgHeight;
	
	public static String channel;
	
	private static DataProvider instance;
	
	public DataProvider(Context context) {
		mContext = context;
		mIngTasks = new LinkedList<MeAsyncTask>();
	}
	
	public static DataProvider getInstance(){
		if (instance == null) {
			synchronized (DataProvider.class) {
				if (instance == null) {
					instance = new DataProvider(null);
				}
			}
		}
		return instance;
	}
	
	
	public void setReqImgSize(int reqImgWidth,int reqImgHeigh) {
		this.reqImgWidth = reqImgWidth;
		this.reqImgHeight = reqImgHeigh;
	}

	private LinkedList<MeAsyncTask> mIngTasks;

	public MeAsyncTask getData(String url,boolean useCache, DataListener listener) {
		if (channel != null) {
			url += channel;
		}
		StringTask httpStringTask = new StringTask(useCache, listener);
		addTask(httpStringTask);
		httpStringTask.executeOnExecutor(ThreadPoolManager.getExecutor(ThreadPoolManager.TAG_STRING), url);
		return httpStringTask;
	}
	
	public void postData(final String url,boolean useCache, byte[] data,DataListener listener) {
		StringTask httpStringTask = new StringTask(useCache, listener);
		httpStringTask.setData(data);
		addTask(httpStringTask);
		httpStringTask.executeOnExecutor(ThreadPoolManager.getExecutor(ThreadPoolManager.TAG_STRING), url);
	}
	
	public void getImage(ImageView imageView,int resourceId,ImgParas imgParas){
		
		if (mContext == null) {
			mContext = imageView.getContext().getApplicationContext();
		}
		
		Bitmap mLoadingBitmap = MemoryBitmapCache.getInstance().getResource(resourceId, mContext);
		getImage(imageView,mLoadingBitmap,imgParas);
	}
	
	/**
	 * 获取bitmap使用
	 */
	public void getBitmap(ImgParas paras,DataListener listener){
		Bitmap bm = MemoryBitmapCache.getInstance().getBitmapFromMemCache(paras.url);
		if (bm != null) {
			if (listener != null) {
				listener.onDataReady(bm);
			}
		}else{
			BitmapTask task = new BitmapTask(paras,listener);
			task.executeOnExecutor(ThreadPoolManager.getExecutor(ThreadPoolManager.TAG_IMAGE));
		}
	}
	
	public void getImage(ImageView imageView,Bitmap mLoadingBitmap,ImgParas imgParas){
		if (imageView == null) {
			return;
		}
		
		if (imgParas.url == null) {
			imageView.setImageBitmap(mLoadingBitmap);
			return;
		}
		
		
		Bitmap bm = MemoryBitmapCache.getInstance().getBitmapFromMemCache(imgParas.url);
		
		if (imgParas.height == 0 && imgParas.width == 0 ) {
			imgParas.width = reqImgWidth;
			imgParas.height = reqImgHeight;
		}
		
		if (bm != null) {
			if (imgParas.isScaleImageView) {
				VUtils.setLayoutParamFromBm(imageView,imgParas.width, bm);
			}
			imageView.setImageBitmap(bm);
		}else if (BitmapWorkerTask.cancelPotentialWork(imgParas.url, imageView)) {
			imgParas.url = imgParas.url;
			final BitmapWorkerTask task = new BitmapWorkerTask(imageView,imgParas);
			addTask(task);
			
			if (mContext == null) {
				mContext = imageView.getContext().getApplicationContext();
			}
			
			imgParas.mLoadingBitmap = mLoadingBitmap;
			imgParas.mContext = mContext;
			
			final AsyncDrawable asyncDrawable =
					new AsyncDrawable(mContext.getResources(), mLoadingBitmap, task);
			imageView.setImageDrawable(asyncDrawable);
            task.executeOnExecutor(ThreadPoolManager.getExecutor(ThreadPoolManager.TAG_IMAGE));
		}
	}
	
	public void startTask(final IMyTask task){
		MeAsyncTask asyncTask = new MeAsyncTask() {
			@Override
			protected Object doInBackground(Object... params) {
				return task.doSomeThing();
			}
			
			@Override
			protected void onPostExecute(Object result) {
				task.onDone(result);
			}
		};
		addTask(asyncTask);
		asyncTask.execute();
	}
	
	
	public void addTask(MeAsyncTask task){
		if (mIngTasks == null) {
			mIngTasks = new LinkedList<MeAsyncTask>();
		}
		mIngTasks.add(task);
		task.setOnTaskCompletedListener(new OnTaskCompletedListener() {
			@Override
			public void onTaskCompleted(MeAsyncTask task) {
				if (mIngTasks != null) {
					mIngTasks.remove(task);
				}
			}
		});
	}
	
	public void exitTask(){
		if (mIngTasks == null) {
			return;
		}
		
		while (mIngTasks.size() > 0 ) {
			MeAsyncTask task = mIngTasks.getFirst();
			if (task.getStatus() != MeAsyncTask.Status.FINISHED) {
				task.setExitTaskEarly(true);
				task.cancel();
			}
			mIngTasks.remove(task);
		}
		mIngTasks = null;
	}
	
	public void removeTask(MeAsyncTask task){
		if (mIngTasks != null ) {
			mIngTasks.remove(task);
		}
	}
	
	public void resume(){
		if (!pause) {
			return;
		}
		
		pause = false;
	}

	private boolean pause;
	public void pause() {
		pause = true;
	}
	
}
