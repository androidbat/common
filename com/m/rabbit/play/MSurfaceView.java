package com.m.rabbit.play;



import android.content.Context;
import android.media.AudioManager;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup.MarginLayoutParams;

import com.m.rabbit.bean.MediaInfo;
import com.m.rabbit.play.IMediaPlayer.OnBufferingUpdateListener;
import com.m.rabbit.play.IMediaPlayer.OnFlagErrorListener;
import com.m.rabbit.utils.LLog;

public class MSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
	private static final String TAG = MSurfaceView.class.getSimpleName();
	private SurfaceHolder mSurfaceHolder = null;
	private IMediaPlayer mMediaPlayer;
	private CallBack mCallBack;
	private MediaInfo mMediaInfo=null;
	private boolean destoryPlayerWhenDestroy = false;
	
	public void setDestoryPlayerWhenDestroy(boolean destoryPlayerWhenDestroy) {
		this.destoryPlayerWhenDestroy = destoryPlayerWhenDestroy;
	}

	public MSurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initVideoView();
	}

	public MSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initVideoView();
	}

	public MSurfaceView(Context context) {
		super(context);
		initVideoView();
	}

	public IMediaPlayer getMediaPlayer(){
		return mMediaPlayer;
	}

	public void setCallBack(CallBack callBack){
		mCallBack=callBack;
	}
	
	public void setMediaInfo(MediaInfo mediaInfo){
		mMediaInfo=mediaInfo;
	}
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		LLog.d(TAG,"surfaceCreated width "+mSwidth +" height "+mSheight);
		mSurfaceHolder = holder;
        if(mMediaInfo!=null && mMediaInfo.getPlay_url() != null){
        	openVideo(mMediaInfo.getPlay_url());
        }else{
        	openVideo(null);
        }
        setScreenRatio(ratioMode);
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		LLog.d(TAG,"surfaceDestroyed");
		if (destoryPlayerWhenDestroy) {
			if (mMediaPlayer != null) {
				PlayControl.getInstance().release();
			}
		}
		mMediaPlayer = null;
	}
	
	private void initVideoView() {
        getHolder().addCallback(this);
        getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//        setFocusable(true);
//        setFocusableInTouchMode(true);
        requestFocus();
    }
	
	private void openVideo(String playurl) {
		try {
	    	mMediaPlayer = PlayControl.getInstance().getMediaPlayer();
	    	LLog.d("surface"," open video");
	    	if(mMediaPlayer == null){
	    			mMediaPlayer = new SystemPlayer();
	    			PlayControl.getInstance().setIMediaPlayer(mMediaPlayer);
	    			initMediaPlayer();
	    	}else{
	    		LLog.d("surface"," open video mMediaPlayer != null");
	    		initMediaPlayer();
	    		if (!destoryPlayerWhenDestroy) {
	    			if (!PlayControl.getInstance().isCompletion()) {
	    				postDelayed(new Runnable() {
	    					public void run() {
	    						LLog.d("surface","start");
	    						PlayControl.getInstance().start();
	    					}
	    				}, 100);
	    			}else{
	    				PlayControl.getInstance().setIsCompletion(false);
	    			}
				}
	    	}
	    	if (playurl != null) {
	    		mMediaPlayer.setDataSource(playurl);
	    		mMediaPlayer.prepareAsync();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	
	private void initMediaPlayer(){
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mMediaPlayer.setScreenOnWhilePlaying(true);
		mMediaPlayer.setOnPreparedListener(mOnPreparedListener);
		mMediaPlayer.setOnVideoSizeChangedListener(mOnVideoSizeChangedListener);
		mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
		mMediaPlayer.setOnFlagErrorListener(mOnFlagErrorListener);
		mMediaPlayer.setOnInfoListener(mOnInfoListener);
		mMediaPlayer.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
		mMediaPlayer.setOnSeekCompleteListener(mOnSeekCompleteListener);
		mMediaPlayer.setDisplay(mSurfaceHolder);
	}
    
	public void setOnFlagErrorListener(OnFlagErrorListener onFlagErrorListener){
		mOnFlagErrorListener = onFlagErrorListener;
	}
	
	public void setOnBufferingUpdateListener(OnBufferingUpdateListener onBufferingUpdateListener){
		mOnBufferingUpdateListener = onBufferingUpdateListener;
	}
	
	IMediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = new IMediaPlayer.OnBufferingUpdateListener() {
		
		@Override
		public void onBufferingUpdate(IMediaPlayer mp, int percent) {
			if(mCallBack != null){
				mCallBack.onBufferingUpdate(percent);
			}
		}
	};
	
	IMediaPlayer.OnFlagErrorListener mOnFlagErrorListener = new IMediaPlayer.OnFlagErrorListener() {
		
		@Override
		public boolean onFlagError(IMediaPlayer mp, int what, int extra) {
			if(mCallBack != null){
				mCallBack.onError(what, extra);
			}
			return true;
		}
	};
	
	IMediaPlayer.OnInfoListener mOnInfoListener = new IMediaPlayer.OnInfoListener() {
		
		@Override
		public boolean onInfo(IMediaPlayer mp, int what, int extra) {
			if(mCallBack != null){
				mCallBack.onInfo(what, extra);
			}
			return true;
		}
	
	};   
	
    IMediaPlayer.OnPreparedListener mOnPreparedListener = new IMediaPlayer.OnPreparedListener() {

		@Override
		public void onPrepared(IMediaPlayer mp) {
			mp.start();
			if(mCallBack!=null){
				mCallBack.onPrepared();
			}
			setScreenRatio(ratioMode);
		}
    };

    private IMediaPlayer.OnCompletionListener mOnCompletionListener = new IMediaPlayer.OnCompletionListener() {

		@Override
		public void onCompletion(IMediaPlayer mp) {
			if(mCallBack != null){
				mCallBack.onCompletion();
			}
		}
    };

    IMediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangedListener = new IMediaPlayer.OnVideoSizeChangedListener() {

		@Override
		public void onVideoSizeChanged(IMediaPlayer mp, int width, int height) {
        }
    };
    
    IMediaPlayer.OnSeekCompleteListener mOnSeekCompleteListener = new IMediaPlayer.OnSeekCompleteListener() {

		@Override
		public void onSeekComplete(IMediaPlayer mp) {
			if(mCallBack!=null){
				mCallBack.onSeekComplete();
			}	
		}
    };    
    public interface CallBack{
    	void onPrepared();
    	void onCompletion();
    	void onError(int what, int extra);
    	void onInfo(int what, int extra);
    	void onBufferingUpdate(int percent);
    	void onSeekComplete();
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    	LLog.d(TAG,"onMeasure width "+mSwidth +" height "+mSheight);
    	if (mSwidth == 0 && getMeasuredWidth() > 0 ) {
			mSwidth = getMeasuredWidth();
			LLog.d(TAG,"onMeasure mSwidth "+mSwidth +" height "+mSheight);
		}
    	if (mSheight == 0 && getMeasuredHeight() > 0 ) {
    		mSheight = getMeasuredHeight();
    		LLog.d(TAG,"width "+mSwidth +" height "+mSheight);
		}
    }
    
    private int mSwidth,mSheight;
    
    public static int FILL_SCREEN = 0;
    public static int RATIO = 1;
    private int ratioMode = 0;
    public void setRatioMode(int ratioMode) {
		this.ratioMode = ratioMode;
	}

	public boolean setScreenRatio(int scaleMode){
		LLog.d(TAG,"lastMode "+mSwidth +" ratioMode "+mSheight);
//    	return setScreenRatio(scaleMode,mSwidth,mSheight);
		return false;
	}
    public boolean setScreenRatio(int scaleMode,int destWidth,int destHeight) {	
		
		final int mVideoWidth = PlayControl.getInstance().getVideoWidth();
		final int mVideoHeight = PlayControl.getInstance().getVideoHeight();
		int surHeight = 0;
		int surWidth = 0;
		
		LLog.d("screen", "destWidth=" + destWidth + "destHeight=" + destHeight +" mode="+scaleMode);
		LLog.d("screen", "mVideoWidth=" + mVideoWidth + "mVideoHeight=" + mVideoHeight);
		
		
		if(mVideoWidth == 0 || mVideoHeight == 0 ){
			return false;
		}
		
        //Full screen
		if(scaleMode==FILL_SCREEN){
			surWidth = destWidth;
			surHeight = destHeight;
		}else{
			//Set the surface size to match the size of the video, depending on which size is smaller
			if (mVideoWidth > destWidth || mVideoHeight > destHeight) {// If the width is greater than the display size of the video, to obtain the ratio
				float heightRatio = (float) mVideoHeight / (float) destHeight;
				float widthRatio = (float) mVideoWidth / (float) destWidth;

				if (heightRatio > 1 || widthRatio > 1) {// Only the width or the height direction beyond
					
					if (heightRatio > widthRatio) {// Subject to high
						surHeight = (int) Math.ceil((float) mVideoHeight
								/ (float) heightRatio);
						surWidth = (int) Math.ceil((float) mVideoWidth
								/ (float) heightRatio);
					} else {// Subject to width
						surHeight = (int) Math.ceil((float) mVideoHeight
								/ (float) widthRatio);
						surWidth = (int) Math.ceil((float) mVideoWidth
								/ (float) widthRatio);
					}
					
				} 
			}else {
				if (destWidth * mVideoHeight / mVideoWidth <= destHeight){
					surWidth = destWidth;
					surHeight = surWidth * mVideoHeight / mVideoWidth;
				}else{
					surHeight = destHeight;
					surWidth = surHeight * mVideoWidth / mVideoHeight;
				}
			}
		}	
		
		setSurfaceSize(surWidth,surHeight,(destWidth-surWidth)/2,(destHeight-surHeight)/2);
		return true;
	}
	
	public void setSurfaceSize(int width,int height,int left,int top){
		try {
			MarginLayoutParams lp = (MarginLayoutParams) getLayoutParams();
			LLog.d("screen", "getLayoutParams lp.width = " + lp.width + "  lp.height=" + lp.height);
			lp.width = width;
			lp.height = height;
			lp.leftMargin = left;
			lp.topMargin = top;
			setLayoutParams(lp);
			LLog.d("screen", "setLayoutParams width = "+ width+" height = "+ height + " left = "+left+ " top = "+top );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
}
