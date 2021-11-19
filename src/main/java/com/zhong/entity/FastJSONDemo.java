package com.zhong.entity;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/11/19 14:47
 */
public class FastJSONDemo {

    public static void main(String[] args) {

        Map<String, String> skuExtendMap = Maps.newHashMap();

        skuExtendMap.put("orderType", "2");
        skuExtendMap.put("goodsCode", "123");
        skuExtendMap.put("guarantee", "13213");
        skuExtendMap.put("isTaxIncluded", "0");
        String s = JSON.toJSONString(skuExtendMap);
        System.out.println("json字符串：" +s);
        System.out.println("直接toString："+skuExtendMap.toString());

    }
}
