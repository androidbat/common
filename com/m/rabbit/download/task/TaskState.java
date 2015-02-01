package com.m.rabbit.download.task;

public class TaskState {
    public static final int TASK_STATE_PREPAREED=1;//加入
    public static final int TASK_STATE_RUNNING=2;
    public static final int TASK_STATE_STOPPING=4;
    public static final int TASK_STATE_STOPPED=8;
    public static final int TASK_STATE_CANCELING=16;
    public static final int TASK_STATE_CANCELED=32;
    public static final int TASK_STATE_FINISHED=64;
    public static final int TASK_STATE_EXCEPTION=128;
    
    
}
