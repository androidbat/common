package com.m.rabbit.download.task;

import com.m.rabbit.download.itask.ITask;
import com.m.rabbit.download.itask.ITaskStateChangeListener;

public abstract class Task implements ITask{
	public static final int CACHE_SIZE = 102400;
    public static final int PROGRESS_TIME = 1000 * 2;
    public static final int READ_TIME_OUT = 15*1000;
    public static final int CONNECT_TIME_OUT = 30*1000;
    protected int mTaskState;
    protected Object mTaskLock = new Object();
    protected ITaskStateChangeListener mTaskStateChangeListener;
    public Task(){
        setTaskState(TaskState.TASK_STATE_PREPAREED);
    }
    @Override
    public final int getTaskState() {
        return mTaskState;
    }

    @Override
    public final Object getTaskLock() {
        return mTaskLock;
    }

    @Override
    public final void stop() {
        synchronized (mTaskLock) {
            if(mTaskState==TaskState.TASK_STATE_PREPAREED 
                    || mTaskState==TaskState.TASK_STATE_EXCEPTION){
                setTaskState(TaskState.TASK_STATE_STOPPED);
            }else if(mTaskState==TaskState.TASK_STATE_RUNNING){
                setTaskState(TaskState.TASK_STATE_STOPPING);
            }else if(mTaskState==TaskState.TASK_STATE_CANCELING){
                //不需变，因为要删除整个任务，包括已下载文件
            }else if(mTaskState==TaskState.TASK_STATE_FINISHED){
               //不需变，因为任务已经完成
            }else if(mTaskState==TaskState.TASK_STATE_STOPPED
                    || mTaskState==TaskState.TASK_STATE_STOPPING){
              //不需变，因为任务已经处于这种状态
            }
        }
    }

    @Override
    public void cancelStop() {
        synchronized (mTaskLock) {
            /**
             * 只有对正在运行的task执行了stop()动作才会处于stopping状态,把以取消时将其恢复为running状态 
             */
            if(mTaskState==TaskState.TASK_STATE_STOPPING){
                setTaskState(TaskState.TASK_STATE_RUNNING);
            }
        }
    }

    /**
     * 删除任务时要连同文件一起删除
     */
    @Override
    public void cancelTask() {
        if(mTaskState!=TaskState.TASK_STATE_CANCELING){
            setTaskState(TaskState.TASK_STATE_CANCELING);
        }
    }

    @Override
    public void setTaskStateListener(ITaskStateChangeListener taskStateChangeListener) {
        this.mTaskStateChangeListener=taskStateChangeListener;
    }
    
    protected final void setTaskState(int taskState){
        synchronized (mTaskLock) {
            this.mTaskState=taskState;
            if(mTaskStateChangeListener!=null){
            	mTaskStateChangeListener.OnTaskStateChanged(this.mTaskState);
            }
        }
    }
}
