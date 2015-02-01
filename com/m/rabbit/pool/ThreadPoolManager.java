package com.m.rabbit.pool;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolManager
{
    private static final int CORE_POOL_SIZE = 2;
    private static final int MAXIMUM_POOL_SIZE = 2;
    private static final int KEEP_ALIVE = 10;
    public static final String TAG_DBACCESS = "DBAccessTask";
    public static final String TAG_STRING = "string";
    public static final String TAG_IMAGE = "img";
    
    //11
    
    private static final HashMap<String, WorkObject> hmWork = new HashMap<String, WorkObject>();
    
    public synchronized static ThreadPoolExecutor getExecutor(String tag)
    {
        WorkObject work = hmWork.get(tag);
        if(work == null || work.sExecutor == null)
        {
            work = new WorkObject(tag);
            hmWork.put(tag, work);
        }
        return work.sExecutor;
    }
    
    public synchronized static ThreadPoolExecutor getExecutor(String tag,int minSize,int maxSize)
    {
        WorkObject work = hmWork.get(tag);
        if(work == null || work.sExecutor == null)
        {
            work = new WorkObject(tag,minSize,maxSize);
            hmWork.put(tag, work);
        }
        return work.sExecutor;
    }
    
    public synchronized static ThreadPoolExecutor getExecutor(String tag,LinkedBlockingQueue<Runnable> linkedBlockingQueue){
        WorkObject work = hmWork.get(tag);
        if(work == null || work.sExecutor == null)
        {
            work = new WorkObject(tag,linkedBlockingQueue);
            hmWork.put(tag, work);
        }
        return work.sExecutor;
    }
    
    public synchronized static void clearQueue(final String tag)
    {
        WorkObject work = hmWork.get(tag);
        if(work != null && work.sWorkQueue != null)
        {
            work.sWorkQueue.clear();
        }
    }
    
    private static class WorkObject
    {
        LinkedBlockingQueue<Runnable> sWorkQueue;
        ThreadFactory sThreadFactory;
        ThreadPoolExecutor sExecutor;
        
        public WorkObject(final String tag)
        {
            sWorkQueue = new LinkedBlockingQueue<Runnable>();
            sThreadFactory = new ThreadFactory() {
                private final AtomicInteger mCount = new AtomicInteger(1);
                public Thread newThread(Runnable r)
                {
                    return new Thread(r, tag + " #" + mCount.getAndIncrement());
                }
            };
            sExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, sWorkQueue, sThreadFactory);
        }
        
        public WorkObject(final String tag,final int minSize,final int maxSize)
        {
            sWorkQueue = new LinkedBlockingQueue<Runnable>();
            sThreadFactory = new ThreadFactory() {
                private final AtomicInteger mCount = new AtomicInteger(1);
                public Thread newThread(Runnable r)
                {
                    return new Thread(r, tag + " #" + mCount.getAndIncrement());
                }
            };
            sExecutor = new ThreadPoolExecutor(minSize, maxSize, KEEP_ALIVE, TimeUnit.SECONDS, sWorkQueue, sThreadFactory);
        }
        
        public WorkObject(final String tag,final LinkedBlockingQueue<Runnable> linkedBlockingQueue)
        {
            if(linkedBlockingQueue!=null){
                sWorkQueue=linkedBlockingQueue;
            }else{
                sWorkQueue = new LinkedBlockingQueue<Runnable>();
            }           
            sThreadFactory = new ThreadFactory() {
                private final AtomicInteger mCount = new AtomicInteger(1);
                public Thread newThread(Runnable r)
                {
                    return new Thread(r, tag + " #" + mCount.getAndIncrement());
                }
            };
            sExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, sWorkQueue, sThreadFactory);
        }
    }
}
