package com.m.rabbit.task;

import java.util.concurrent.ThreadPoolExecutor;

import com.m.rabbit.pool.ThreadPoolManager;

public class MyAsyncTaskHandler extends MyAsyncTask<Object, Integer, Object>{
    private IMyTask mIMyTask;
    private boolean bCancelled = false;
    public MyAsyncTaskHandler(String tag,IMyTask iTask) {
        super(tag);
        mIMyTask=iTask;
    }

    @Override
    protected Object doInBackground(Object... params) {
        if(!bCancelled && mIMyTask!=null){
            return mIMyTask.doSomeThing();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        if (!bCancelled && mIMyTask!=null) {
        	mIMyTask.onDone(result);
		}
    }
    
    @Override
    protected void onCancelled() {
        bCancelled = true;
        super.onCancelled();
    }
    
    public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor){
        mThreadPoolExecutor=threadPoolExecutor;
    }
    
    public static final void execute(String tag,IMyTask iMyTask){
        MyAsyncTaskHandler myAsyncTaskHandler=new MyAsyncTaskHandler(tag,iMyTask);
        myAsyncTaskHandler.setThreadPoolExecutor(ThreadPoolManager.getExecutor(tag));
        myAsyncTaskHandler.execute();
    }
}
