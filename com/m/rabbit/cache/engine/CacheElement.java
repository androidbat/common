/*
 * Copyright (c) 2012 Freeman TV. All rights reserved.
 */
package com.m.rabbit.cache.engine;

import java.io.Serializable;

import com.m.rabbit.cache.engine.behavior.ICacheElement;
import com.m.rabbit.cache.engine.behavior.IElementAttributes;


/**
 * Generic element wrapper. Often stuffed inside another.
 * 
 * @author mingrenhan
 */
public class CacheElement implements ICacheElement, Serializable {
    private static final long serialVersionUID = -6062305728297627263L;

    /** The name of the cache region. This is a namespace. */
    public final String cacheName;

    /** This is the cache key by which the value can be referenced. */
    public final Serializable key;

    /** This is the cached value, reference by the key. */
    public final Serializable val;

    /**
     * These attributes hold information about the element and what it is
     * allowed to do.
     */
    public IElementAttributes attr;

    /**
     * Constructor for the CacheElement object
     * <p>
     * 
     * @param cacheName
     * @param key
     * @param val
     */
    public CacheElement(String cacheName, Serializable key, Serializable val) {
        this.cacheName = cacheName;
        this.key = key;
        this.val = val;
    }

    /**
     * Constructor for the CacheElement object
     * <p>
     * 
     * @param cacheName
     * @param key
     * @param val
     * @param attrArg
     */
    public CacheElement(String cacheName, Serializable key, Serializable val, IElementAttributes attrArg) {
        this.cacheName = cacheName;
        this.key = key;
        this.val = val;
        this.attr = attrArg;
    }

    /**
     * Constructor for the CacheElement object
     * <p>
     * 
     * @param cacheName
     * @param key
     * @param val
     */
    public CacheElement(String cacheName, Serializable key, Object val) {
        this(cacheName, key, (Serializable) val);
    }

    /**
     * Gets the cacheName attribute of the CacheElement object
     * <p>
     * 
     * @return The cacheName value
     */
    public String getCacheName() {
        return this.cacheName;
    }

    /**
     * Gets the key attribute of the CacheElement object
     * <p>
     * 
     * @return The key value
     */
    public Serializable getKey() {
        return this.key;
    }

    /**
     * Gets the val attribute of the CacheElement object
     * <p>
     * 
     * @return The val value
     */
    public Serializable getVal() {
        return this.val;
    }

    /**
     * Sets the attributes attribute of the CacheElement object
     * <p>
     * 
     * @param attr The new IElementAttributes value
     */
    public void setElementAttributes(IElementAttributes attr) {
        this.attr = (ElementAttributes) attr;
    }

    /**
     * Gets the IElementAttributes attribute of the CacheElement object
     * <p>
     * 
     * @return The IElementAttributes value, never null
     */
    public IElementAttributes getElementAttributes() {
        // create default attributes if they are null
        // this shouldn't happen, but could if a corrupt
        // object was sent over the wire.
        if (this.attr == null) {
            this.attr = new ElementAttributes();
        }
        return this.attr;
    }

    /**
     * @return a hash of the key only
     */
    public int hashCode() {
        return key.hashCode();
    }

    public boolean equals(Object obj) {
        return hashCode() == obj.hashCode() ? true : false;
    }

    public String toString() {
        return "[CacheElement: cacheName [" + cacheName + "], key [" + key + "], val [" + val + "], attr [" + attr
                + "]";
    }

    @Override
    public boolean isExpired() {
        try {

            long now = System.currentTimeMillis();

            long maxLifeSeconds = attr.getMaxLifeSeconds();
            long createTime = attr.getCreateTime();

            if (maxLifeSeconds != -1 && (now - createTime) > (maxLifeSeconds * 1000)) {
                return true;
            }
        } catch (Exception e) {
            return true;
        }
        return false;
    }

}
