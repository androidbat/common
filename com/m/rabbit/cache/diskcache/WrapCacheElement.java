/*
 * Copyright (c) 2012 Freeman TV. All rights reserved.
 */
package com.m.rabbit.cache.diskcache;

import java.io.Serializable;

import com.m.rabbit.cache.engine.behavior.ICacheElement;
import com.m.rabbit.cache.engine.behavior.IElementAttributes;


public class WrapCacheElement implements ICacheElement {

    private static final long serialVersionUID = -564260336101474527L;

    /** Wrapped cache Element */
    protected ICacheElement cacheElement;

    /**
     * Constructor
     * <p>
     * 
     * @param cacheElement CacheElement to wrap.
     */
    public WrapCacheElement(ICacheElement cacheElement) {
        this.cacheElement = cacheElement;
    }

    @Override
    public String getCacheName() {
        return cacheElement.getCacheName();
    }

    @Override
    public IElementAttributes getElementAttributes() {
        return cacheElement.getElementAttributes();
    }

    @Override
    public Serializable getKey() {
        return cacheElement.getKey();
    }

    @Override
    public Serializable getVal() {
        return cacheElement.getVal();
    }

    @Override
    public boolean isExpired() {
        return cacheElement.isExpired();
    }

    @Override
    public void setElementAttributes(IElementAttributes attr) {
        cacheElement.setElementAttributes(attr);

    }

    /**
     * Get the wrapped cache element.
     * <p>
     * 
     * @return ICacheElement
     */
    public ICacheElement getCacheElement() {
        return cacheElement;
    }

}
