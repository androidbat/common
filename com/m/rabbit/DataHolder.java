package com.m.rabbit;

import com.m.rabbit.cache.DiskCache;
import com.m.rabbit.net.RequestListener;

public class DataHolder implements RequestListener{
    private Object tempData;
    private DataListener mListener;
    private String mUrl;
    private boolean mUseCache;
    public DataHolder(String url, DataListener listener,boolean useCache){
        this.mListener=listener;
        this.mUrl=url;
        this.mUseCache=useCache;
    }
    
    public Object getTempData() {
        return tempData;
    }

    public void setTempData(Object tempData) {
        this.tempData = tempData;
    }

    @Override
    public void onResult(Object result, int state) {
    	try {
			if(mUseCache){
				if (result == null) {
					returnDataFromCache(state);
					return;
				}else{
					if (mListener != null) {
						if(state==0){
							mListener.onDataReady(result);
							saveToCache(mUrl.hashCode(),result);
						}else{
							returnDataFromCache(state);
						}
					}
				}
			}else{
				System.out.println(result.toString());
				if (mListener != null) {
					if(state==0){
						mListener.onDataReady(mListener.resolve(result.toString()));
					}else{
						mListener.onNoData(state);
					}
				}
			}
		} catch (Exception e) {
		}
    }

    private void returnDataFromCache(int state){
        if (mListener != null) {
            if(tempData!=null){
                mListener.onDataReady(tempData);
            }else{
                mListener.onNoData(state);
            }
        }
    }
    
    private void saveToCache(int key,Object value){
        if(value!=null){
            try {
                DiskCache.getStringCache().put(key, value);
            } catch (Exception e) {
            }
        }
    }
}
