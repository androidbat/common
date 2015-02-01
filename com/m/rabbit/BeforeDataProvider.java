package com.m.rabbit;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.m.rabbit.bean.ImageNetParaed;
import com.m.rabbit.cache.DiskCache;
import com.m.rabbit.cache.bean.BitmapCache;
import com.m.rabbit.cache.engine.behavior.ICacheElement;
import com.m.rabbit.my_cache_task.CacheTask;
import com.m.rabbit.my_cache_task.CacheTask.VisitCacheListener;
import com.m.rabbit.mycache.MyCacheManager;
import com.m.rabbit.net.HttpRequestFactory;
import com.m.rabbit.net.http.IHttpTask;
import com.m.rabbit.net.ihttp.HttpConnectionListener;
import com.m.rabbit.utils.AppUtils;

public class BeforeDataProvider {
	private static BeforeDataProvider mInstance;
	public static String channel;
//	public static int currentPos=0;
    public static BeforeDataProvider getInstance() {
        if (mInstance == null) {
            synchronized (BeforeDataProvider.class) {
                if (mInstance == null) {
                    mInstance = new BeforeDataProvider();
                }
            }
        }
        return mInstance; 
    }
    
    /**
     * @param url
     * @param useCache 是否使用缓存
     * @param listener
     */
    public void getData(String url,boolean useCache, DataListener listener) {
    	
    	if (channel != null) {
    		url += channel;
		}
    	
		if (useCache) {
			ICacheElement element = DiskCache.getStringCache().getCacheElement(url.hashCode());
			DataHolder holder = new DataHolder(url, listener, true);
			if (element != null) {
				if (!element.isExpired()) {
					listener.onDataReady(element.getVal());
				} else {
					holder.setTempData(element.getVal());
					getDataFromNet(url, holder);
				}

			} else {
				holder.setTempData(null);
				getDataFromNet(url, holder);
			}
		} else {
			DataHolder holder = new DataHolder(url, listener,false);
			getDataFromNet(url, holder);
		}
    }
    
    private void getDataFromNet(final String url, DataHolder holder) {
        HttpRequestFactory.getString(url, holder);
    }
    
    public void getImage(final String url,Bitmap loadingBitmap,final ImageView imageView){
    	if (imageView != null && loadingBitmap != null) {
    		imageView.setImageBitmap(loadingBitmap);
		}
    	getImage(url,imageView,2,true,0,0);
    }
    
    public void getImage(final String url,int resId,final ImageView imageView){
    	if (imageView != null && resId != 0) {
    		imageView.setImageResource(resId);
		}
    	getImage(url,imageView,2,true,0,0);
    }
    
	/**
	 * 对外接口，获取图片
	 * @param url
	 * @param imageView
	 * @param whichPage
	 * @param useCache
	 */
	public void getImage(final String url,final ImageView imageView,final int whichPage,boolean useCache,int position,int width){
		
		
		if (url == null) {
			return ;
		}
		
		ImageNetParaed imgpara=new ImageNetParaed();
		imgpara.imageView=imageView;
		imgpara.url=url;
		imgpara.whichPage=whichPage;
		imgpara.useCache=useCache;
		imgpara.position=position;
		imgpara.width = width;
		
		if(imgpara.useCache){
			getImageFromCache(imgpara);
		}else{
			//联网
			getImageFromNet(imgpara);
		}
	}
	
