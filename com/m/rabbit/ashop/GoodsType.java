package com.m.rabbit.ashop;

import java.io.Serializable;



public class GoodsType implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1699953754652266234L;
	public int resId;
	public int focusId;
	
    public String id;
	public String code;
	public String name;
	public String dictcode;
	public String dictname;
	public String note;//海淘奇货,手机显示
	public String logo; 
	
	public int barId;
	public int leftId;
	public int rightId;
	public int upId;
	public int downId;
	public int arrowId;
	public int bordorId;
	public int detailId;
	public String color;
	public String htmlUrl;
	public String imageUrl;
	public String type;
	public int no;
	
	@Override
	public boolean equals(Object o) {
		if (code == null) {
			return false;
		}
		return code.equals(((GoodsType)o).code);
	}

	public GoodsType(int barId, int leftId, int rightId, int upId, int downId,
			int arrowId,String color,int bordorId,int detailId) {
		super();
		this.barId = barId;
		this.leftId = leftId;
		this.rightId = rightId;
		this.upId = upId;
		this.downId = downId;
		this.arrowId = arrowId;
		this.color = color;
		this.bordorId = bordorId;
		this.detailId = detailId;
	}
	
	public GoodsType(){
		
	}

	@Override
	public String toString() {
		return "GoodsType [resId=" + resId + ", focusId=" + focusId + ", id="
				+ id + ", code=" + code + ", name=" + name + ", dictcode="
				+ dictcode + ", dictname=" + dictname + ", note=" + note
				+ ", logo=" + logo + ", barId=" + barId + ", leftId=" + leftId
				+ ", rightId=" + rightId + ", upId=" + upId + ", downId="
				+ downId + ", arrowId=" + arrowId + ", bordorId=" + bordorId
				+ ", detailId=" + detailId + ", color=" + color + ", htmlUrl="
				+ htmlUrl + ", type=" + type + "]";
	}
	
	
}
