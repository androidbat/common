package com.m.rabbit.ashop;

import java.io.Serializable;
import java.util.ArrayList;

public class GoodsAttr implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4922201966208178972L;
	/**
	 * 
	 */
	public String mBh;//"3BF0E9B7060C47258C3BA7BA7E45E60E",
	 public String mName;//#Dior凝脂恒久亲肤系列#尺寸",
	 public ArrayList<GoodSubAttr> list;
              
              
     public static class GoodSubAttr implements Serializable{
    	 /**
		 * 
		 */
		private static final long serialVersionUID = -8612884119225128980L;
		/**
		 * 
		 */
		public String bh;// "45B0ACA27CAB4B37A8C73B3716A092E3",
    	 public String sjbh;// "3BF0E9B7060C47258C3BA7BA7E45E60E",
    	 public String mc;// "大号",
    	 public String sx;// 1,
    	 public String zt;// "0"
     }
}
