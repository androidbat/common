package com.m.rabbit.app;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;

import com.m.rabbit.DataProvider;
import com.m.rabbit.utils.DialogUtils;
import com.m.rabbit.utils.LLog;
import com.m.rabbit.utils.ToastUtils;

public abstract class RootActivity extends FragmentActivity implements OnClickListener{
	
	public DataProvider mDataProvider;
	public boolean isActivityPause;
	public boolean isActivityDestroyed;
	protected int mScreenWidth;
	protected int mScreenHeight;
	public static boolean isMobile = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		initDataProvider();
		super.onCreate(savedInstanceState);
		DisplayMetrics dm = getResources().getDisplayMetrics();
		mScreenWidth = dm.widthPixels;
		mScreenHeight = dm.heightPixels;
		if (isMobile && mScreenWidth > mScreenHeight) {
			mScreenWidth = dm.heightPixels;
			mScreenHeight = dm.widthPixels;
		}
		isActivityDestroyed = false;
		LLog.d(" activity oncreate "+mScreenWidth+"x"+mScreenHeight);
	}
	
	private ProgressDialog progressDialog;
	public Dialog showPregrossDialog(String msg){
		if (progressDialog == null) {
			if (msg == null) {
				msg = "正在加载";
			}
			LLog.d("progressDialog == null");
			progressDialog = DialogUtils.getProgressDialog(this, null, msg);
		}else{
			progressDialog.setMessage(msg);
		}
		if (!progressDialog.isShowing()) {
			progressDialog.show();
		}
		return progressDialog;
	}
	
	public void dismissPregross(){
		if (progressDialog != null && progressDialog.isShowing()) {
			try {
				progressDialog.dismiss();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	protected abstract void findViews();

	protected void initEvents(){
		
	}
	
	protected void init(){
		
	}
	
	private void initDataProvider(){
		if (mDataProvider == null) {
			mDataProvider = new DataProvider(getApplicationContext());
		}
	}
	
	public DataProvider getDataProvider() {
		initDataProvider();
		return mDataProvider;
	}
	
	@Override
	protected void onDestroy() {
		mDataProvider.exitTask();
		mDataProvider = null;
		dismissPregross();
		super.onDestroy();
		isActivityDestroyed = true;
		LLog.d("  activity onDestroy ");
	}

	@Override
	protected void onResume() {
		LLog.d("  activity onResume ");
		mDataProvider.resume();
		isActivityPause = false;
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		LLog.d("  activity onPause ");
		isActivityPause = true;
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		LLog.d("  activity onstop ");
		mDataProvider.pause();
		super.onStop();
	}
	
	public void netError(){
		dismissPregross();
		ToastUtils.showToast(getApplicationContext(), "网络错误,请检查网络");
	}
	
	@Override
	public void onClick(View v) {
		
	}
	
	public void startActivity(Class<?> cls){
		Intent intent=new Intent();
		intent.setClass(this, cls);
		this.startActivity(intent);
	}
	
	public Intent nIntent(Class<?> cls){
		Intent intent=new Intent();
		intent.setClass(this, cls);
		return intent;
	}
	
	public void startActivityFroResult(Class<?> cls,int requestCode){
		Intent intent=new Intent();
		intent.setClass(this, cls);
		this.startActivityForResult(intent, requestCode);
	}
	
	public void startActivityFroResult(Class<?> cls,int requestCode,Bundle bundle){
		Intent intent=new Intent();
		intent.setClass(this, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		this.startActivityForResult(intent, requestCode);
	}
	
	public void startActivity(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(this, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}

	/** 通过Action跳转界面 **/
	protected void startActivity(String action) {
		startActivity(action, null);
	}

	/** 含有Bundle通过Action跳转界面 **/
	protected void startActivity(String action, Bundle bundle) {
		Intent intent = new Intent();
		intent.setAction(action);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
	}
	
}
