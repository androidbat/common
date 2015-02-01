/*
 * Copyright (c) 2012 Freeman TV. All rights reserved.
 */
package com.m.rabbit.cache.access.behavior;

import com.m.rabbit.cache.engine.behavior.ICacheAttributes;
import com.m.rabbit.cache.engine.behavior.IElementAttributes;

/**
 * ICacheAccess defines the behavior for client access.
 * 
 * @author mingrenhan
 */
public interface ICacheAccess {
    /**
     * Basic get method.
     * <p>
     * 
     * @param name
     * @return Object or null if not found.
     */
    Object get(Object name);

    /**
     * Puts in cache if an item does not exist with the name in that region.
     * <p>
     * 
     * @param name
     * @param obj
     * @throws Exception
     */
    void putSafe(Object name, Object obj) throws Exception;

    /**
     * Puts and/or overrides an element with the name in that region.
     * <p>
     * 
     * @param name
     * @param obj
     * @throws Exception
     */
    void put(Object name, Object obj) throws Exception;

    /**
     * Description of the Method
     * <p>
     * 
     * @param name
     * @param obj
     * @param attr
     * @throws Exception
     */
    void put(Object name, Object obj, IElementAttributes attr) throws Exception;

    /**
     * Old remove all method.
     * 
     * @throws Exception
     */
    void clear() throws Exception;

    /**
     * Remove an object for this key if one exists, else do nothing.
     * <p>
     * 
     * @param name
     * @throws Exception
     */
    void remove(Object name) throws Exception;

    void setDefaultElementAttributes(IElementAttributes attr) throws Exception;

    void resetElementAttributes(Object name, IElementAttributes attr) throws Exception;

    public IElementAttributes getDefaultElementAttributes() throws Exception;

    public ICacheAttributes getCacheAttributes();

    public void setCacheAttributes(ICacheAttributes cattr);

}
