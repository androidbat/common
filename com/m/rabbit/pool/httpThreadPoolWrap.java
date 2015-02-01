package com.m.rabbit.pool;

/**
 * @desc wrap ThreadPoolExecutor & ArrayBlockingQueue to handle parallel Runnable working
 * ArrayBlockingQueue perform better than LinkedBlockingQueue, but poolsize is limited.
 * */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.m.rabbit.utils.LLog;

public class httpThreadPoolWrap {

    private static httpThreadPoolWrap instance ;

    private BlockingQueue<Runnable> bq;

    private ThreadPoolExecutor executor = null;

    private httpThreadPoolWrap() {
        bq = new  ArrayBlockingQueue<Runnable>(50);
        executor=new ThreadPoolExecutor(DEFAULT_COREPOOLSIZE, DEFAULT_MAXIMUM_POOLSIZE, DEFAULT_KEEP_ALIVE_TIME, TimeUnit.SECONDS, bq);
    }

    public static httpThreadPoolWrap getThreadPool() {
        if(instance==null){
            synchronized(httpThreadPoolWrap.class){
                if(instance==null){
                    instance=new httpThreadPoolWrap();
                }
            }
        }
        return instance;
    }
    
    public void executeTask(Runnable task) {
        try {
            executor.execute(task);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeTask(Runnable task) {
        try {
            executor.remove(task);
        } catch (Exception e) {
            //LogUtil.printStackTrace(e);
        }
    }

    public void shutdown() {
        executor.shutdown();
        instance = null;
    }

    private static final int DEFAULT_COREPOOLSIZE =2; //4;

    private static final int DEFAULT_MAXIMUM_POOLSIZE =2; //8;

    private static final long DEFAULT_KEEP_ALIVE_TIME = 30;
}