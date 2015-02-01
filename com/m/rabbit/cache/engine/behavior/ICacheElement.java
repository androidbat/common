/*
 * Copyright (c) 2012 Freeman TV. All rights reserved.
 */
package com.m.rabbit.cache.engine.behavior;

import java.io.Serializable;

import com.m.rabbit.cache.engine.behavior.IElementAttributes;


/**
 * Every item is the cache is wrapped in an ICacheElement. This contains
 * information about the element: the region name, the key, the value, and the
 * element attributes.
 * 
 * @author mingrenhan
 */
public interface ICacheElement extends Serializable {

    /**
     * Gets the cacheName attribute of the ICacheElement object. The cacheName
     * is also known as the region name.
     * 
     * @return The cacheName value
     */
    public String getCacheName();

    /**
     * Gets the key attribute of the ICacheElement object
     * 
     * @return The key value
     */
    public Serializable getKey();

    /**
     * Gets the val attribute of the ICacheElement object
     * 
     * @return The val value
     */
    public Serializable getVal();

    /**
     * Gets the attributes attribute of the ICacheElement object
     * 
     * @return The attributes value
     */
    public IElementAttributes getElementAttributes();

    /**
     * Sets the attributes attribute of the ICacheElement object
     * 
     * @param attr The new attributes value
     */
    public void setElementAttributes(IElementAttributes attr);

    public boolean isExpired();
}
