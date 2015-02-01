package com.m.rabbit.mycache;

import com.m.rabbit.my_cache_task.ImgCacheTask;
import com.m.rabbit.my_cache_task.CacheTask.VisitCacheListener;
import com.m.rabbit.task.MyAsyncTaskHandler;

public class MyCacheManager {
	public static final String TAG_CACHE = "cache_task";

	
	public static final void getImgFromCache(final String url,int whichPage,
			VisitCacheListener mVisitCacheListener) {
		ImgCacheTask task = new ImgCacheTask(url,whichPage);
		task.setmVisitCacheListener(mVisitCacheListener);
		MyAsyncTaskHandler.execute(TAG_CACHE, task);
	}

}
