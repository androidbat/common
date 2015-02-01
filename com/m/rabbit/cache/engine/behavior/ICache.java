/*
 * Copyright (c) 2012 Freeman TV. All rights reserved.
 */
package com.m.rabbit.cache.engine.behavior;

import java.io.IOException;
import java.io.Serializable;

/**
 * This is the top level interface for all cache like structures. It defines the
 * methods used internally by JCS to access, modify, and instrument such
 * structures.
 * 
 * @author mingrenhan
 */
public interface ICache extends Serializable {
    /***
     * Initialize the cache
     */
    public void init();

    /**
     * Puts an item to the cache.
     * <p>
     * 
     * @param ce
     * @throws IOException
     */
    public void update(ICacheElement ce) throws IOException;

    /**
     * Gets an item from the cache.
     * <p>
     * 
     * @param key
     * @return
     * @throws IOException
     */
    public ICacheElement get(Serializable key) throws IOException;

    /**
     * Removes an item from the cache.
     * <p>
     * 
     * @param key
     * @return
     * @throws IOException
     */
    public boolean remove(Serializable key) throws IOException;

    /**
     * Removes all cached items from the cache.
     * <p>
     * 
     * @throws IOException
     */
    public void removeAll() throws IOException;

    public void dispose() throws IOException;

    public int getSize();

    public int getStatus();

    public String getCacheName();

    public ICacheAttributes getCacheAttributes();

    public void setCacheAttributes(ICacheAttributes cattr);

    public IElementAttributes getElementAttributes();

    public void setElementAttributes(IElementAttributes attr);

}
