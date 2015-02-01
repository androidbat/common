package com.m.rabbit.bean;

import java.io.Serializable;

import android.R.integer;

public class Coupon implements Serializable {
	public String id;//	编号	String	32
	public String meId;//	会员编号	String	32
	public String couId;//	商品编号	String	32
	public String cDate;//	会员获取时间	String	
	public int couType;//	优惠券类型（0注册优惠券，1返利优惠券，2满就送优惠券)
	public String couName;//	优惠券名称	String	
	public double quota;//	金额	double	
	public int days;//	有效天数(0表示永远有效)	int	
	public String sDate;//	优惠券有效开始日期	String	
	public String eDate;//	优惠券有效结束日期	String	
	public int count;//	数量	int	
	public int couNum;//	优惠券数量	int	
	public int couUserNum;//	优惠券使用数量	int	
}
