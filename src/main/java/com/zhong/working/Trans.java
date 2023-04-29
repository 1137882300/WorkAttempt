package com.zhong.working;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhong.network.HttpService;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author: juzi
 * @date: 2023/4/20
 * @desc:
 */
public class Trans {

    public static void main(String[] args) {

        //执行前必修改
//        int bizType = 1;
        int bizType = 21;
        File file = new File("E:\\poi剩余的.text");

        List<String> errList = Lists.newArrayList();
        String fileStr = FileUtil.readUtf8String(file);
        String[] arr = fileStr.split(",");
        List<String> list = Arrays.asList(arr);
//        List<String> asList = Collections.singletonList(list.get(0));
//        asList.forEach(System.out::println);
        list.forEach(id -> {
            String url = String.format("https://fxhz.wgly.hangzhou.gov.cn/dashboard/api/translation/get?bizType=%s&id=%s", bizType, id);
            new Trans().doRequest(url, id, errList);
//            try {
////                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
        });
        System.out.println("errList:" + JSONObject.toJSONString(errList));
    }


    private void doRequest(String url, String id, List<String> errList) {
        Map<String, String> header = Maps.newHashMap();
        header.put("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyMSIsImlhdCI6MTY4MTk1Njc2MywiZXhwIjoxNjg0NTQ4NzYzLCJuYmYiOjE2ODE5NTY3NjN9.d67BlJEBO6H18FydrFX1U6YaRCmhSeaedT0RCsaXv5c");
        JSONObject result = HttpService.get(url, header);
//        System.out.println(result);
        if (result.getString("code").equals("500")) {
            System.out.println("不ok" + id + "--" + result);
            errList.add(id);
        } else {
            System.out.println("ok" + id + "--" + result);
        }
    }
}
