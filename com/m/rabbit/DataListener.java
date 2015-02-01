package com.m.rabbit;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;


public abstract class DataListener<T>{
	public Type mType;
	
	public DataListener(){
	}
	
	public DataListener(Type type){
		mType = type;
	}
	
	public T resolve(Object result){
		if (mType != null) {
			return new Gson().fromJson(result.toString(), mType);
		}
		return null;
	}
	
    public abstract void onDataReady(T result);
    public abstract void onNoData(int state);
    public void onProgressChanged(int progress){}
    public void onTaskComplete(T result){}
    
}
