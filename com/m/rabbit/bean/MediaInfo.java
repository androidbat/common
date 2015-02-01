package com.m.rabbit.bean;

import java.io.Serializable;


public class MediaInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 796733462736235951L;
	private String progra_name;//当前节目名称
	private String play_url;//播放地址
	
	public MediaInfo() {
		super();
	}
	
	public MediaInfo(String play_url) {
		super();
		this.play_url = play_url;
	}
	
	public String getProgra_name() {
		return progra_name;
	}
	public void setProgra_name(String progra_name) {
		this.progra_name = progra_name;
	}
	public String getPlay_url() {
		return play_url;
	}
	public void setPlay_url(String play_url) {
		this.play_url = play_url;
	}
	
//	@Override
//	public boolean equals(Object o) {
//		return begintime.equals(((MediaInfo)o).getBegintime());
//	}
}
