package com.m.rabbit.download;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.m.rabbit.download.itask.ITask;
import com.m.rabbit.download.itask.ITaskInfo;
import com.m.rabbit.download.itask.ITaskStateChangeListener;
import com.m.rabbit.download.task.TaskState;
import com.m.rabbit.pool.ThreadPoolManager;
import com.m.rabbit.upload.bean.UpLoadTaskInfo;
import com.m.rabbit.utils.LLog;


public class TaskManager {
	public final static int TASK_ADD_ISEXIST = -1;
	public final static int TASK_ADD_CANCELING=-2;
    public final static int TASK_ADD_QUEUE_FULL = -3;
    public final static int TASK_START_SUCCESS=0;
    public final static int TASK_START_CANCELING=-4;
    public final static int TASK_START_FINISHED=-5;
    public final static int TASK_START_UNKONW_FAIL=-6;
    public final static int TASK_PARAMETER_NULL=-7;
    
    /**
     * 最大任务数据 
     */
    private int maxQueue = 50;
	/**
	 * 下载标记
	 */
	public static final String DOWNLOAD_TAG="download";
	/**
	 * 上传标记
	 */
	public static final String UPLOAD_TAG="upload";
	/**
	 * 保存不同类任务的map
	 */
	private static HashMap<String,TaskManager> mTaskManagerMap=new HashMap<String,TaskManager>();
	
	private HashMap<String,ITask> mTaskMap=new HashMap<String,ITask>();
	
	/**
	 * 单个任务对列
	 */
	private ArrayList<ITask> mTaskQueue=new ArrayList<ITask>();
	
	/**
	 * 创建Manager对象锁
	 */
	private static Object mManagerLock=new Object();
	
	/**
	 * 创建Task对象锁
	 */
	private static Object mTaskLock=new Object();
	
	/**
	 * 单例模式，私有化构造函数
	 */
	private TaskManager(){
	}
	
	/**
	 * 单例函数
	 * @param tag 不同任务的标记，上传为UPLOAD_TAG，下载为DOWNLOAD_TAG
	 * @return
	 */
	public static TaskManager getInstance(String tag){
		if(mTaskManagerMap.get(tag)==null){
			synchronized (mManagerLock) {
				if(mTaskManagerMap.get(tag)==null){
					mTaskManagerMap.put(tag, new TaskManager());
				}
			}
		}
		return mTaskManagerMap.get(tag);
	}
	
	public void upLoad(String pathName,String fromUserId){
//		pathName="/storage/emulated/0/tmp_pic_2455.jpg";
		if (pathName == null) {
			return;
		}
		System.out.println("pathName="+pathName);
		UpLoadTaskInfo ut =new UpLoadTaskInfo(pathName);
		ut.setDesp("聊天图片");
		ut.setTitle("上传");
		ut.setType("img");
		ut.setToUserId(fromUserId);
		
		TaskManager.getInstance(TaskManager.UPLOAD_TAG).addTask(ut, new ITaskStateChangeListener() {
			@Override
			public void onProgressUpdate(long l) {
				System.out.println("upLoad progress--------->"+l);
			}
			
			@Override
			public void OnTaskStateChanged(int taskState) {
				System.out.println("upLoad state------->"+taskState);
			}
		});
//		imgTask.task = TaskManager.getInstance(TaskManager.UPLOAD_TAG).getTask(pathName);
		TaskManager.getInstance(TaskManager.UPLOAD_TAG).startTask(pathName);
	}
	
