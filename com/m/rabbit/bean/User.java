package com.m.rabbit.bean;

import java.io.Serializable;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4746734735681523645L;
	
	public String userId;//: "8ba015ba9f654a709dc6f5a9553d0dca",
	public String loginName;//: "abcdef@126.com",
	public String userName;//: "张三",
	public String loginTime;//: "Mar 24, 2014 5:23:05 PM",
	public String logoutTime;//: "Mar 20, 2014 6:18:05 PM",
	public String type;//: "0",
	public String status;//: "000",
	public String grade;//: "00010001",
	public String gradeName;//: "普通会员",
	public String integration;//: 0,
	public String meId;//: "34fad0a16b294f0bb77c4ca91d315f82",
	public String firstLetter;//首字母
	public String avatar;//头像
	
	public String phone;//
	public String age;//
	public String sex;//
	public String kid;//
	public String key;//
	
	public String address;//
	public String img;//
	public String meName;//
	
	
	
	public User(String userId, String userName) {
		this.userId = userId;
		this.userName = userName;
	}
	
	public User(){
		
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", loginName=" + loginName
				+ ", userName=" + userName + ", loginTime=" + loginTime
				+ ", logoutTime=" + logoutTime + ", type=" + type + ", status="
				+ status + ", grade=" + grade + ", gradeName=" + gradeName
				+ ", integration=" + integration + ", meId=" + meId
				+ ", firstLetter=" + firstLetter + ", avatar=" + avatar
				+ ", phone=" + phone + ", age=" + age + ", sex=" + sex
				+ ", kid=" + kid + ", key=" + key + ", address=" + address
				+ ", img=" + img + ", meName=" + meName + "]";
	}
	
	
	
}
