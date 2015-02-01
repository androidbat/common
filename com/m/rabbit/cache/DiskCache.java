package com.m.rabbit.cache;

import java.io.File;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.Build.VERSION_CODES;

import com.m.rabbit.cache.diskcache.createtime.CreateTimeDiskCache;
import com.m.rabbit.cache.diskcache.createtime.CreateTimeDiskCacheAttributes;
import com.m.rabbit.cache.engine.ElementAttributes;
import com.m.rabbit.utils.FileUtils;
import com.m.rabbit.utils.LLog;
/**
 * 
 * @author mingrenhan
 * 2012-10-12
 */
public class DiskCache {
    //string cache
    private static CacheAccess stringCache;
    
    //image cache
    private static CacheAccess firstPageImageCache;
    private static CacheAccess otherPageImageCache;
    
    private static String cacheDir = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Android/data/shaopin";
    
    public static CacheAccess getStringCache(){
        if(stringCache == null){
            CreateTimeDiskCacheAttributes ctdca = new CreateTimeDiskCacheAttributes();
            ctdca.setCacheClassName(CreateTimeDiskCache.class.getName());
            ctdca.setDiskPath(cacheDir + "/stringCache/");
            ctdca.setMaxObjects(500);
            ElementAttributes ea = new ElementAttributes();
            //ten minutes
            ea.setMaxLifeSeconds(60 * 60*24);//1天过期
            try {
                stringCache = CacheAccess.getInstance("stringCache", ctdca, ea);
            } catch (Exception e) {
            }
        }
        return stringCache;
    }
    
    public static CacheAccess getFirstPageImageCache(){
        if(firstPageImageCache == null){
            CreateTimeDiskCacheAttributes ctdca = new CreateTimeDiskCacheAttributes();
            ctdca.setCacheClassName(CreateTimeDiskCache.class.getName());
            ctdca.setMaxObjects(500);
            ctdca.setDiskPath(cacheDir + "/imageCache/firstPageImageCache/");
            ElementAttributes ea = new ElementAttributes();
            //ten minutes
            ea.setMaxLifeSeconds(60 * 5);//5分钟过期
            try {
                firstPageImageCache = CacheAccess.getInstance("firstPageImageCache", ctdca,ea);
            } catch (Exception e) {
            }
        }
        return firstPageImageCache;
    }

    public static CacheAccess getOtherPageImageCache(){
        if(otherPageImageCache == null){
            CreateTimeDiskCacheAttributes ctdca = new CreateTimeDiskCacheAttributes();
            ctdca.setCacheClassName(CreateTimeDiskCache.class.getName());
            ctdca.setMaxObjects(500);
            ctdca.setDiskPath(cacheDir + "/imageCache/otherPageImageCache/");
            LLog.d(cacheDir);
            try {
                otherPageImageCache = CacheAccess.getInstance("otherPageImageCache", ctdca);
            } catch (Exception e) {
            }
        }
        return otherPageImageCache;
    }
    
    public static void setCacheDir(String cacheDir) {
        DiskCache.cacheDir = cacheDir;
    }

    public static String getCacheDir() {
        return cacheDir;
    }
}
