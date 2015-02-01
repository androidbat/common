/*
 * Copyright (c) 2012 Freeman TV. All rights reserved.
 */
package com.m.rabbit.cache.diskcache.createtime;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.m.rabbit.cache.diskcache.NoValueCacheElement;
import com.m.rabbit.cache.engine.behavior.ICacheElement;
import com.m.rabbit.cache.engine.behavior.IElementSerializer;


/**
 * Performs default serialization and de-serialization.
 */
public class StandardSerializer implements IElementSerializer {

    /**
     * Serializes an object using default serilaization.
     */
    public boolean serialize(Serializable obj, Object params) {
        ICacheElement ce = (ICacheElement) obj;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(params.toString());
            oos = new ObjectOutputStream(fos);
            oos.writeObject(new NoValueCacheElement(ce.getCacheName(), ce.getKey(), null, ce.getElementAttributes()));
            oos.writeObject(ce);
            oos.flush();
            return true;
        } catch (IOException e) {
            return false;
        }catch (OutOfMemoryError e) {
			return false;
		} finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                }
            }
        }
    }

    @Override
    public ICacheElement deSerialize(Object params) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(params.toString());
            ois = new ObjectInputStream(fis);
            // read NoValueCacheElement first,but we do not need it
            ois.readObject();
            return (ICacheElement) ois.readObject();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        } catch (OutOfMemoryError e) {
		}finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    public NoValueCacheElement deSerializeNoValueCacheElement(Object params) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(params.toString());
            ois = new ObjectInputStream(fis);
            // read NoValueCacheElement
            return (NoValueCacheElement) ois.readObject();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }
}
