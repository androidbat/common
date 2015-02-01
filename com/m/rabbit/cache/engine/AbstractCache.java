/*
 * Copyright (c) 2012 Freeman TV. All rights reserved.
 */
package com.m.rabbit.cache.engine;

import java.io.Serializable;

import com.m.rabbit.cache.engine.behavior.ICache;
import com.m.rabbit.cache.engine.behavior.ICacheElement;


/***
 * @author mingrenhan
 */
public abstract class AbstractCache implements ICache, Serializable {
    private static final long serialVersionUID = -2838097410378294960L;

    protected boolean alive = true;

    private int removeCount;

    /** cache hit count */
    private int hitCount;

    /** Count of misses where element was not found. */
    private int missCountNotFound = 0;

    /** Count of misses where element was expired. */
    private int missCountExpired = 0;

    /**
     * Constructor for the Cache object,the only one constructor!!!
     * <p>
     * 
     * @param cacheName The name of the region
     * @param cattr The cache attribute
     * @param attr The default element attributes
     */
    public AbstractCache() {
    }

    public abstract ICacheElement get(Serializable key);

    public abstract void dispose();

    /**
     * Gets the status attribute of the Cache object.
     * <p>
     * 
     * @return The status value
     */
    public int getStatus() {
        return alive ? CacheConstants.STATUS_ALIVE : CacheConstants.STATUS_DISPOSED;
    }

    public int getHitCountAux() {
        return hitCount;
    }

    /**
     * Number of times a requested element was not found.
     * 
     * @return number of misses.
     */
    public int getMissCountNotFound() {
        return missCountNotFound;
    }

    /**
     * Number of times a requested element was found but was expired.
     * 
     * @return number of found but expired gets.
     */
    public int getMissCountExpired() {
        return missCountExpired;
    }

    /**
     * @return Returns the removeCount.
     */
    public int getRemoveCount() {
        return removeCount;
    }
}
