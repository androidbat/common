/*
 * Copyright (c) 2012 Freeman TV. All rights reserved.
 */
package com.m.rabbit.cache.engine;

/**
 * Constants used by the JCS cache engine
 * 
 * @author mingrenhan
 */
public interface CacheConstants {
    /** Cache alive status. */
    public final static int STATUS_ALIVE = 1;

    /** Cache disposed status. */
    public final static int STATUS_DISPOSED = 2;

    /** Cache in error. */
    public final static int STATUS_ERROR = 3;

}
