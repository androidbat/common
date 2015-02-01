/*
 * Copyright (c) 2012 Freeman TV. All rights reserved.
 */
package com.m.rabbit.cache.engine.control;

import java.io.Serializable;
import java.util.Hashtable;

import com.m.rabbit.cache.diskcache.createtime.CreateTimeDiskCacheAttributes;
import com.m.rabbit.cache.engine.AbstractCache;
import com.m.rabbit.cache.engine.ElementAttributes;
import com.m.rabbit.cache.engine.behavior.ICacheAttributes;
import com.m.rabbit.cache.engine.behavior.ICacheManager;
import com.m.rabbit.cache.engine.behavior.IElementAttributes;


/***
 * @author mingrenhan
 */
@SuppressWarnings("unchecked")
public class CacheManager implements Serializable, ICacheManager {

    private static final long serialVersionUID = 6031406663558557125L;

    /** Caches managed by this cache manager */

    @SuppressWarnings("rawtypes")
    protected Hashtable caches = new Hashtable();

    /** Default cache attributes for this cache manager */
    protected ICacheAttributes defaultCacheAttr = new CreateTimeDiskCacheAttributes();

    /** Default element attributes for this cache manager */
    protected IElementAttributes defaultElementAttr = new ElementAttributes();

    /** The Singleton Instance */
    protected static CacheManager instance;

    public static CacheManager getInstance() {
        if (instance == null) {
            synchronized (CacheManager.class) {
                if (instance == null) {
                    instance = new CacheManager();
                }
            }
        }
        return instance;
    }

    @Override
    public AbstractCache getCache(String cacheName) {
        return getCache(cacheName, this.defaultCacheAttr.copy());
    }

    public AbstractCache getCache(String cacheName, ICacheAttributes cattr) {
        cattr.setCacheName(cacheName);
        return getCache(cattr, this.defaultElementAttr.copy());
    }

    public AbstractCache getCache(String cacheName, ICacheAttributes cattr, IElementAttributes attr) {
        cattr.setCacheName(cacheName);
        return getCache(cattr, attr);
    }

    /**
     * If the cache has already been created, then the CacheAttributes and the
     * element Attributes will be ignored. Currently there is no overriding the
     * CacheAttributes once it is set up. You can change the default
     * ElementAttributes for a cache later.
     * 
     * @param cattr
     * @param attr
     * @return CompositeCache
     */
    @SuppressWarnings("rawtypes")
    private AbstractCache getCache(ICacheAttributes cattr, IElementAttributes attr) {
        AbstractCache cache;
        cache = (AbstractCache) caches.get(cattr.getCacheName());
        if (cache == null) {
            synchronized (caches) {
                cache = (AbstractCache) caches.get(cattr.getCacheName());
                if (cache != null) {
                    return cache;
                }
                Class c = null;
                try {
                    c = Class.forName(cattr.getCacheClassName());
                    cache = (AbstractCache) c.newInstance();
                    cache.setCacheAttributes(cattr);
                    cache.setElementAttributes(attr);
                    cache.init();
                } catch (ClassNotFoundException e) {
                } catch (InstantiationException e) {
                } catch (IllegalAccessException e) {
                }

                caches.put(cattr.getCacheName(), cache);
            }

        } else {
            return cache;
        }

        return cache;
    }

    public ICacheAttributes getDefaultCacheAttributes() {
        return this.defaultCacheAttr.copy();
    }

    public void setDefaultCacheAttributes(ICacheAttributes icca) {
        this.defaultCacheAttr = icca;
    }

    public void setDefaultElementAttributes(IElementAttributes iea) {
        this.defaultElementAttr = iea;
    }

    public IElementAttributes getDefaultElementAttributes() {
        return this.defaultElementAttr.copy();
    }

}