	private void refreshImageView(final ImageNetParaed imgpara,BitmapCache bitmapTool){
		if (bitmapTool!=null &&  imgpara.imageView != null && imgpara.imageView.getTag()!=null) {
			MyTag mytag=(MyTag)imgpara.imageView.getTag();
			if(mytag!=null && mytag.url.equals(imgpara.url) && mytag.position==imgpara.position){
				ImageViewBean ivb=new ImageViewBean();
				ivb.imageview=imgpara.imageView;
//				ivb.bmp=bitmapTool.getBitmap();
				ivb.bmp=bitmapTool.getBitmap(imgpara.width,0);
				Message msg=Message.obtain();
				msg.obj=ivb;
				msg.what=REFRESH_IMAGEVIEW;
				mHandler.sendMessage(msg);
			}else{
			}
			
		}
	}
	/**
	 * 从网络上取图上
	 * @param url
	 * @param imageView
	 * @param whichPage
	 * @param useCache
	 */
	private void getImageFromNet(final ImageNetParaed imgpara){
		setTag(imgpara);
		HttpConnectionListener listener = new HttpConnectionListener(){
			@Override
			public void downloadEnd(IHttpTask http, Object data) {
				if(data==null || http==null || !http.getRequestUrl().equals(imgpara.url)){
					return;
				}
				ByteArrayOutputStream out = (ByteArrayOutputStream)data;
//				Bitmap b=BitmapFactory.decodeByteArray(out.toByteArray(), 0,out.toByteArray().length);
				BitmapCache bitmapTool = new BitmapCache(out);
				if(imgpara.useCache){
					try {
						if(imgpara.whichPage == 1){
							DiskCache.getFirstPageImageCache().put(imgpara.url.hashCode(), bitmapTool);
						}else{
							DiskCache.getOtherPageImageCache().put(imgpara.url.hashCode(), bitmapTool);
						}
					} catch (Exception e) {
					}
				}
				refreshImageView(imgpara,bitmapTool);
//				refreshImageView(imageView,url,position);
				
			}               
		};
		HttpRequestFactory.getBitmap(imgpara.url, listener);
		
	}
	private void setTag(final ImageNetParaed imgpara){
//		imgpara.imageView.setTag(imgpara.url);
		MyTag mytag=new MyTag();
    	mytag.url=imgpara.url;
    	mytag.position=imgpara.position;
    	imgpara.imageView.setTag(mytag);
	}
	/**
	 * 从缓存中取图片
	 * @param url
	 * @param imageView
	 * @param whichPage
	 * @return 成功为true,失败为false
	 */
	private void getImageFromCache(final ImageNetParaed imgpara){
    	setTag(imgpara);
		MyCacheManager.getImgFromCache(imgpara.url, imgpara.whichPage, new VisitCacheListener() {
			
			@Override
			public void OnResult(CacheTask task, Object data) {
				if(task!=null && task.getRequestUrl()!=null && task.getRequestUrl().equals(imgpara.url) && data!=null){
					//从缓存获取成功
					BitmapCache bitmapTool = (BitmapCache)data;
					refreshImageView(imgpara,bitmapTool);
					if(task.isExpired()){
						//如果过期
						sendNetMessage(REQUEST_IMG_FROM_NET,imgpara);
					}
				}else{
					//从缓存获取失败
					sendNetMessage(REQUEST_IMG_FROM_NET,imgpara);
				}
			}
		});
	}

	private void sendNetMessage(int what,Object obj){
		Message msg=Message.obtain();
		msg.what=what;
		msg.obj=obj;
		mHandler.sendMessage(msg);
	}
	public static final int REFRESH_IMAGEVIEW=1;
	public static final int REFRESH_VIEW=2;
	public static final int REQUEST_IMG_FROM_NET=3;
	
	Handler mHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case REFRESH_IMAGEVIEW:
				if(msg.obj!=null){
					ImageViewBean ivb=(ImageViewBean)msg.obj;
					if(ivb!=null && ivb.bmp!=null){
						ivb.imageview.setImageBitmap(ivb.bmp);
					}
					
				}
				break;
			case REQUEST_IMG_FROM_NET:
				if(msg!=null && msg.obj!=null){
					ImageNetParaed para=(ImageNetParaed)msg.obj;
					if(para!=null){
						//联网
						getImageFromNet(para);
					}
				}
				break;
			}
		}
		
	};
	
	public static class MyTag {
		public String  url;
		public int position;
	}
	public class ImageViewBean {
		public ImageView imageview;
		public Bitmap bmp;
	}
	
}
