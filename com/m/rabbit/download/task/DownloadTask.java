package com.m.rabbit.download.task;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;

import com.m.rabbit.download.itask.ITaskInfo;
import com.m.rabbit.utils.LLog;

public class DownloadTask extends Task {
    private DownloadTaskInfo downloadTaskInfo;
    // 尝试重新下载的最大次数  
    private final  int retry_times = 3;
    // 线程暂停10000ms
    private final  int retry_delay_time = 10000;
    // 设置超时时间为20000ms   
    private final int timeout = 20000; 
    /** 保存上次刷新的时间 **/
    private             long    lastLoadTime        = 0;
    /**<i> 定义发送广播的间隔时间,单位为毫秒 </i>**/
    private final       long    updateTime          = 2500;
    
    // 处理下载中，下载完成和下载错误事件的接口
    //    private DownloadListener onDownloadListener;
    // 处理状态栏中进度条的接口
    //    private DownloadNotificationListener onDownloadNotificationListener;
    
    private Boolean     success     = false;        // 下载是否成功
    
    private int progress;
    @Override
    public void run() {
        synchronized (mTaskLock){
            if(mTaskState==TaskState.TASK_STATE_CANCELING
                    || mTaskState==TaskState.TASK_STATE_STOPPING
                    || mTaskState==TaskState.TASK_STATE_FINISHED
                    || mTaskState==TaskState.TASK_STATE_RUNNING){
                return;
            }else{
                setTaskState(TaskState.TASK_STATE_RUNNING);
            }
        }
        if (downloadTaskInfo == null) {
			setTaskState(TaskState.TASK_STATE_EXCEPTION);
            return;
        }
        int retryCount = 0;             //线程重试的次数
        File rsfFile = null;
        do { 
            InputStream inStream = null;                //定义输入流
            RandomAccessFile rFile = null;              //定义断点续传
            File fileSavePath = new File(downloadTaskInfo.getSavePath());     //保存的路径（目录）
            
            // 如果保存路径不存在，则重新创建  
            if (!fileSavePath.exists()){
            	if(!fileSavePath.mkdirs()){
            		LLog.d("task","fileSavePath.mkdirs failed");
            		setTaskState(TaskState.TASK_STATE_EXCEPTION);
					return;
            	}else{
            	}
            }
         // 获取已下载文件的大小
            long startPos =0 ;
            // 获取已下载的文件
            rsfFile = new File(fileSavePath +"/"+ downloadTaskInfo.getTemporaryName());
            if(rsfFile.exists()){
            	startPos=rsfFile.length();
            }else{
            	try {
					if(!rsfFile.createNewFile()){
						setTaskState(TaskState.TASK_STATE_EXCEPTION);
						return;
					}
				} catch (IOException e) {
					setTaskState(TaskState.TASK_STATE_EXCEPTION);
					e.printStackTrace();
					return;
				}
            }
            
            
            URL url;
            try {
                // 初始化HTTP，定义HTTP头
                url = new URL(downloadTaskInfo.getDownUrl());
                //HttpURLConnection http = (HttpURLConnection) url.openConnection();
                HttpURLConnection http = getURLConnection(url);
                
                http.setConnectTimeout(timeout);        //设置连接超时时间为20000ms  
                http.setReadTimeout(timeout);           //设置读取数据超时时间为20000ms  
                if (startPos > 0) {
                    http.setRequestProperty("User-Agent", "NetFox");
                    http.setRequestProperty("Range", "bytes=" + startPos + "-");//设置文件下载开始的位置
                }
                http.setRequestProperty("Connection", "Keep-Alive");
                http.setRequestProperty("Keep-Alive", "300");  
                //链接请求
                http.connect();
                //从HTTP头获取网络文件的大小
                String contentlenth=http.getHeaderField("Content-Length");
                if(contentlenth!=null){
                	downloadTaskInfo.setFileSize(Long.parseLong(contentlenth));
                }
                
//                int contentSize = http.getContentLength();
//                if (contentSize <= 0) {
//                	setTaskState(TaskState.TASK_STATE_EXCEPTION);
//                    return;
//                }
                downloadTaskInfo.setFileSize(downloadTaskInfo.getFileSize() + startPos);
                
                inStream = http.getInputStream();
                rFile = new RandomAccessFile(rsfFile, "rw");    
                byte[] buffer = new byte[1024];
                int offSet = 0;
                while ((offSet = inStream.read(buffer)) != -1) {
                    if(checkTaskState(rsfFile)){
                        return;
                    }
                    rFile.write(buffer, 0, offSet);                     //写入文件
                    startPos +=  offSet;                                //更新已下载文件的大小
                    downloadTaskInfo.setDownloadedSize(startPos);
                    int progress=(int) ((((float)startPos)/downloadTaskInfo.getFileSize()) * 100);
                    if (this.progress != progress) {
                    	mTaskStateChangeListener.onProgressUpdate(progress);
                    	this.progress = progress;
					}
                }
                // 如果下载的文件大小等于需要下载的大小
//                if(startPos == downloadTaskInfo.getFileSize()){
//                    success = true;
//                }
                success = true;
            } catch (Exception e) {
                setTaskState(TaskState.TASK_STATE_EXCEPTION);
                e.printStackTrace();
            } finally {
                try {
                    // 关闭文件流
                    if(inStream != null){
                        inStream.close();
                    }
                    if(rFile != null){
                        rFile.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 如果尝试的次数超过最大次数则跳出循环,并处理错误
            if(++retryCount >= retry_times) {
                setTaskState(TaskState.TASK_STATE_EXCEPTION);
                break;
            } else if (!success) {      //线程休眠10S，然后重新下载
                try { Thread.sleep(retry_delay_time); } catch (Exception ex) { }
            }
             
        } while (!success); 
        
        // 如果下载成功
        if (success) {
            if (rsfFile != null) {
                //重命名下载的文件
//                File newFile = new File(FileTools.getFileRoot()+FilePathEnum.RING_FILE+"/"+ downloadTaskInfo.getFileName());   
                File newFile = new File(downloadTaskInfo.getSavePath() +"/"+ downloadTaskInfo.getFileName());   
                if (newFile.exists()) newFile.delete();
                rsfFile.renameTo(newFile);
                setTaskState(TaskState.TASK_STATE_FINISHED);
//                if (mTaskStateChangeListener != null) {
//                    mTaskStateChangeListener.OnTaskStateChanged(TaskState.TASK_STATE_FINISHED);
//                }
            }
        }
    }
    /**
     * <p> 解决wap,3g联网问题 </p>
     * @author mingrenhan
     * 2012-6-27
     * @param url
     * @return
     * @throws Exception
     */
    private HttpURLConnection getURLConnection(URL url) throws Exception {
        String proxyHost = android.net.Proxy.getDefaultHost();
        if (proxyHost != null) {
            java.net.Proxy p = new java.net.Proxy(java.net.Proxy.Type.HTTP,
                    new InetSocketAddress(android.net.Proxy.getDefaultHost(),
                            android.net.Proxy.getDefaultPort()));
            return (HttpURLConnection) url.openConnection(p);
        } else {
            return (HttpURLConnection) url.openConnection();
        }
    }
    /**
     * 循环读取数据中检查任务状态
     * @author mingrenhan
     * 2012-6-29
     * @param rsfFile
     * @return
     */
    private boolean checkTaskState(File rsfFile){
        if(mTaskState==TaskState.TASK_STATE_CANCELING){
            //删除文件，退出线程
            if(rsfFile.exists()){
                rsfFile.delete();
            }
            setTaskState(TaskState.TASK_STATE_STOPPED);
            return true;
        }
        if(mTaskState==TaskState.TASK_STATE_STOPPING){
            setTaskState(TaskState.TASK_STATE_STOPPED);
            return true;
        }
        // 如果不存在了  则直接杀死线程,并return
        if (!rsfFile.exists()) {
        	LLog.d("task", "kill return");
            setTaskState(TaskState.TASK_STATE_EXCEPTION);
            return true;
        }
        
        return false;
    }

    public void setDownloadInfo(DownloadTaskInfo downloadTaskInfo) {
        this.downloadTaskInfo = downloadTaskInfo;
    }
    
    @Override
    public void setTaskInfo(ITaskInfo taskInfo) {
        if(taskInfo instanceof DownloadTaskInfo){
            setDownloadInfo((DownloadTaskInfo)taskInfo);
        }
    }
}
