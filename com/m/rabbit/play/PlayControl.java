package com.m.rabbit.play;



import java.io.FileDescriptor;

import android.content.Context;
import android.net.Uri;

import com.m.rabbit.bean.MediaInfo;
import com.m.rabbit.utils.LLog;


/**
 * 负责处理播放相关的业务逻辑
 * @author hanmingren
 *
 */
public class PlayControl implements IPlayControler{
	public static final int MAX_SEEKBAR_LENGTH=1000;
	private IMediaPlayer mIMediaPlayer;
	private MSurfaceView mMSurfaceView;
	private Context mContext;
	private static PlayControl mPlayControl;
	private PlayControl(){
	}
	
	public void setContext(Context mContext) {
		this.mContext = mContext;
	}

	public static  PlayControl getInstance(){
		if(mPlayControl==null){
			synchronized (PlayControl.class) {
				if(mPlayControl==null){
					mPlayControl=new PlayControl();
					return mPlayControl;
				}
			}
		}
		return mPlayControl;
	}
	
	public void setIMediaPlayer(IMediaPlayer m,MSurfaceView ms){
		mIMediaPlayer=m;
		mMSurfaceView=ms;
	}
	
	public void setIMediaPlayer(IMediaPlayer m){
		mIMediaPlayer=m;
	}
	
	public boolean isPause(){
		if (mIMediaPlayer!=null) {
			return mIMediaPlayer.getCurrentState() == IMediaPlayer.STATE_PAUSED;
		}
		return false;
	}
	
	@Override
	public void start() {
		if(mIMediaPlayer!=null){
			mIMediaPlayer.start();
		}
	}

	@Override
	public void pause() {
		if(mIMediaPlayer!=null){
			mIMediaPlayer.pause();
		}
	}
	
	private boolean isCompletion = false;
	public void setIsCompletion(boolean enable){
		isCompletion = enable;
	}
	public boolean isCompletion(){
		return isCompletion;
	}

	@Override
	public int getDuration() {
		if(mIMediaPlayer!=null){
			return mIMediaPlayer.getDuration();
		}
		return 0;
	}

	@Override
	public int getCurrentPosition() {
		if(mIMediaPlayer!=null){
			return mIMediaPlayer.getCurrentPosition();
		}
		return 0;
	}

	@Override
	public void seekTo(float percent) {
		
		if(mIMediaPlayer!=null){
			int seekPosition = (int)(percent*mIMediaPlayer.getDuration());
			mIMediaPlayer.seekTo(seekPosition);
		}
	}
	
	public void seekTo(int position) {
		
		if(mIMediaPlayer!=null){
			mIMediaPlayer.seekTo(position);
		}
	}
	
	public void seekBy(int pos) {
		if(mIMediaPlayer!=null && pos<mIMediaPlayer.getDuration()){
			final int seekPosition = mIMediaPlayer.getCurrentPosition()+pos;
			mIMediaPlayer.seekTo(seekPosition);
		}
	}
	
	public void play_or_pause(){
		if(mIMediaPlayer!=null){
			if(mIMediaPlayer.isPlaying()){
				mIMediaPlayer.pause();
			}else{
				mIMediaPlayer.start();
			}
		}
	}
	

    /**
     * 获得当前的播放进度
     * @return
     */
    public int getCurrentPlayPosition(MediaInfo mediaInfo){
    	long pos=0;
    	if(mediaInfo!=null){
//    		if(mediaInfo.getEndtime()-mediaInfo.getBegintime()>0){
//        		pos=(mCurrentPlayTime-beginTime)*MAX_SEEKBAR_LENGTH/(endTime-beginTime);
//        	}
    	}
    	return Math.round(pos);
    }
//	public void seekTo2(int pos){
//    	if(pos>0){
//    		long time=pos*(endTime-beginTime)/MAX_SEEKBAR_LENGTH+beginTime;
//        	String seekToTime=DateTools.transTimeInMillisToString(time);
//        	doSeekTo(seekToTime);
//    	}else if(pos==0){
////    		String seekToTime=DateTools.transTimeInMillisToString(beginTime);
//    		if(mMediaInfo!=null){
//    			doSeekTo(mMediaInfo.getBegintime());
//    		}
//    	}
//    }
	
	@Override
	public boolean isPlaying() {
		if(mIMediaPlayer!=null){
			return mIMediaPlayer.isPlaying();
		}
		return false;
	}

	@Override
	public int getBufferPercentage() {
		if(mIMediaPlayer!=null){
			int bufferPos=mIMediaPlayer.getBufferProgress();
			int duration=mIMediaPlayer.getDuration();
			if(bufferPos<duration && duration>0){
				return bufferPos/duration;
			}
		}
		return 0;
	}

	@Override
	public boolean canPause() {
		if(mIMediaPlayer!=null && mIMediaPlayer.isPlaying()){
			return true;
		}
		return false;
	}

	@Override
	public boolean canSeekBackward() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canSeekForward() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		if(mIMediaPlayer!=null){
			mIMediaPlayer.release();
			mIMediaPlayer=null;
		}
	}

	@Override
	public void playUrl(String videoUrl) {
		LLog.d("player",videoUrl + "");
        try {
        	if (mIMediaPlayer != null) {
        		mIMediaPlayer.reset();  
        		mIMediaPlayer.setDataSource(videoUrl);  
        		mIMediaPlayer.prepareAsync();//prepare之后自动播放  
			}else{
			}
        } catch (IllegalArgumentException e) {  
            e.printStackTrace();  
        } catch (IllegalStateException e) {  
            e.printStackTrace();  
        }
	}
	
	@Override
	public void playUrl(FileDescriptor dp) {
        try {
        	if (mIMediaPlayer != null) {
        		mIMediaPlayer.reset();  
        		mIMediaPlayer.setDataSource(dp);  
        		mIMediaPlayer.prepareAsync();//prepare之后自动播放  
			}else{
			}
        } catch (IllegalArgumentException e) {  
            e.printStackTrace();  
        } catch (IllegalStateException e) {  
            e.printStackTrace();  
        }
	}
	
	@Override
	public void playRes(int resid) {
        try {
        	if (mIMediaPlayer != null) {
        		Uri uri = Uri.parse("android.resource://com.mochou.tv/"+resid);
        		mIMediaPlayer.reset();  
        		mIMediaPlayer.setDataSource(mContext,uri);  
        		mIMediaPlayer.prepareAsync();//prepare之后自动播放  
			}else{
			}
        } catch (IllegalArgumentException e) {  
            e.printStackTrace();  
        } catch (IllegalStateException e) {  
            e.printStackTrace();  
        }
	}

	@Override
	public void stop() {
		if(mIMediaPlayer!=null){
			mIMediaPlayer.stop();
		}
	}

	@Override
	public void reset() {
		if(mIMediaPlayer!=null){
			mIMediaPlayer.reset();
		}
	}

	@Override
	public int getVideoWidth() {
		if(mIMediaPlayer!=null){
			return mIMediaPlayer.getVideoWidth();
		}
		return 0;
	}

	@Override
	public int getVideoHeight() {
		if(mIMediaPlayer!=null){
			return mIMediaPlayer.getVideoHeight();
		}
		return 0;
	}
	
	public IMediaPlayer getMediaPlayer(){
		return mIMediaPlayer;
	}

	public MSurfaceView getMSurfaceView() {
		return mMSurfaceView;
	}
	
	public boolean isStarted() {
		if(mIMediaPlayer!=null){
			return mIMediaPlayer.getCurrentState() > IMediaPlayer.STATE_STARTED;
		}
		return false;
	}
	

}
