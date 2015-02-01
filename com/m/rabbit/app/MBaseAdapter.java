package com.m.rabbit.app;

import java.util.List;

import com.m.rabbit.DataProvider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public abstract class MBaseAdapter<MyObj> extends BaseAdapter{
	protected List<MyObj> mList=null;
    protected LayoutInflater inflater;
    protected Context mContext;
    protected DataProvider mDataProvider;
    public MBaseAdapter(List<MyObj> list,Context context){
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
    public Object getItem(int position) {
        if(mList!=null && mList.size()>=position){
            return mList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
   
}
