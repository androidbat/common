/*
 * Copyright (c) 2012 Freeman TV. All rights reserved.
 */
package com.m.rabbit.cache.engine.behavior;

import java.io.Serializable;

/**
 * This defines the behavior for the Cache Configuration.
 * 
 * @author mingrenhan
 */
public interface ICacheAttributes extends Serializable {

    public void setMaxObjects(int size);

    public int getMaxObjects();

    public void setCacheName(String s);

    public String getCacheName();

    public String getCacheClassName();

    public String setCacheClassName(String s);

    public ICacheAttributes copy();
}
