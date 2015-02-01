package com.m.rabbit.bean;

import java.io.Serializable;

public class Program implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public int programId;
	public String programName;
	public String proStarttime;
	public String runtime;
	public String director;
	public String actor;
	public String programType;
	public String description;
	public String score;
	public String imgUrl;
	public String videoUrl;
	public String tvName;
	public int tvId;
	public String orderTime;
	public String orderId;
	public String programTypeCode;
	public String jsNumber;
	public int watchedNumber;
	
	public int getWatchedNumber() {
		return watchedNumber;
	}
	public void setWatchedNumber(int watchedNumber) {
		this.watchedNumber = watchedNumber;
	}
	public String getTvName() {
        return tvName;
    }
    public void setTvName(String tvName) {
        this.tvName = tvName;
    }
    public int getTvId() {
        return tvId;
    }
    public void setTvId(int tvId) {
        this.tvId = tvId;
    }
    public int getProgramId() {
		return programId;
	}
	public void setProgramId(int programId) {
		this.programId = programId;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getProStarttime() {
		return proStarttime;
	}
	public void setProStarttime(String proStarttime) {
		this.proStarttime = proStarttime;
	}
	public String getRuntime() {
		return runtime;
	}
	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getActor() {
		return actor;
	}
	public void setActor(String actor) {
		this.actor = actor;
	}
	public String getProgramType() {
		return programType;
	}
	public void setProgramType(String programType) {
		this.programType = programType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	
    public String getProgramTypeCode() {
		return programTypeCode;
	}
	public void setProgramTypeCode(String programTypeCode) {
		this.programTypeCode = programTypeCode;
	}
	public String getJsNumber() {
		return jsNumber;
	}
	public void setJsNumber(String jsNumber) {
		this.jsNumber = jsNumber;
	}
	@Override
    public String toString() {
        return "Program [programId=" + programId + ", programName="
                + programName + ", proStarttime=" + proStarttime + ", runtime="
                + runtime + ", director=" + director + ", actor=" + actor
                + ", programType=" + programType + ", description="
                + description + ", score=" + score + ", imgUrl=" + imgUrl
                + ", videoUrl=" + videoUrl + ", tvName=" + tvName + ", tvId="
                + tvId + "]";
    }
	@Override
	public boolean equals(Object o) {
		return proStarttime.equals(((Program)o).proStarttime);
	}
}
