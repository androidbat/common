package com.m.rabbit.play;

import java.io.FileDescriptor;

public interface IPlayControler {
	public void    start();
	public void    pause();
	public void    stop();
	public void    reset();
	public int     getDuration();
	public int     getCurrentPosition();
	public void    seekTo(float percent);
	public boolean isPlaying();
	public int     getBufferPercentage();
	public void release();
	public boolean canPause();
	public boolean canSeekBackward();
	public boolean canSeekForward();
	public void playUrl(String videoUrl);
	public void playUrl(FileDescriptor dp);
	public void playRes(int resourceId);
	public int getVideoWidth();
	public int getVideoHeight();
	
}
