package com.m.rabbit.filetask;

import com.m.rabbit.file.ifile.OnFileOperateListener;
import com.m.rabbit.task.IMyTask;

public abstract class FileTask implements IMyTask{
	public static final int SUCCESS=1;
	public static final int FAIL=-1;
	protected OnFileOperateListener mOnFileOperateListener;
	protected String sourcePath;
	protected String targetPath;
	public FileTask(){
	}
	@Override
	public Object doSomeThing() {
		
		return fileOperate();
	}

	@Override
	public void onDone(Object result) {
		if(mOnFileOperateListener!=null){
			mOnFileOperateListener.OnResult(sourcePath, result);
		}
	}
	
	public String getSourcePath() {
		return sourcePath;
	}
	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}
	public String getTargetPath() {
		return targetPath;
	}
	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}
	protected abstract Object fileOperate() ;
	public OnFileOperateListener getmOnFileOperateListener() {
		return mOnFileOperateListener;
	}
	public void setmOnFileOperateListener(
			OnFileOperateListener mOnFileOperateListener) {
		this.mOnFileOperateListener = mOnFileOperateListener;
	}

}
