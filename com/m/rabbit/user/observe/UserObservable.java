package com.m.rabbit.user.observe;

import java.util.Observable;

public class UserObservable extends Observable{
	
	public static final int LOGIN_SUCESS = 100;
	public static final int LOGOUT_SUCESS = 101;
	public static final int REGIST_SUCESS = 102;
	
	private static UserObservable mInstance;
    private UserObservable(){}
    
    public static UserObservable getInstance(){
        if(mInstance == null){
            synchronized(UserObservable.class){
                if(mInstance == null){
                    mInstance = new UserObservable();
                }
            }
        }
        return mInstance;
    }
    
    public void changed(int state){
    	setChanged();
        notifyObservers(state);
    }
}
