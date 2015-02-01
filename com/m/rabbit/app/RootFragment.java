package com.m.rabbit.app;

import com.m.rabbit.DataProvider;
import com.m.rabbit.utils.LLog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;


public class RootFragment extends Fragment implements OnClickListener{
	
	protected DataProvider mDataProvider;
	protected boolean isActivityPause;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDataProvider = ((RootAcbActivity)getActivity()).getDataProvider();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		isActivityCreated = true;
		if (getUserVisibleHint()) {
			onFragmentResume();
		}
	}
	
	@Override
	public void onResume() {
		isActivityPause = false;
		super.onResume();
	}
	
	@Override
	public void onPause() {
		isActivityPause = true;
		super.onPause();
	}
	
	private boolean isActivityCreated;
	protected void gotoOtherActivity(Class<?> cls){
		if(cls==null){
			return;
		}
		Intent intent=new Intent(getActivity().getApplicationContext(), cls);
		startActivity(intent);
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (!isActivityCreated) {
			return;
		}
		if (isVisibleToUser) {
			onFragmentResume();
		}else{
			onFragmentPause();
		}
	}
	
	protected void onFragmentResume(){
	}
	
	protected void onFragmentPause(){
	}
	
	public void checked(){
		if (getUserVisibleHint()) {
			clickAgain();
		}
	}
	
	protected void clickAgain(){
		
	}

	@Override
	public void onClick(View v) {
		
	}
	
	
}
