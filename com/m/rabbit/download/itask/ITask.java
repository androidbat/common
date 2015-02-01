package com.m.rabbit.download.itask;

public interface ITask extends Runnable{
    public int getTaskState();
    public Object getTaskLock();
    /**
     * 停止下载任务
     * @author mingrenhan
     * 2012-6-25
     */
	public void stop();
	/**
	 * 正在停止中的可以取消停止
	 * @author mingrenhan
	 * 2012-6-25
	 */
	public void cancelStop();
	/**
	 * 取消下载任务
	 * @author mingrenhan
	 * 2012-6-25
	 */
	public void cancelTask();
	/**
	 * 一个任务task一个taskInfo
	 * @author mingrenhan
	 * 2012-6-25
	 * @param taskInfo
	 */
	public void setTaskInfo(ITaskInfo taskInfo);
	
	public void setTaskStateListener(ITaskStateChangeListener taskStateChangeListener);
}
