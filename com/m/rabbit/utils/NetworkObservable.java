package com.m.rabbit.utils;

import java.util.Observable;
import java.util.Observer;


public class NetworkObservable extends Observable {
    private static final NetworkObservable singleInstance=new NetworkObservable();
    private int mNetType = -2; //避免刚添加进去的监听者收到为0（判断网络中0代表无网）的信号
    private NetworkObservable(){
    }
    
    public static NetworkObservable getInstance(){
        return singleInstance;
    }
    
    /**
     * @param netType
     * NETWORK_UNKNOWN = -1;
     * NETWORK_NONE = 0;
     * NETWORK_WIFI = 1;
     * NETWORK_MOBILE = 2;
     * NETWORK_2G = 3;
     * NETWORK_3G = 4;
     */
    public void setNetType(int netType){
        mNetType = netType;
        setChanged();
        notifyObservers(netType);
    };
    
    @Override
    public void addObserver(Observer observer){
        super.addObserver(observer);
        if(observer!=null){
            observer.update(this, mNetType);
        }
    }
    
    public int getCurrentNetType(){
        return mNetType;
    }
}
