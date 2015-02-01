/*
 * Copyright (c) 2012 Freeman TV. All rights reserved.
 */
package com.m.rabbit.cache;

import java.io.IOException;
import java.io.Serializable;

import com.m.rabbit.cache.access.behavior.ICacheAccess;
import com.m.rabbit.cache.engine.AbstractCache;
import com.m.rabbit.cache.engine.CacheElement;
import com.m.rabbit.cache.engine.behavior.ICacheAttributes;
import com.m.rabbit.cache.engine.behavior.ICacheElement;
import com.m.rabbit.cache.engine.behavior.IElementAttributes;
import com.m.rabbit.cache.engine.control.CacheManager;


/**
 * This class provides an interface for all types of access to the cache.
 * 
 * @author mingrenhan
 * 2012-10-12
 */
public class CacheAccess implements ICacheAccess {

    /**
     * Cache manager
     */
    private static CacheManager cacheMgr;

    /**
     * The cache that a given instance of this class provides access to.
     */
    protected AbstractCache cache;

    /**
     * Constructor for the CacheAccess object.
     * <p>
     * 
     * @param cache The cache which the created instance accesses
     */
    protected CacheAccess(AbstractCache cache) {
        this.cache = cache;
    }

    public static CacheAccess getInstance(String region) throws Exception {
        ensureCacheManager();

        return new CacheAccess(cacheMgr.getCache(region));
    }

    public static CacheAccess getInstance(String region, ICacheAttributes cattr) throws Exception {
        ensureCacheManager();

        return new CacheAccess(cacheMgr.getCache(region, cattr));
    }

    public static CacheAccess getInstance(String region, ICacheAttributes cattr, IElementAttributes attr)
            throws Exception {
        ensureCacheManager();

        return new CacheAccess(cacheMgr.getCache(region, cattr, attr));
    }

    protected static void ensureCacheManager() {
        synchronized (CacheAccess.class) {
            if (cacheMgr == null) {
                cacheMgr = CacheManager.getInstance();
            }
        }
    }

    public Object get(Object name) {
        ICacheElement element = cache.get((Serializable) name);

        return (element != null) ? element.getVal() : null;
    }

    public ICacheElement getCacheElement(Object name) {
        return cache.get((Serializable) name);
    }

    public void putSafe(Object key, Object value) throws Exception {
        if (this.cache.get((Serializable) key) != null) {
            throw new Exception("putSafe failed.  Object exists in the cache for key [" + key
                    + "].  Remove first or use a non-safe put to override the value.");
        }
        put(key, value);
    }
    
    public void put(Object name, Object obj) throws Exception {
        // Call put with a copy of the contained caches default attributes.
        // the attributes are copied by the cache
        put(name, obj, cache.getElementAttributes());
    }

    public void put(Object key, Object val, IElementAttributes attr) throws Exception {
        if (key == null) {
            throw new Exception("Key must not be null");
        } else if (val == null) {
            throw new Exception("Value must not be null");
        }

        try {
            CacheElement ce = new CacheElement(cache.getCacheName(), (Serializable) key, (Serializable) val);

            ce.setElementAttributes(attr);

            cache.update(ce);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public void clear() throws Exception {
        try {
            cache.removeAll();
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    public void remove(Object name) throws Exception {
        cache.remove((Serializable) name);
    }

    public void setDefaultElementAttributes(IElementAttributes attr) throws Exception {
        cache.setElementAttributes(attr);
    }

    public void resetElementAttributes(Object name, IElementAttributes attr) throws Exception, Exception {
        ICacheElement element = cache.get((Serializable) name);
        if (element == null) {
            throw new Exception("Object for name [" + name + "] is not in the cache");
        }

        put(element.getKey(), element.getVal(), attr);

    }

    public IElementAttributes getDefaultElementAttributes() throws Exception {
        return cache.getElementAttributes();
    }

    public void dispose() {
        cache.dispose();
    }

    public ICacheAttributes getCacheAttributes() {
        return cache.getCacheAttributes();
    }

    public void setCacheAttributes(ICacheAttributes cattr) {
        cache.setCacheAttributes(cattr);
    }

}
