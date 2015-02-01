/*
 * Copyright (c) 2012 Freeman TV. All rights reserved.
 */
package com.m.rabbit.cache.engine.behavior;

import java.io.IOException;
import java.io.Serializable;

/**
 * Defines the behavior for cache element serializes. This layer of abstraction
 * allows us to plug in different serialization mechanisms, such as XStream.
 * 
 * @author mingrenhan
 */
public interface IElementSerializer {

    /**
     * serialize the obj.
     * 
     * @param obj
     * @return
     * @throws IOException
     */
    public abstract boolean serialize(Serializable obj, Object params) throws IOException;

    /**
     * Turns the Object.
     * 
     * @param bytes
     * @return
     * @throws IOException
     */
    public abstract Object deSerialize(Object params) throws IOException;

}
