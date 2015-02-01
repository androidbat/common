/*
 * Copyright (c) 2012 Freeman TV. All rights reserved.
 */
package com.m.rabbit.cache.diskcache.createtime;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

import com.m.rabbit.cache.diskcache.AbstractDiskCache;
import com.m.rabbit.cache.diskcache.NoValueCacheElement;
import com.m.rabbit.cache.engine.ElementAttributes;
import com.m.rabbit.cache.engine.behavior.ICacheAttributes;
import com.m.rabbit.cache.engine.behavior.ICacheElement;
import com.m.rabbit.cache.engine.behavior.IElementAttributes;
import com.m.rabbit.utils.LLog;


public class CreateTimeDiskCache extends AbstractDiskCache {

    private static final long serialVersionUID = 998133518569429L;

    private CreateTimeDiskCacheAttributes cacheAttr;

    protected Queue<NoValueCacheElement> mQueue = null;

    private IElementAttributes ea = new ElementAttributes();

    private int hitCount = 0;

    /** Serializing the objects */
    private static final StandardSerializer SERIALIZER = new StandardSerializer();

    protected void updateQueue(ICacheElement ce) {
        NoValueCacheElement update = new NoValueCacheElement(ce.getCacheName(), ce.getKey(), null,
                ce.getElementAttributes());
        mQueue.remove(update);
        deleteFromFile(cacheAttr.getDiskPath() + update.getKey());
        if (mQueue.size() >= cacheAttr.getMaxObjects()) {
            ICacheElement removed = mQueue.poll();
            if (removed != null) {
                deleteFromFile(cacheAttr.getDiskPath() + removed.getKey());
            }
        }
        mQueue.offer(update);
    }

    private void deleteFromFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    @Override
    protected void doDispose() {

    }

    @Override
    protected ICacheElement doGet(Serializable key) {
        NoValueCacheElement update = new NoValueCacheElement(null, key, null, null);
        if (mQueue.contains(update)) {
            ICacheElement ce = SERIALIZER.deSerialize(cacheAttr.getDiskPath() + key);
            if (ce != null) {
                incrementHitCount();
                return ce;
            }
        }
        return null;
    }

    @Override
    protected boolean doRemove(Serializable key) {
        try {
            NoValueCacheElement update = new NoValueCacheElement(null, key, null, null);
            mQueue.remove(update);
            deleteFromFile(cacheAttr.getDiskPath() + key);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    protected void doUpdate(ICacheElement element) {
        updateQueue(element);
        SERIALIZER.serialize(element, cacheAttr.getDiskPath() + element.getKey());
    }

    @Override
    public CreateTimeDiskCacheAttributes getCacheAttributes() {
        return cacheAttr;
    }

    public void setCacheAttributes(ICacheAttributes cattr) {
        super.setCacheAttributes(cattr);
        cacheAttr = (CreateTimeDiskCacheAttributes) cattr;
    }

    @Override
    public int getSize() {
        return mQueue.size();
    }

    @Override
    public void init() {
        mQueue = new PriorityQueue<NoValueCacheElement>(cacheAttr.getMaxObjects(), new CacheComparator());
        File cacheDir = new File(cacheAttr.getDiskPath());
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }

        File[] files = cacheDir.listFiles();
        if (files != null) {
            for (File cacheFile : files) {
                String path = cacheAttr.getDiskPath() + cacheFile.getName();
                NoValueCacheElement nce = SERIALIZER.deSerializeNoValueCacheElement(path);
                if (nce != null) {
                    if (mQueue.size() < cacheAttr.getMaxObjects()) {
                        mQueue.offer(nce);
                    } else {
                        deleteFromFile(path);
                    }
                } else {
                    deleteFromFile(path);
                }
            }
        }
    }

    @Override
    public void removeAll() throws IOException {
    	File cacheDir = new File(cacheAttr.getDiskPath());
        if (!cacheDir.exists()) {
            return;
        }

        File[] files = cacheDir.listFiles();
        if (files != null) {
            for (File cacheFile : files) {
            	deleteFromFile(cacheAttr.getDiskPath() + cacheFile.getName());
            }
        }
    }

    @Override
    public void setElementAttributes(IElementAttributes attr) {
        ea = attr;
    }

    @Override
    public String getCacheName() {
        return cacheAttr.getCacheName();
    }

    @Override
    public IElementAttributes getElementAttributes() {
        return ea.copy();
    }

    private static final class CacheComparator implements Comparator<ICacheElement> {

        @Override
        public int compare(ICacheElement arg0, ICacheElement arg1) {
            int ret = 0;
            try {
                ret = (int) (arg0.getElementAttributes().getCreateTime() - arg1.getElementAttributes().getCreateTime());
            } catch (Exception e) {

            }
            return ret;
        }

    }

    /**
     * Increments the hit count in a thread safe manner.
     */
    private synchronized void incrementHitCount() {
        if (this.getCacheAttributes().getCacheName().equals("stringCache")) {
            hitCount++;
        }

    }

}
