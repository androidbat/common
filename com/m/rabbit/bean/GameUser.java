package com.m.rabbit.bean;

public class GameUser {
	public int result=-1; //0 登录成功 1登录失败
	public int userId=-1; //用户id
	public String userName; //账号
	public String niceName; //昵称
	public String password; //密码
	public String sex; //性别
	public String eMail; //用户E-MAIL
	public String address; //住址
	public String telephone; //手机号
	public String image; //用户头像
	public int experience=-1; //经验值
	public int degreeExperience=-1; //等级经验值
	public int degree=-1; //等级值
	public String degreeName; //等级名称
	public int goldValue=-1; //金币值
	public int collectMaximum=-1; //收藏最大数
	public int reserveMaximum=-1; //预约最大数
//	private int isSigned;//是否签到(0:已签到；1：未签到)
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getNiceName() {
		return niceName;
	}
	public void setNiceName(String niceName) {
		this.niceName = niceName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	public int getDegree() {
		return degree;
	}
	public void setDegree(int degree) {
		this.degree = degree;
	}
	public String getDegreeName() {
		return degreeName;
	}
	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}
	public int getGoldValue() {
		return goldValue;
	}
	public void setGoldValue(int goldValue) {
		this.goldValue = goldValue;
	}
	public int getCollectMaximum() {
		return collectMaximum;
	}
	public void setCollectMaximum(int collectMaximum) {
		this.collectMaximum = collectMaximum;
	}
	public int getReserveMaximum() {
		return reserveMaximum;
	}
	public void setReserveMaximum(int reserveMaximum) {
		this.reserveMaximum = reserveMaximum;
	}
	public int getDegreeExperience() {
		return degreeExperience;
	}
	public void setDegreeExperience(int degreeExperience) {
		this.degreeExperience = degreeExperience;
	}
//	public int getIsSigned() {
//		return isSigned;
//	}
//	public void setIsSigned(int isSigned) {
//		this.isSigned = isSigned;
//	}
	
	
	
}
