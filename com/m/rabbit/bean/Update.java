package com.m.rabbit.bean;

import java.io.Serializable;

public class Update implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String code;
	public String appName;
	public int appCode;
	public String encode;
	public String appInfo;
	public String appPath;
	public int updateFlag;//0:静默
	public int force;//0:默认升级方式，1：强制升级方式
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPackageName() {
		return appName;
	}
	public void setPackageName(String packageName) {
		this.appName = packageName;
	}
	public int getVersionCode() {
		return appCode;
	}
	public void setVersionCode(int versionCode) {
		this.appCode = versionCode;
	}
	public String getEncode() {
		return encode;
	}
	public void setEncode(String encode) {
		this.encode = encode;
	}
	public String getFilePath() {
		return appPath;
	}
	public void setFilePath(String filePath) {
		this.appPath = filePath;
	}
	public int getUpdateFlag() {
		return updateFlag;
	}
	public void setUpdateFlag(int updateFlag) {
		this.updateFlag = updateFlag;
	}
}
