package com.zhong.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/12/28 16:33
 */
public class DefaultCache implements Cache{

    private Map<Object,Object> cache = new HashMap<>();  //散列结构存储

    @Override
    public void putObject(Object key, Object value) {
        cache.put(key,value);
    }

    @Override
    public Object getObject(Object key) {
        return cache.get(key);
    }

    @Override
    public Object removeObject(Object key) {
        return cache.remove(key);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public String toString() {
        return "DefaultCache{" +
                "cache=" + cache.toString() +
                '}';
    }

    public static void main(String[] args) {
        Cache cache=new DefaultCache();
        cache.putObject("A",100);
        cache.putObject("B",200);
        cache.putObject("C",300);
        System.out.println(cache);
        System.out.println(cache.getObject("A"));
    }
}
