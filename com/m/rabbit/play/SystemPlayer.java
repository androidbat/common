package com.m.rabbit.play;

import java.io.FileDescriptor;
import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.m.rabbit.utils.LLog;

public class SystemPlayer implements IMediaPlayer {

    private volatile int mState = STATE_UNDEF;
    private MediaPlayer mMediaPlayer = null;
    private MediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = null;
    private MediaPlayer.OnCompletionListener mOnCompletionListener = null;
    private MediaPlayer.OnErrorListener mOnFlagErrorListener = null;
    private MediaPlayer.OnInfoListener mOnInfoListener = null;
    private MediaPlayer.OnPreparedListener mOnPreparedListener = null;
    private MediaPlayer.OnSeekCompleteListener mSeekCompleteListener = null;
    private MediaPlayer.OnVideoSizeChangedListener mVideoSizeChangedListener = null;
    private static boolean DEBUG = false;

    public SystemPlayer() {
        mMediaPlayer = new MediaPlayer();
        mState = STATE_IDLE;
    }
    
    public  IMediaPlayer CreateMediaPlayer(Context context,Uri uri){
    	mMediaPlayer=MediaPlayer.create(context, uri);
    	mState = STATE_IDLE;
    	return this;
    }

    @Override
    public int getCurrentPosition() {
        int position = 0;
        if (mState > STATE_PREPARED && mState < STATE_PLAYBACK_COMPLETE) {
            try {
                position = mMediaPlayer.getCurrentPosition();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
        	if (DEBUG) {
        		LLog.e("error!call getCurrentPosition() in state " + mState);
			}
        }
        return position;
    }

    @Override
    public int getDuration() {
        int duration = 0;
        if (mState > STATE_PREPARED && mState < STATE_PLAYBACK_COMPLETE) {
            try {
            	if(mMediaPlayer!=null){
            		duration = mMediaPlayer.getDuration();
            	}
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        } else {
        	if (DEBUG) {
        		LLog.e("error!call getDuration() in state " + mState);
        	}
        }
        return duration;
    }

    @Override
    public int getVideoHeight() {
        int height = 0;
        if (mState != STATE_ERROR) {
            try {
                height = mMediaPlayer.getVideoHeight();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        } else {
        	if (DEBUG) {
        		LLog.e("error!call getVideoHeight() in state " + mState);
        	}
        }
        return height;
    }

    @Override
    public int getVideoWidth() {
        int width = 0;
        if (mState != STATE_ERROR) {
            try {
                width = mMediaPlayer.getVideoWidth();
                LLog.d("[DefaultPlayer|getVideoWidth]width="+width);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        } else {
        	if (DEBUG) {
        		LLog.e("error!call getVideoWidth() in state " + mState);
        	}
        }
        return width;
    }

    @Override
    public boolean isPlaying() {
        boolean isPlaying = false;
        if (mState != STATE_ERROR) {
            try {
                isPlaying = mMediaPlayer.isPlaying();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        } else {
        	if (DEBUG) {
        		LLog.e("error!call isPlaying() in state " + mState);
        	}
        }
        return isPlaying;
    }

    @Override
    public void pause() {
        if (mState == STATE_PREPARED || mState == STATE_STARTED
                || mState == STATE_PAUSED) {
            try {
                mMediaPlayer.pause();
                mState = STATE_PAUSED;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        } else {
        	if (DEBUG) {
        		LLog.e("error!call pause() in state " + mState);
        	}
        }
    }

    @Override
    public void prepare() {
        if (mState == STATE_INITIALIZED || mState == STATE_STOPED) {
            try {
                mMediaPlayer.prepare();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mState = STATE_PREPARED;
        } else {
        	if (DEBUG) {
        		LLog.e("error!call prepare() in state " + mState);
        	}
        }
    }

    @Override
    public void prepareAsync() {
        if (mState == STATE_INITIALIZED || mState == STATE_STOPED) {
            try {
                mMediaPlayer.prepareAsync();
                mState = STATE_PREPARING;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        } else {
        	if (DEBUG) {
        		LLog.e("error!call prepareAsync() in state " + mState);
        	}
        }
    }

    @Override
    public void release() {
        try {
        	if (mMediaPlayer != null) {
                mMediaPlayer.release();
                mMediaPlayer = null;
                mState = STATE_END;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reset() {
        LLog.d("call reset() in state " + mState);
        if (mState == STATE_END) {
            mMediaPlayer = new MediaPlayer();
        }
        try {
            mMediaPlayer.reset();
            mState = STATE_IDLE;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void seekTo(int msec) {
        if (mState == STATE_PREPARED || mState == STATE_STARTED
                || mState == STATE_PAUSED || mState == STATE_PLAYBACK_COMPLETE) {
            try {
                mMediaPlayer.seekTo(msec);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        } else {
            LLog.e("error!call seekTo() in state " + mState);
        }
    }
    
    @Override
    public void setDataSource(String path) {
        if (mState == STATE_IDLE) {
            try {
                mMediaPlayer.setDataSource(path);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mState = STATE_INITIALIZED;
        } else {
            LLog.e("error!call setDataSource() in state " + mState);
        }
    }
    
    @Override
    public void setDataSource(FileDescriptor fd) {
        if (mState == STATE_IDLE) {
            try {
            	mMediaPlayer.setDataSource(fd);;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mState = STATE_INITIALIZED;
        } else {
            LLog.e("error!call setDataSource() in state " + mState);
        }
    }

    @Override
    public void setOnBufferingUpdateListener(IMediaPlayer.OnBufferingUpdateListener l) {
        final IMediaPlayer.OnBufferingUpdateListener tempListener = l;

        mOnBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                tempListener.onBufferingUpdate(SystemPlayer.this, percent);
            }
        };
        mMediaPlayer.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
    }

    @Override
    public void setOnCompletionListener(IMediaPlayer.OnCompletionListener l) {
        final IMediaPlayer.OnCompletionListener tempListener = l;

        mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                tempListener.onCompletion(SystemPlayer.this);
                mState = STATE_PLAYBACK_COMPLETE;
            }
        };
        mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
    }

    @Override
    public void setOnFlagErrorListener(IMediaPlayer.OnFlagErrorListener l) {
        final IMediaPlayer.OnFlagErrorListener tempListener = l;

        mOnFlagErrorListener = new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mState = STATE_ERROR;
                return tempListener.onFlagError(SystemPlayer.this, what, extra);
            }
        };
        mMediaPlayer.setOnErrorListener(mOnFlagErrorListener);
    }  
    
	@Override
	public void setOnInfoListener(IMediaPlayer.OnInfoListener l) {		
		final IMediaPlayer.OnInfoListener tempListener = l;
		
		mOnInfoListener = new MediaPlayer.OnInfoListener() {			
			@Override
			public boolean onInfo(MediaPlayer mp, int what, int extra) {
//				mState = STATE_ERROR;
				return tempListener.onInfo(SystemPlayer.this, what, extra);
			}
		};
		mMediaPlayer.setOnInfoListener(mOnInfoListener);
	}
	
    @Override
    public void setOnPreparedListener(IMediaPlayer.OnPreparedListener l) {
        final IMediaPlayer.OnPreparedListener tempListener = l;

        mOnPreparedListener = new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mState = STATE_PREPARED;
                tempListener.onPrepared(SystemPlayer.this);
            }
        };
        mMediaPlayer.setOnPreparedListener(mOnPreparedListener);
    }

    @Override
    public void setOnSeekCompleteListener(IMediaPlayer.OnSeekCompleteListener l) {
        final IMediaPlayer.OnSeekCompleteListener tempListener = l;

        mSeekCompleteListener = new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {
                tempListener.onSeekComplete(SystemPlayer.this);
            }
        };
        mMediaPlayer.setOnSeekCompleteListener(mSeekCompleteListener);
    }

    @Override
    public void setOnVideoSizeChangedListener(IMediaPlayer.OnVideoSizeChangedListener l) {
        final IMediaPlayer.OnVideoSizeChangedListener tempListener = l;

        mVideoSizeChangedListener = new MediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                tempListener.onVideoSizeChanged(SystemPlayer.this, width, height);
            }
        };
        mMediaPlayer.setOnVideoSizeChangedListener(mVideoSizeChangedListener);
    }


    @Override
    public void setScreenOnWhilePlaying(boolean screenOn) {
        try {
            mMediaPlayer.setScreenOnWhilePlaying(screenOn);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        if (mState == STATE_PREPARED || mState == STATE_STARTED
                || mState == STATE_PAUSED || mState == STATE_PLAYBACK_COMPLETE) {
            try {
                mMediaPlayer.start();
                mState = STATE_STARTED;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        } else {
            LLog.e("error!call start() in state " + mState);
        }
    }

    @Override
    public void stop() {
        if (mState == STATE_PREPARING || mState == STATE_PREPARED || mState == STATE_STARTED
                || mState == STATE_PAUSED || mState == STATE_STOPED
                || mState == STATE_PLAYBACK_COMPLETE) {
            try {
                mMediaPlayer.stop();
                mState = STATE_STOPED;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        } else {
            LLog.e("error!call stop() in state " + mState);
        }
    }

    @Override
    public int getCurrentState() {
        return mState;
    }


    @Override
    public void setSurfaceAndPos(Surface surface, int x, int y) {
        LLog.e("no implements function setSurfaceAndPos()");
    }



    @Override
    public int getBufferProgress() {
        LLog.e("no implements function getBufferProgress()");
        return 0;
    }


    @Override
    public void setDisplay(SurfaceHolder holder) {
        mMediaPlayer.setDisplay(holder);
    }


    @Override
    public void setSurfaceType(SurfaceView surface) {
        surface.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }


	@Override
	public void setAudioStreamType(int streamtype) {
		mMediaPlayer.setAudioStreamType(streamtype);
	}


	@Override
	public void setDataSource(Context context, Uri uri) {
		 if (mState == STATE_IDLE) {
			 try {
					mMediaPlayer.setDataSource(context, uri);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 mState = STATE_INITIALIZED;
		 }else {
	            LLog.e("error!call setDataSource() in state " + mState);
	        }
	}




}
