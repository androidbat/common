package com.m.rabbit.download.itask;

public interface ITaskStateChangeListener {
    public void OnTaskStateChanged(int taskState);
    public void onProgressUpdate(long l);
}
