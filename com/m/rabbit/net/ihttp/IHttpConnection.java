package com.m.rabbit.net.ihttp;

public interface IHttpConnection {
    
    public void setRequestUrl(String requestUrl);
    public void setRequestType(int requestType);
    public void setRequestMethod(int requestMethod);
    public void execute();
    public boolean cancel(boolean mayInterruptIfRunning);

}
