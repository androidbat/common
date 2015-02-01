package com.m.rabbit.bean;

import android.content.Context;
import android.graphics.Bitmap;


public class ImgParas {
	
	public boolean isScaleImageView;
	public Bitmap mLoadingBitmap;
	public Context mContext;
	public ImgParas(String url) {
		this.url = url;
	}
	
	public ImgParas(String url,int width, boolean isScaleImageView) {
		this.url = url;
		this.width = width;
		this.isScaleImageView = isScaleImageView;
	}
	
	public ImgParas(String url,int width, int height) {
		this.url = url;
		this.width = width;
		this.height = height;
	}
	
	public String url;
	public boolean useCache = true;
	public int whichPage;
	public int width;
	public int height;
	public boolean round;
	public boolean mFadeInBitmap = true;
	
	public ImgParas setFadeInBitmap(boolean isFadeIn) {
		this.mFadeInBitmap = isFadeIn;
		return this;
	}
	
	public ImgParas setScale(boolean isScale) {
		this.isScaleImageView = isScale;
		return this;
	}

	public ImgParas setUrl(String url) {
		this.url = url;
		return this;
	}

	public ImgParas setUseCache(boolean useCache) {
		this.useCache = useCache;
		return this;
	}

	public ImgParas setWhichPage(int whichPage) {
		this.whichPage = whichPage;
		return this;
	}

	public ImgParas setWidth(int width) {
		this.width = width;
		return this;
	}

	public ImgParas setHeight(int height) {
		this.height = height;
		return this;
	}

	public ImgParas setRound(boolean round) {
		this.round = round;
		return this;
	}
	
	
	
}
