package com.m.rabbit.app;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.m.rabbit.DataProvider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public abstract class MyBaseAdapter<MyObj> extends BaseAdapter{
	protected List<MyObj> mList=null;
    protected LayoutInflater inflater;
    protected Context mContext;
    protected DataProvider mDataProvider;
    public MyBaseAdapter(List<MyObj> list,Context context){
    	mList=list;
    	mContext=context;
    	mDataProvider = ((RootAcbActivity)context).getDataProvider();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    @Override
    public int getCount() {
        return mList==null?0:mList.size();
    }

    @Override
    public MyObj getItem(int position) {
        if(mList!=null && mList.size()>position){
            return mList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
    	if (convertView == null) {
    		convertView = generateView(position, parent);
		}
    	fillValues(position, convertView);
    	return convertView;
    }
    
    public abstract View generateView(int position,ViewGroup parent);
    public abstract void fillValues(int position,View view);
    
}
