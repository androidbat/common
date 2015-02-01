/*
 * Copyright (c) 2012 Freeman TV. All rights reserved.
 */
package com.m.rabbit.cache.diskcache;

import com.m.rabbit.cache.diskcache.behavior.IDiskCacheAttributes;
import com.m.rabbit.cache.engine.AbstractCacheAttributes;

@SuppressWarnings("serial")
public abstract class AbstractDiskCacheAttributes extends AbstractCacheAttributes implements IDiskCacheAttributes {

    public static final int MAX_MAP_SIZE_DEFUALT = 50;

    /** path to disk */
    protected String diskPath;

    /** default to 50 */
    protected int maxMapSize = MAX_MAP_SIZE_DEFUALT;

    /**
     * Sets the diskPath attribute of the IJISPCacheAttributes object
     * <p>
     * 
     * @param path The new diskPath value
     */
    public void setDiskPath(String path) {
        this.diskPath = path.trim();
    }

    /**
     * Gets the diskPath attribute of the attributes object
     * <p>
     * 
     * @return The diskPath value
     */
    public String getDiskPath() {
        return this.diskPath;
    }

    /**
     * Gets the maxMapSize attribute of the DiskCacheAttributes object
     */
    public int getMaxMapSize() {
        return maxMapSize;
    }

    /**
     * Sets the maxMapSize attribute of the DiskCacheAttributes object
     */
    public void setMaxMapSize(int maxMapSize) {
        this.maxMapSize = maxMapSize;
    }
}
