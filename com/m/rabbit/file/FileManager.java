package com.m.rabbit.file;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.m.rabbit.file.ifile.OnFileOperateListener;
import com.m.rabbit.filetask.CopyFileTask;
import com.m.rabbit.filetask.DelFileTask;
import com.m.rabbit.filetask.GetFileTask;
import com.m.rabbit.filetask.ImageFileTask;
import com.m.rabbit.task.MyAsyncTaskHandler;

public class FileManager {
	public static final String TAG_FILE = "file_task";

	/**
	 * 删除一个文件，为了保险期间，暂时不让删除目录
	 * 
	 * @param filePath
	 *            要删除的文件路径
	 * @param mOnFileOperateListener
	 */
	public static final void delFile(final String filePath,
			OnFileOperateListener mOnFileOperateListener) {
		DelFileTask task = new DelFileTask(filePath);
		task.setmOnFileOperateListener(mOnFileOperateListener);
		MyAsyncTaskHandler.execute(TAG_FILE, task);
	}

	/**
	 * 把文件从一个地方复制到另一个地
	 * 
	 * @param sourcePath
	 *            源文件路径
	 * @param targetPath
	 *            目标文件路径
	 * @param mOnFileOperateListener
	 */
	public static final void copyFile(final String sourcePath,
			final String targetPath, boolean delSource,
			OnFileOperateListener mOnFileOperateListener) {
		CopyFileTask task = new CopyFileTask(sourcePath, targetPath,delSource);
		task.setmOnFileOperateListener(mOnFileOperateListener);
		MyAsyncTaskHandler.execute(TAG_FILE, task);
	}
	
	/**
	 * 把文件从一个地方复制到另一个地
	 * 
	 * @param sourcePath
	 *            源文件路径
	 * @param targetPath
	 *            目标文件路径
	 * @param mOnFileOperateListener
	 */
	public static final void copyImgFile(final String sourcePath,
			final String targetPath, boolean delSource,boolean encrypt,
			OnFileOperateListener mOnFileOperateListener) {
		ImageFileTask task = new ImageFileTask(sourcePath, targetPath,delSource,encrypt);
		task.setmOnFileOperateListener(mOnFileOperateListener);
		MyAsyncTaskHandler.execute(TAG_FILE, task);
	}
	
	public static final List<InputStream> getInputStreamList(final String sourcePath,
			OnFileOperateListener mOnFileOperateListener) {
		List<InputStream> list=new ArrayList<InputStream>();
		GetFileTask task = new GetFileTask(sourcePath, list);
		task.setmOnFileOperateListener(mOnFileOperateListener);
		MyAsyncTaskHandler.execute(TAG_FILE, task);
		return null;
	}

	/**
	 * 给文件重命名
	 * 
	 * @param oldName
	 * @param newName
	 */
	public static final boolean reNameFile(String oldName, String newName) {
		boolean b = false;
		File oldFile = new File(oldName);
		if (oldFile.exists()) {
			File newFile = new File(newName);
			b = oldFile.renameTo(newFile);
		}
		return b;
	}
}
