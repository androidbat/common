package com.m.rabbit.net.ihttp;

import com.m.rabbit.net.http.IHttpTask;


public interface HttpConnectionListener {

    public void downloadEnd(IHttpTask http, Object data);
}
