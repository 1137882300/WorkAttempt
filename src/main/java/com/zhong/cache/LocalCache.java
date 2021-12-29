package com.zhong.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/12/27 15:04
 */
public class LocalCache {

    private static final Map<String, Object> map;

    static {
        map = new HashMap<>();
    }

    public static void put(String key, Object value) {
        map.put(key, value);
    }

    public static void putAll(Map<String, Object> m) {
        map.putAll(m);
    }

    public static Object get(String key) {
        return map.get(key);
    }

    public static boolean containsKey(String key) {
        return map.containsKey(key);
    }

    public static void remove(String key) {
        map.remove(key);
    }

    public static int size() {
        return map.size();
    }
    public static void clear() {
        if (size() > 0) {
            map.clear();
        }
    }



}
