package com.m.rabbit.my_cache_task;


import com.m.rabbit.net.ihttp.HttpHandlerStateListener;
import com.m.rabbit.task.IMyTask;

public abstract class CacheTask implements IMyTask{
	public static final int SUCCESS=1;
	public static final int FAIL=-1;
	protected HttpHandlerStateListener mHttpHandlerStateListener;
    private VisitCacheListener mVisitCacheListener;
    protected String url=""; 
    protected int whichPage;
    protected boolean isExpired=false;
	public CacheTask(){
	}
	@Override
	public Object doSomeThing() {
		return getCacheData(url,whichPage);
	}

	@Override
	public void onDone(Object result) {
		mVisitCacheListener.OnResult(this, result);
	}
	protected abstract  Object getCacheData(String url,int whichPage);  
	
	public void setRequestUrl(String requestUrl)
    {
        if(requestUrl != null)
        {
            try
            {
                url = requestUrl.trim();
                url = url.replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "");
            }
            catch(Exception ex)
            {
            }
        }
    }
    
	
    public int getWhichPage() {
		return whichPage;
	}
	public void setWhichPage(int whichPage) {
		this.whichPage = whichPage;
	}
	public String getRequestUrl(){
        return url;
    }
    
    public VisitCacheListener getmVisitCacheListener() {
		return mVisitCacheListener;
	}
	public void setmVisitCacheListener(VisitCacheListener mVisitCacheListener) {
		this.mVisitCacheListener = mVisitCacheListener;
	}

	public boolean isExpired() {
		return isExpired;
	}
	public void setExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}

	public interface VisitCacheListener {
        public void OnResult(CacheTask task, Object data);
    }
}
