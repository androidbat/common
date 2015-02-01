package com.m.rabbit.my_cache_task;

import com.m.rabbit.cache.CacheAccess;
import com.m.rabbit.cache.DiskCache;
import com.m.rabbit.cache.bean.BitmapCache;
import com.m.rabbit.cache.engine.behavior.ICacheElement;

public class ImgCacheTask extends CacheTask{
	public ImgCacheTask(String url,int whichPage){
		this.url=url;
		this.whichPage=whichPage;
	}
	@Override
	protected Object getCacheData(String url,int whichPage) {
		CacheAccess cacheAccess;
		if(whichPage>1){
			cacheAccess=DiskCache.getOtherPageImageCache();
		}else{
			cacheAccess=DiskCache.getFirstPageImageCache();
		}
		if(cacheAccess!=null){
			ICacheElement ce=cacheAccess.getCacheElement(url.hashCode());
			if(ce==null){
				return null;
			}
			if(ce.isExpired()){
				//如果过期了
				this.isExpired=true;
			}else{
				this.isExpired=false;
			}
			BitmapCache bitmapTool = (BitmapCache)ce.getVal();
			return bitmapTool;
		}else{
			return null;
		}
	}

}
