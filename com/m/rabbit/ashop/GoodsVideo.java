package com.m.rabbit.ashop;

import java.io.Serializable;

public class GoodsVideo implements Serializable {
	public String videoId;//	编号	String
	public String vName;//视频名称	String
	public String vPath;//视频地址	String
	public int vSort;//	排序	int
	public String vNetPath;//	网络视频播放地址	String
	
	/** 视频类型（0普通，1高清,2网络播视频）	 */
	public String videoType;
	

}
