package com.m.rabbit.upload;

import com.m.rabbit.download.itask.ITaskInfo;
import com.m.rabbit.download.itask.ITaskStateChangeListener;
import com.m.rabbit.download.task.Task;
import com.m.rabbit.download.task.TaskState;
import com.m.rabbit.upload.bean.UpLoadTaskInfo;
import com.m.rabbit.upload.http.UploadHttpAction;
import com.m.rabbit.utils.LLog;
import com.m.rabbit.utils.NetUtils;
import com.m.rabbit.utils.StringUtils;

public class UploadTask extends Task{
	private UpLoadTaskInfo mUpLoadTaskInfo;
	@Override
	public void setTaskInfo(ITaskInfo taskInfo) {
		if(taskInfo instanceof UpLoadTaskInfo){
			mUpLoadTaskInfo=(UpLoadTaskInfo)taskInfo;
        }
	}

	@Override
	public void run() {
		synchronized (mTaskLock){
			if (mUpLoadTaskInfo == null) {
				LLog.d("444444444444");
    			setTaskState(TaskState.TASK_STATE_EXCEPTION);
                return;
            }
            if(mTaskState==TaskState.TASK_STATE_CANCELING
                    || mTaskState==TaskState.TASK_STATE_STOPPING
                    || mTaskState==TaskState.TASK_STATE_FINISHED
                    || mTaskState==TaskState.TASK_STATE_RUNNING){
                return;
            }else{
                setTaskState(TaskState.TASK_STATE_RUNNING);
            }
        }
		
		try {
            UploadHttpAction httpAction = new UploadHttpAction();
            LLog.d("11111111111111111111111");
            //1 
            if (!mUpLoadTaskInfo.isUpload_head_success()) {  // first upload
                //TO get information from server:videoId userPath uploadPath
            	boolean success=httpAction.addUpload(NetUtils.getNetTypeForUpload(),mUpLoadTaskInfo,isNeedProxy());
            	if(!success){
            		setTaskState(TaskState.TASK_STATE_EXCEPTION);
            		LLog.d("555555555555");
            		return;
            	}
            }
            LLog.d("222222222222222222");
            //2.
            while (mUpLoadTaskInfo.getUploadedPart() < mUpLoadTaskInfo.getUploadTotal()) {
            	LLog.d("2222222222222222220000000000-->"+getTaskState());
                if (httpAction.uploadVideoNextPart(mUpLoadTaskInfo,isNeedProxy())) { //upload part of entity successful
                	LLog.d("222222222222222222aaaaaaaaaa->"+getTaskState());
                	mUpLoadTaskInfo.setUploadedPart(mUpLoadTaskInfo.getUploadedPart()+1);
                	LLog.d("222222222222222222bbbbbbbbbb");
                	float progress=mUpLoadTaskInfo.getUploadedPart()*UpLoadTaskInfo.PART_FILE_SIZE*100/mUpLoadTaskInfo.getFileSize();
                	LLog.d("222222222222222222ccccccccccc");
                	mTaskStateChangeListener.onProgressUpdate((int)progress);
                } else { //failed
                	setTaskState(TaskState.TASK_STATE_EXCEPTION);
                	LLog.d("dddddddddd");
                    return;
                }
            }
            LLog.d("333333333333333");
            //3.
            if (mUpLoadTaskInfo.getUploadedPart() >= mUpLoadTaskInfo.getUploadTotal()) {
            	LLog.d("333333333333333oooooooooo");
                if (httpAction.endUpload(mUpLoadTaskInfo,isNeedProxy())) {
                	LLog.d("33333333333333311111111111");
                	setTaskState(TaskState.TASK_STATE_FINISHED);
                }else{
                	LLog.d("33333333333333322222222222");
                	setTaskState(TaskState.TASK_STATE_EXCEPTION);
                }
                
            }
        } catch (Exception e) {
        	e.printStackTrace();
        	setTaskState(TaskState.TASK_STATE_EXCEPTION);
        	LLog.d("eeeeeeeeeeeeeeeeeee");
        }        
	}
	public static boolean isNeedProxy(){
        return (!NetUtils.isWifi()) && (StringUtils.isNotEmpty(NetUtils.getHostbyWAP()));
    }
}
