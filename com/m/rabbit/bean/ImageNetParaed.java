package com.m.rabbit.bean;

import android.widget.ImageView;

public class ImageNetParaed {
	public String url;
	public ImageView imageView;
	public int whichPage;
	public boolean useCache;
	public int position;
	public int width;
	
	public static ImageNetParaed getPara(final String url,final ImageView imageView,final int whichPage,boolean useCache,int position,int width){
		ImageNetParaed para=new ImageNetParaed();
		para.imageView=imageView;
		para.url=url;
		para.whichPage=whichPage;
		para.useCache=useCache;
		para.position=position;
		para.width = width;
		return para;
	}
	
}
