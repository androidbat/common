/*
 * Copyright (c) 2012 Freeman TV. All rights reserved.
 */
package com.m.rabbit.cache.diskcache.createtime;

import com.m.rabbit.cache.diskcache.AbstractDiskCacheAttributes;

public class CreateTimeDiskCacheAttributes extends AbstractDiskCacheAttributes {

    private static final long serialVersionUID = -2326960367988044740L;

    public CreateTimeDiskCacheAttributes copy() {
        try {
            return (CreateTimeDiskCacheAttributes) this.clone();
        } catch (Exception e) {
        }
        return this;
    }

}
