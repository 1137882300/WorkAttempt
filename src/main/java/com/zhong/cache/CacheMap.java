package com.zhong.cache;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @date 2022/3/4 16:06
 */
public class CacheMap {

    private static final AtomicReference<Map<Long, String>> CacheMap = new AtomicReference<>(new HashMap<>());

    public static void putAll(Map<Long, String> map){
        Map<Long, String> copy = new HashMap<>(map);
        CacheMap.set(copy);
    }

    public static Map<Long, String> getAll(){
        return new HashMap<>(CacheMap.get());
    }

    public static String getProperty(Long key){
        if (null != key){
            return CacheMap.get().get(key);
        }else {
            return null;
        }
    }


}
