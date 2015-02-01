/*
 * Copyright (c) 2012 Freeman TV. All rights reserved.
 */
package com.m.rabbit.cache.diskcache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.m.rabbit.cache.diskcache.behavior.IDiskCacheAttributes;
import com.m.rabbit.cache.engine.AbstractCache;
import com.m.rabbit.cache.engine.CacheConstants;
import com.m.rabbit.cache.engine.behavior.ICacheAttributes;
import com.m.rabbit.cache.engine.behavior.ICacheElement;


@SuppressWarnings("serial")
public abstract class AbstractDiskCache extends AbstractCache {

    /** Generic disk cache attributes */
    private IDiskCacheAttributes dcattr = null;

    @SuppressWarnings("rawtypes")
    protected Map elementMap = new HashMap();

    protected int mapHits = 0;

    public void setCacheAttributes(ICacheAttributes cattr) {
        dcattr = (IDiskCacheAttributes) cattr;
    }

    @SuppressWarnings("unchecked")
    public final void update(ICacheElement cacheElement) {
        // Wrap the CacheElement in a WrapCacheElement
        WrapCacheElement wce = new WrapCacheElement(cacheElement);
        try {
            if (alive) {
                if (elementMap.containsKey(wce.getKey())) {
                    return;
                }
                // Add the element to elementMap
                synchronized (elementMap) {
                    if (elementMap.containsKey(wce.getKey())) {
                        return;
                    }
                    if (elementMap.size() < dcattr.getMaxMapSize()) {
                        elementMap.put(wce.getKey(), wce);
                    } else {
                        return;
                    }
                }

                synchronized (wce.getCacheElement()) {
                    ICacheElement element = null;
                    synchronized (elementMap) {
                        // If the element has already been removed from
                        // wrap cache element do nothing
                        if (!elementMap.containsKey(wce.getKey())) {
                            return;
                        }

                        element = wce.getCacheElement();
                    }
                    doUpdate(element);

                    synchronized (elementMap) {
                        // After the update has completed, it is safe to
                        // remove the element from cacheElement.
                        elementMap.remove(wce.getKey());
                    }
                }
            } else {
                /*
                 * The cache is not alive, hence the element should be removed
                 * from purgatory. All elements should be removed eventually.
                 * Perhaps, the alive check should have been done before it went
                 * in the queue. This block handles the case where the disk
                 * cache fails during normal operations.
                 */
                synchronized (elementMap) {
                    elementMap.remove(wce.getKey());
                }
            }
        } catch (Exception ex) {

        }
    }

    public final ICacheElement get(Serializable key) {
        if (!alive) {
            return null;
        }

        WrapCacheElement wce = null;

        synchronized (elementMap) {
            wce = (WrapCacheElement) elementMap.get(key);
        }

        if (wce != null) {
            mapHits++;
            return wce.cacheElement;
        }

        try {
            return doGet(key);
        } catch (Exception e) {

        }
        return null;
    }

    public final boolean remove(Serializable key) {
        WrapCacheElement wce = null;
        synchronized (elementMap) {
            Object value = elementMap.get(key);
            if (value != null) {
                wce = (WrapCacheElement) value;
            }
        }
        if (wce != null) {
            synchronized (wce.getCacheElement()) {
                synchronized (elementMap) {
                    elementMap.remove(key);
                }
                // Remove from persistent store immediately
                return doRemove(key);
            }
        } else {
            // Remove from persistent store immediately
            return doRemove(key);
        }

    }

    public final void dispose() {
        alive = false;
        doDispose();
    }

    public int getStatus() {
        return (alive ? CacheConstants.STATUS_ALIVE : CacheConstants.STATUS_DISPOSED);
    }

    protected abstract ICacheElement doGet(Serializable key);

    protected abstract void doUpdate(ICacheElement element);

    protected abstract boolean doRemove(Serializable key);

    protected abstract void doDispose();
}
