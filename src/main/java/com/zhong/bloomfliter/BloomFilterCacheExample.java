package com.zhong.bloomfliter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: juzi
 * @date: 2024/2/20
 * @desc:
 */

public class BloomFilterCacheExample {

    private static BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), 1000, 0.01);

    private static Map<String, String> cache = new HashMap<>();

    // 模拟数据库查询函数
    private static String queryDatabase(String query) {
        // 这里简单地返回一个字符串作为模拟数据库查询结果
        return "Result for query: " + query;
    }

    // 处理请求的函数
    private static String processRequest(String query) {
        // 先通过布隆过滤器判断请求的查询条件是否存在
        if (bloomFilter.mightContain(query)) {
            // 如果存在于布隆过滤器中，再检查缓存
            if (cache.containsKey(query)) {
                return cache.get(query);
            } else {
                // 如果缓存中没有，查询数据库
                String result = queryDatabase(query);
                cache.put(query, result);
                return result;
            }
        } else {
            // 如果布隆过滤器判断不存在，直接返回错误信息
            return "Query not found";
        }
    }

    public static void main(String[] args) {

        // 模拟一些请求
        String[] queries = {"apple", "banana", "orange"};

        // 将一些查询条件添加到布隆过滤器中
        for (String query : queries) {
            bloomFilter.put(query);
        }

        // 处理请求
        for (String query : queries) {
            String result = processRequest(query);
            System.out.println(result);
        }

        // 模拟一个不存在的查询条件
        String nonExistentQuery = "grape";
        String result = processRequest(nonExistentQuery);
        System.out.println(result);
    }
}
