package com.m.rabbit.download.task;

import com.m.rabbit.download.itask.ITaskInfo;

import android.os.Environment;


public class DownloadTaskInfo implements ITaskInfo {
    private String      downUrl;                // 下载的URL链接
    private String      saveDir= Environment.getExternalStorageDirectory() + "/tv_shopping/preload/";;                // 本地保存数据的地址
    private String      fileName;               // 文件保存的名称
    private String      temporaryName;          // 下载时候的临时名称
    private long        totalFileSize;          // 文件的实际大小
    private long        downloadedSize;         // 已经下载的文件大小
    
    @Override
    public String getKey() {
        return downUrl;
    }

    @Override
    public String getTaskClassName() {
        return DownloadTask.class.getName();
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getSavePath() {
        return saveDir;
    }

    public void setSavePath(String savePath) {
        this.saveDir = savePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTemporaryName() {
        return temporaryName;
    }

    public void setTemporaryName(String temporaryName) {
        this.temporaryName = temporaryName;
    }

    public long getFileSize() {
        return totalFileSize;
    }

    public void setFileSize(long fileSize) {
        this.totalFileSize = fileSize;
    }

    public long getDownloadedSize() {
        return downloadedSize;
    }

    public void setDownloadedSize(long downloadedSize) {
        this.downloadedSize = downloadedSize;
    }

}
