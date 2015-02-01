package com.m.rabbit.net.ihttp;

import com.m.rabbit.net.http.IHttpTask;


public interface HttpHandlerStateListener {
    public static final int SUCCESS=0;
    public static final int SOCKET_TIMEOUT_EXCEPTION=1;
    public static final int OUTOF_MEMORY_ERROR=2;
    public static final int IO_EXCEPTION=3;
    public static final int DATA_NULL=4;
    public void setHttpResponseState(IHttpTask http, int state);
}
