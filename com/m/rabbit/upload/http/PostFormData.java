package com.m.rabbit.upload.http;


import java.util.HashMap;
import java.util.Set;


public class PostFormData {
    public byte[] buildPostFormBoby(HashMap<String, String> formMap) {
        byte[] b = null;
        if (formMap != null && formMap.size() > 0) {
            StringBuffer buffer = new StringBuffer();
            Set<String> keys = formMap.keySet();
            for (String key : keys) {
                buffer.append(key);
                buffer.append("=");
                buffer.append(formMap.get(key));
                buffer.append("&");
            }
            b = buffer.substring(0, buffer.length() - 1).getBytes();
        }
        return b;
    }
}
