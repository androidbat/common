/*
 * Copyright (c) 2012 Freeman TV. All rights reserved.
 */
package com.m.rabbit.cache.engine;

import com.m.rabbit.cache.engine.behavior.ICacheAttributes;

/**
 * @author mingrenhan
 */
public abstract class AbstractCacheAttributes implements ICacheAttributes, Cloneable {
    private static final long serialVersionUID = 6754049978134196787L;

    private static final int DEFAULT_MAX_OBJECTS = 100;

    /** The name of this cache. */
    private String cacheName = "defaultCache";

    /** The class name of this cache. */
    private String cacheClassName = "com.joysee.publiccode.cache.diskcache.createtime.CreateTimeDiskCache";

    private int maxObjs = DEFAULT_MAX_OBJECTS;

    /**
     * Sets the maxObjects attribute of the object
     * <p>
     * 
     * @param maxObjs The new maxObjects value
     */
    public void setMaxObjects(int maxObjs) {
        this.maxObjs = maxObjs;
    }

    /**
     * Gets the maxObjects attribute of the object
     * <p>
     * 
     * @return The maxObjects value
     */
    public int getMaxObjects() {
        return this.maxObjs;
    }

    /**
     * Sets the cacheName attribute of the object
     * <p>
     * 
     * @param s The new cacheName value
     */
    public void setCacheName(String s) {
        this.cacheName = s;
    }

    /**
     * Gets the cacheName attribute of the object
     * <p>
     * 
     * @return The cacheName value
     */
    public String getCacheName() {
        return this.cacheName;
    }

    /**
     * Dumps the core attributes.
     * <p>
     * 
     * @return For debugging.
     */
    public String toString() {
        StringBuffer dump = new StringBuffer();
        dump.append("[ ");
        dump.append("cacheName = ").append(cacheName);
        dump.append("cacheClassName = ").append(cacheClassName);
        dump.append(", maxObjs = ").append(maxObjs);
        dump.append(" ]");
        return dump.toString();
    }

    @Override
    public String getCacheClassName() {
        return cacheClassName;
    }

    @Override
    public String setCacheClassName(String s) {
        return cacheClassName = s;
    }
}
