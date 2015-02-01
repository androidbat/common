/*
 * Copyright (c) 2012 Freeman TV. All rights reserved.
 */
package com.m.rabbit.cache.diskcache.behavior;

public interface IDiskCacheAttributes{

    /**
     * Sets the diskPath attribute of the IJISPCacheAttributes object
     * <p>
     * @param path
     *            The new diskPath value
     */
    public void setDiskPath( String path );

    /**
     * Gets the diskPath attribute of the attributes object
     * <p>
     * @return The diskPath value
     */
    public String getDiskPath();
    
    /**
     * Gets the maxKeySize attribute of the DiskCacheAttributes object
     * <p>
     * @return The maxMapSize value
     */
    public int getMaxMapSize();

    /**
     * Sets the maxMapSize attribute of the DiskCacheAttributes object
     * <p>
     * @param maxPurgatorySize
     *            The new maxMapSize value
     */
    public void setMaxMapSize( int maxMapSize );
}