	/**
	 * 
	 * @param taskInfo
	 * @return
	 */
    public int addTask(ITaskInfo taskInfo,ITaskStateChangeListener taskStateChangeListener){
    	if(taskInfo==null){
    		return TASK_PARAMETER_NULL;
    	}
    	synchronized (mTaskLock) {
            if (mTaskMap.containsKey(taskInfo.getKey())) {
                ITask task=mTaskMap.get(taskInfo.getKey());
                if(task.getTaskState()==TaskState.TASK_STATE_CANCELING){
                    return TASK_ADD_CANCELING;
                }else{
                    return TASK_ADD_ISEXIST;
                }
            } else if (mTaskMap.size()==maxQueue) {
                return TASK_ADD_QUEUE_FULL;
            }
            Class<?> c = null;
            try {
                c = Class.forName(taskInfo.getTaskClassName());
                ITask task = (ITask) c.newInstance();
                task.setTaskInfo(taskInfo);
//                task.setProgressListener(taskProgressListener);
                task.setTaskStateListener(taskStateChangeListener);
                mTaskMap.put(taskInfo.getKey(), task);
            } catch (Exception e) {
            	e.printStackTrace();
            }
            return mTaskMap.size();
        }
    }
    
    /**
     * 删除一个任务
     * @param key
     */
    public void dellTask(String key){
        synchronized (mTaskLock){
            if(mTaskMap.get(key)!=null){
                mTaskMap.get(key).cancelTask();
            }
            mTaskMap.remove(key);
        }
    }
    
    /**
     * 删除所有任务,这意为着已经下载的文件将会被删除
     */
    public void dellAllTask(){
    	synchronized (mTaskLock){
    		Set<String> keys=mTaskMap.keySet();
        	for (Iterator<String> key = keys.iterator(); key.hasNext();) {
    			String url = (String) key.next();
    			dellTask(url);
    		}
    	}
    }
    
    /**
     * 开始一个任务
     * @author mingrenhan
     * 2012-6-25
     * @param key url
     * @return TASK_START_SUCCESS是开始成功，其它情况为开始失败
     */
    public int startTask(String key){
    	if(key!=null && !key.trim().equals("")){
    		ITask task=mTaskMap.get(key);
    		LLog.d("key",key);
    		if(task!=null){
    			synchronized(task.getTaskLock()){
    			    if(task.getTaskState()==TaskState.TASK_STATE_PREPAREED 
    			            || task.getTaskState()==TaskState.TASK_STATE_STOPPED      
    			            || task.getTaskState()==TaskState.TASK_STATE_EXCEPTION){
    			        ThreadPoolManager.getExecutor(DOWNLOAD_TAG, 100, 100).execute(task);
    			        return TASK_START_SUCCESS;
    			    }else if(task.getTaskState()==TaskState.TASK_STATE_RUNNING){
    			        return TASK_START_SUCCESS;
    			    }else if(task.getTaskState()==TaskState.TASK_STATE_STOPPING){
                        task.cancelStop();
                        return TASK_START_SUCCESS;
                    }else if(task.getTaskState()==TaskState.TASK_STATE_CANCELING){
    			        return TASK_START_CANCELING;
    			    }else if(task.getTaskState()==TaskState.TASK_STATE_FINISHED){
                        return TASK_START_FINISHED;
                    }
        			return TASK_START_UNKONW_FAIL;
    			}
    		}else{
    			return TASK_PARAMETER_NULL;
    		}
    	}else{
    		return TASK_PARAMETER_NULL;
    	}
    }
    
    /**
     * 开始所有任务
     */
    public void startAllTask(){
    	Set<String> keys=mTaskMap.keySet();
    	for (Iterator<String> key = keys.iterator(); key.hasNext();) {
			String url = (String) key.next();
			startTask(url);
		}
    }
    
    /**
     * 停止一个任务
     * @param key
     */
    public void stopTask(String key){
    	if(mTaskMap.get(key)!=null){
    		mTaskMap.get(key).stop();
    	}
    }
    
    /**
     * 停止所有任务
     */
    public void stopAllTask(){
    	Set<String> keys=mTaskMap.keySet();
    	for (Iterator<String> key = keys.iterator(); key.hasNext();) {
			String url = (String) key.next();
			stopTask(url);
		}
    }
    
    /**
     * 根据URL获得它对应的task
     * @param url
     * @return
     */
    public ITask getTask(String url){
    	if(url!=null){
    		ITask task=mTaskMap.get(url);
    		return task;
    	}
    	return null;
    }
    
    /**
     * 获得task个数
     * @return
     */
    public int getTaskCount(){
    	return mTaskMap.size();
    }
}
