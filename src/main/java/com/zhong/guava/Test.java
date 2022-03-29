package com.zhong.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheStats;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/12/28 16:00
 */
public class Test {

    public static void main(String[] args) throws ExecutionException {
        Cache<Object, Object> loadingCache = new Cache<Object, Object>() {
            @Override
            public Object getIfPresent(Object o) {
                return null;
            }

            @Override
            public Object get(Object o, Callable<?> callable) throws ExecutionException {
                return null;
            }

            @Override
            public ImmutableMap<Object, Object> getAllPresent(Iterable<?> iterable) {
                return null;
            }

            @Override
            public void put(Object o, Object o2) {

            }

            @Override
            public void putAll(Map<?, ?> map) {

            }

            @Override
            public void invalidate(Object o) {

            }

            @Override
            public void invalidateAll(Iterable<?> iterable) {

            }

            @Override
            public void invalidateAll() {

            }

            @Override
            public long size() {
                return 0;
            }

            @Override
            public CacheStats stats() {
                return null;
            }

            @Override
            public ConcurrentMap<Object, Object> asMap() {
                return null;
            }

            @Override
            public void cleanUp() {

            }
        };

        Object key = loadingCache.get("key", new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "v";
            }
        });
        System.out.println(key);

    }




}
