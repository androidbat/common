package com.m.rabbit.upload.bean;

import com.m.rabbit.download.itask.ITaskInfo;
import com.m.rabbit.upload.UploadTask;
import com.m.rabbit.utils.FileUtils;

public class UpLoadTaskInfo implements ITaskInfo{
	public static int PART_FILE_SIZE = 102400;
	private String      filePath;                // 要上传的文件路径，包括文件名
	private String 		fileName; //文件名
	private String		title;//标题
	private String 		type;//类型 :图片，视频，文档，压缩包
	private String 		desp;//描述
	private int 	 	uploadedPart = 0;//已经上传了多少块
    private int 		uploadTotal; //把文件分为多少块上传
    private String 		imgPath;//要上传文件对应的本地图片路径,在客户端的列表中要显示的图片
    private String 		fromUserId;
    private String 		toUserId;
    private long 		fileSize;//文件大小
    private String      upLoadUrl=null;//把头部上传后服务器反回的文件上传地址
    private String      id=null;//把头部上传后服务器反回的id
    private boolean     upload_head_success=false;//如果上传头部成功这里会变成true
    
    public UpLoadTaskInfo(String filePath){
    	this.filePath = filePath;
        init(filePath);
    }
    
    private void init(String filePath) {
        FileUtils filePorxy = new FileUtils(filePath);
        this.fileName = filePorxy.getFileName();
        this.fileSize = filePorxy.getFileSize();
        if (fileSize > 0) {
            this.uploadTotal = (int) (fileSize % PART_FILE_SIZE == 0 ? (fileSize / PART_FILE_SIZE)
                    : (fileSize / PART_FILE_SIZE) + 1);
        }
    }
    
	@Override
	public String getKey() {
		return filePath;
	}

	@Override
	public String getTaskClassName() {
		return UploadTask.class.getName();
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
		init(filePath);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesp() {
		return desp;
	}

	public void setDesp(String desp) {
		this.desp = desp;
	}

	public synchronized int getUploadedPart() {
		return uploadedPart;
	}

	public void setUploadedPart(int uploadedPart) {
		this.uploadedPart = uploadedPart;
	}

	public long getUploadTotal() {
		return uploadTotal;
	}

	public void setUploadTotal(int uploadTotal) {
		this.uploadTotal = uploadTotal;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public String getFileName() {
		return fileName;
	}

	private void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	private void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public String getUpLoadUrl() {
		return upLoadUrl;
	}

	public void setUpLoadUrl(String upLoadUrl) {
		this.upLoadUrl = upLoadUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isUpload_head_success() {
		return upload_head_success;
	}

	public void setUpload_head_success(boolean upload_head_success) {
		this.upload_head_success = upload_head_success;
	}

	
}
