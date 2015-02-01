package com.m.rabbit.play;

import java.io.FileDescriptor;

import android.content.Context;
import android.net.Uri;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public interface IMediaPlayer {
    
    public static final int STATE_UNDEF = -1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_INITIALIZED = 1;
    public static final int STATE_PREPARING = 2;
    public static final int STATE_PREPARED = 3;
    public static final int STATE_STARTED = 4;
    public static final int STATE_PAUSED = 5;
    public static final int STATE_STOPED = 6;
    public static final int STATE_PLAYBACK_COMPLETE = 7;
    public static final int STATE_END = 8;
    public static final int STATE_ERROR = 9;
    
    /**
    * @Title: getCurrentState
    * @Description: return current state of mediaplayer
    * @param: none
    * @return: int
    * @Comment: 
    */
    public int getCurrentState();

    /**
    * @Title: getCurrentPosition
    * @Description: get current position of playing video
    * @param: none
    * @return: int
    * @Comment: 
    */
    public int getCurrentPosition();
    
    /**
    * @Title: getDuration
    * @Description: get video length by ms
    * @param: none
    * @return: int
    * @Comment: 
    */
    public int getDuration();
    
    /**
    * @Title: getVideoHeight
    * @Description: get video heigth
    * @param: none
    * @return: int
    * @Comment: 
    */
    public int getVideoHeight();
    
    /**
    * @Title: getVideoWidth
    * @Description: get video width
    * @param: none
    * @return: int
    * @Comment: 
    */
    public int getVideoWidth();
    
    /**
    * @Title: isPlaying
    * @Description: If now is in playback state return true,esle return false.
    * @param: none
    * @return: boolean
    * @Comment: 
    */
    public boolean isPlaying();
    
    /**
    * @Title: pause
    * @Description: pause play
    * @param: 
    * @return: void
    * @Comment: 
    */
    public void pause();
    
    /**
    * @Title: prepare
    * @Description: Prepares the player for playback, synchronously.
    * @param: 
    * @return: void
    * @Comment: 
     * After setting the datasource and the display surface, you need to either
     * call prepare() or prepareAsync(). For files, it is OK to call prepare(),
     * which blocks until MediaPlayer is ready for playback.
    */
    public void prepare();
    
    /**
    * @Title: prepareAsync
    * @Description: Prepares the player for playback, asynchronously.
    * @param: 
    * @return: void
    * @Comment: 
     * After setting the datasource and the display surface, you need to either
     * call prepare() or prepareAsync(). For streams, you should call prepareAsync(),
     * which returns immediately, rather than blocking until enough data has been
     * buffered.
    */
    public void prepareAsync();
    
    /**
    * @Title: release
    * @Description: release media resource
    * @param: 
    * @return: void
    * @Comment: 
     * Releases resources associated with this MediaPlayer object.
     * It is considered good practice to call this method when you're
     * done using the MediaPlayer.
    */
    public void release();
    
    /**
    * @Title: reset
    * @Description: reset mediaplayer.
    * @param: 
    * @return: void
    * @Comment: 
     * Resets the MediaPlayer to its uninitialized state. After calling
     * this method, you will have to initialize it again by setting the
     * data source and calling prepare().
    */
    public void reset();
    
    /**
    * @Title: seekTo
    * @Description: Seeks to specified time position.
    * @param: msec the offset in milliseconds from the start to seek to
    * @return: void
    * @Comment: 
    * 
    */
    public void seekTo(int msec);
    
    /**
    * @Title: setDataSource
    * @Description: Sets the data source (file-path or http/rtsp URL) to use.
    * @param: path the path of the file, or the http/rtsp URL of the stream you want to play
    * @return: void
    * @Comment: 
    */
    public void setDataSource(String path);
    
    public void setDataSource(Context context,Uri uri);
    
    public void setDataSource(FileDescriptor fd);
    
    /**
    * @Title: setOnBufferingUpdateListener
    * @Description: Register a callback to be invoked when the status of a network
    *               stream's buffer has changed.
    * @param: l the callback that will be run.
    * @return: void
    * @Comment: 
    */
    public void setOnBufferingUpdateListener(OnBufferingUpdateListener l);
    
    /**
    * @Title: setOnCompletionListener
    * @Description: Interface definition for a callback to be invoked when playback of
    *               a media source has completed.
    * @param: l the callback that will be run.
    * @return: void
    * @Comment: 
    */
    public void setOnCompletionListener(IMediaPlayer.OnCompletionListener l);
    
    /**
    * @Title: setOnFlagErrorListener
    * @Description: Register a callback to be invoked when an error has happened
    *               during an asynchronous operation.
    * @param: l the callback that will be run.
    * @return: void
    * @Comment: 
    */
    public void setOnFlagErrorListener(IMediaPlayer.OnFlagErrorListener l);
    
    public void setOnInfoListener(IMediaPlayer.OnInfoListener l);
    /**
    * @Title: setOnPreparedListener
    * @Description: Register a callback to be invoked when the media source is ready
    *               for playback.
    * @param: l the callback that will be run.
    * @return: void
    * @Comment: 
    */
    public void setOnPreparedListener(IMediaPlayer.OnPreparedListener l);
    
    /**
    * @Title: setOnSeekCompleteListener
    * @Description: Register a callback to be invoked when a seek operation has been
    *               completed.
    * @param: l the callback that will be run.
    * @return: void
    * @Comment: 
    */
    public void setOnSeekCompleteListener(IMediaPlayer.OnSeekCompleteListener l);
    
    /**
    * @Title: setOnVideoSizeChangedListener
    * @Description: Register a callback to be invoked when the video size is
    *               known or updated.
    * @param: l the callback that will be run.
    * @return: void
    * @Comment: 
    */
    public void setOnVideoSizeChangedListener(IMediaPlayer.OnVideoSizeChangedListener l);
    
    /**
    * @Title: setScreenOnWhilePlaying
    * @Description: 
    * @param: @param screenOn
    * @return: void
    * @Comment: 
    */
    public void setScreenOnWhilePlaying(boolean screenOn);
    
    /**
    * @Title: start
    * @Description: Starts or resumes playback.
    * @param: 
    * @return: void
    * @Comment:
     * If playback had previously been paused,
     * playback will continue from where it was paused. If playback had
     * been stopped, or never started before, playback will start at the
     * beginning. 
    */
    public void start();
    
    /**
    * @Title: stop
    * @Description: Stops playback after playback has been stopped or paused.
    * @param: 
    * @return: void
    * @Comment: 
    */
    public void stop();

    /**
     * Interface definition of a callback to be invoked indicating buffering
     * status of a media resource being streamed over the network.
     */
    public interface OnBufferingUpdateListener {
        /**
         * Called to update status in buffering a media stream.
         *
         * @param mp      the MediaPlayer the update pertains to
         * @param percent the percentage (0-100) of the buffer
         *                that has been filled thus far
         */
        void onBufferingUpdate(IMediaPlayer mp, int percent);
    }
    
    /**
     * Interface definition for a callback to be invoked when playback of
     * a media source has completed.
     */
    public interface OnCompletionListener {
        /**
         * Called when the end of a media source is reached during playback.
         *
         * @param mp the MediaPlayer that reached the end of the file
         */
        void onCompletion(IMediaPlayer mp);
    }

    /**
     * Interface definition for a callback to be invoked when the media
     * source is ready for playback.
     */
    public interface OnPreparedListener {
        /**
         * Called when the media file is ready for playback.
         *
         * @param mp the MediaPlayer that is ready for playback
         */
        void onPrepared(IMediaPlayer mp);
    }
    
    
    /**
     * Interface definition of a callback to be invoked indicating
     * the completion of a seek operation.
     */
    public interface OnSeekCompleteListener {
        /**
         * Called to indicate the completion of a seek operation.
         *
         * @param mp the MediaPlayer that issued the seek operation
         */
        public void onSeekComplete(IMediaPlayer mp);
    }
    
    
    /**
     * Interface definition of a callback to be invoked when the
     * video size is first known or updated
     */
    public interface OnVideoSizeChangedListener {
        /**
         * Called to indicate the video size
         *
         * @param mp        the MediaPlayer associated with this callback
         * @param width     the width of the video
         * @param height    the height of the video
         */
        public void onVideoSizeChanged(IMediaPlayer mp, int width, int height);
    }
    
    
    /**
     * Interface definition of a callback to be invoked when there
     * has been an error during an asynchronous operation (other errors
     * will throw exceptions at method call time).
     */
    public interface OnFlagErrorListener {
        /**
         * Called to indicate an error.
         *
         * @param mp      the MediaPlayer the error pertains to
         * @param what    the type of error that has occurred:
         * <ul>
         * <li>{@link #MEDIA_ERROR_UNKNOWN}
         * <li>{@link #MEDIA_ERROR_SERVER_DIED}
         * </ul>
         * @param extra an extra code, specific to the error. Typically
         * implementation dependant.
         * @return True if the method handled the error, false if it didn't.
         * Returning false, or not having an OnFlagErrorListener at all, will
         * cause the OnCompletionListener to be called.
         */
        boolean onFlagError(IMediaPlayer mp, int what, int extra);
    }

    public interface OnInfoListener {
        /**
         * info
         */
        boolean onInfo(IMediaPlayer mp, int what, int extra);
    }

    /**
    * @Title: setSurfaceAndPos
    * @Description: set video display position that on surface.
    * @param: @param surface
    * @param: @param x
    * @param: @param y
    * @return: void
    * @Comment: 
    */
    public void setSurfaceAndPos(Surface surface, int x, int y);
    
    public void setDisplay(SurfaceHolder holder);
    
    public int getBufferProgress();
    
    public void setSurfaceType(SurfaceView surface);
    
    public void setAudioStreamType(int  streamtype);
    public  IMediaPlayer CreateMediaPlayer(Context context,Uri uri);
}
