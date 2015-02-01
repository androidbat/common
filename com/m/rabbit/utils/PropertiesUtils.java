package com.m.rabbit.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;


public class PropertiesUtils {
	public static final String PROPERTIES_COMMON_PATH = "/assets/server_info.properties";
	private static HashMap<String, Properties> propertiesMap = new HashMap<String, Properties>();
	public static String SERVER_PATH = "sver";
	private static Properties domainProperties;
	
	// ---- application uses ----//
    public final static String POID = "poid";
    public final static String CLIENT = "client";
    public final static String PLATFORM = "platform";
    public final static String PLATFORM_PAD = "platform_pad";
    public static String PARTNERNO = "partnerNo";
    
	static { 
        domainProperties = getProperties(PROPERTIES_COMMON_PATH);
    }
	
	public static Properties getProperties(String configPath) {
        Properties properties = propertiesMap.get(configPath);
        synchronized (propertiesMap) {
            if (properties == null) {
                synchronized (propertiesMap) {
                    properties = loadProperties(configPath);
                    propertiesMap.put(configPath, properties);
                }
            }
        }
        return properties == null ? null : properties;
    }
	
	private static Properties loadProperties(String configPath) {
        Properties properties = new Properties();
        if (StringUtils.isNotEmpty(configPath)) {
            InputStream in = PropertiesUtils.class.getResourceAsStream(configPath);
            try {
                if (in != null) {
                    properties.load(in);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }
	
	public static String getValueA(String configPath, String key) {
        Properties properties = propertiesMap.get(configPath);
        synchronized (propertiesMap) {
            if (properties == null) {
                synchronized (propertiesMap) {
                    properties = loadProperties(configPath);
                    propertiesMap.put(configPath, properties);
                }
            }
        }
        return properties == null ? null : properties.getProperty(key);
    }
	
	public static String getDomainValue(String key, String defaultValue){
        if(domainProperties != null){
            String value = domainProperties.getProperty(key);
            if(StringUtils.isNotEmpty(value)){
                return value;
            }
        }
        return defaultValue;
    }
}
