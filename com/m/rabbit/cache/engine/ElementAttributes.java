/*
 * Copyright (c) 2012 Freeman TV. All rights reserved.
 */
package com.m.rabbit.cache.engine;

import java.io.Serializable;

import com.m.rabbit.cache.engine.behavior.IElementAttributes;


/**
 * This it the element attribute descriptor class. Each element in the cache has
 * an ElementAttribute object associated with it.
 * 
 * @author mingrenhan
 */
public class ElementAttributes implements IElementAttributes, Serializable, Cloneable {
    private static final long serialVersionUID = 7814990748035017441L;

    /**
     * Max life seconds
     */
    public long maxLifeSeconds = -1;

    /**
     * The creation time. This is used to enforce the max life.
     */
    public long createTime = 0;

    /**
     * The last access time.
     */
    public long lastAccessTime = 0;

    /**
     * Constructor for the IElementAttributes object
     */
    public ElementAttributes() {
        this.createTime = System.currentTimeMillis();
        this.lastAccessTime = this.createTime;
    }

    /**
     * Copies the attributes, including references to event handlers.
     * <p>
     * 
     * @return a copy of the Attributes
     */
    public IElementAttributes copy() {
        try {
            ElementAttributes attr = new ElementAttributes();
            attr.setMaxLifeSeconds(this.getMaxLifeSeconds());
            return attr;
        } catch (Exception e) {
            return new ElementAttributes();
        }
    }

    public void setMaxLifeSeconds(long mls) {
        this.maxLifeSeconds = mls;
    }

    public long getMaxLifeSeconds() {
        return this.maxLifeSeconds;
    }

    public long getCreateTime() {
        return createTime;
    }

    /**
     * Sets the createTime attribute of the IElementAttributes object
     */
    public void setCreateTime() {
        createTime = System.currentTimeMillis();
    }

    public long getTimeToLiveSeconds() {
        long now = System.currentTimeMillis();
        return ((this.getCreateTime() + (this.getMaxLifeSeconds() * 1000)) - now) / 1000;
    }

    public long getLastAccessTime() {
        return this.lastAccessTime;
    }

    public void setLastAccessTimeNow() {
        this.lastAccessTime = System.currentTimeMillis();
    }

    public String toString() {
        StringBuffer dump = new StringBuffer();
        dump.append(", MaxLifeSeconds = ").append(this.getMaxLifeSeconds());
        dump.append(", CreateTime = ").append(this.getCreateTime());
        dump.append(", LastAccessTime = ").append(this.getLastAccessTime());
        dump.append(", getTimeToLiveSeconds() = ").append(String.valueOf(getTimeToLiveSeconds()));
        dump.append(", createTime = ").append(String.valueOf(createTime)).append(" ]");
        return dump.toString();
    }
}
