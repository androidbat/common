package com.m.rabbit.upload.bean;

import java.util.ArrayList;

public class UploadResultData<T>{
	public ArrayList<T> data;

	public ArrayList<T> getData() {
		return data;
	}

	public void setData(ArrayList<T> data) {
		this.data = data;
	}
}
