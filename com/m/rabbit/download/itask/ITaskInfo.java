package com.m.rabbit.download.itask;

public interface ITaskInfo {

    /**
     * 返回该TaskInfo的唯一身份标志，一般用url
     * @author mingrenhan
     * 2012-7-2
     * @return
     */
	public String getKey();
	/**
	 * 返回TaskInfo所对应的task的类名（也就是真正去执行的类名）
	 * @author mingrenhan
	 * 2012-7-2
	 * @return
	 */
	public String getTaskClassName();
}
