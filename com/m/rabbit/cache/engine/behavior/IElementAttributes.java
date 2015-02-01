/*
 * Copyright (c) 2012 Freeman TV. All rights reserved.
 */
package com.m.rabbit.cache.engine.behavior;

import java.io.Serializable;

/**
 * Interface for cache element attributes classes. Every item is the cache is
 * associated with an element attributes object. It is used to track the life of
 * the object as well as to restrict its behavior.
 * 
 * @author mingrenhan
 */
public interface IElementAttributes extends Serializable {
    /**
     * Sets the maxLife attribute of the IAttributes object.
     * 
     * @param mls The new MaxLifeSeconds value
     */
    public void setMaxLifeSeconds(long mls);

    /**
     * Sets the maxLife attribute of the IAttributes object. How many seconds it
     * can live after creation.
     * <p>
     * If this is exceeded the element will not be returned, instead it will be
     * removed. It will be removed on retrieval, or removed actively if the
     * memory shrinker is turned on.
     * 
     * @return The MaxLifeSeconds value
     */
    public long getMaxLifeSeconds();

    /**
     * Gets the createTime attribute of the IAttributes object.
     * <p>
     * This shoudd be the current time in milliseconds returned by the sysutem
     * call when the element is put in the cache.
     * <p>
     * Putting an item in the cache overrides any existing items.
     * 
     * @return The createTime value
     */
    public long getCreateTime();

    /**
     * Gets the LastAccess attribute of the IAttributes object.
     * 
     * @return The LastAccess value.
     */
    public long getLastAccessTime();

    /**
     * Sets the LastAccessTime as now of the IElementAttributes object
     */
    public void setLastAccessTimeNow();

    /**
     * Gets the time left to live of the IAttributes object.
     * <p>
     * This is the (max life + create time) - current time.
     * 
     * @return The TimeToLiveSeconds value
     */
    public long getTimeToLiveSeconds();

    /**
     * Returns a copy of the object.
     * 
     * @return IElementAttributes
     */
    public IElementAttributes copy();

}
