package com.zhong.working;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.zhong.network.HttpService;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author: juzi
 * @date: 2023/9/14
 * @desc:
 */
public class GetScenicSpotName {


    public static void main1(String[] args) {
        String path = "https://wap.lotsmall.cn/lotsapi/merchant/api/merchantParkInfo/merchantParkInfo?wayType=1&merchantParkInfoId=%s&xj_time_stamp_2019_11_28=1694680871341&merchantInfoId=1724&merchantId=1724";

        List<String> list = Lists.newArrayList();
        IntStream.range(100500, 200500).parallel().forEach(x -> {
            JSONObject jsonObject = HttpService.get(String.format(path, x));
            if (ObjectUtils.isNotEmpty(jsonObject)) {
                String name = jsonObject.getString("name");
                if (StringUtils.isNotBlank(name)) {
                    list.add(name);
                }
            }
        });
        System.out.println(JSONObject.toJSONString(list));
    }

    @SneakyThrows
    public static void main(String[] args) {
        String path = "https://wap.lotsmall.cn/lotsapi/merchant/api/merchantParkInfo/merchantParkInfo?wayType=1&merchantParkInfoId=%s";

        List<String> list = Collections.synchronizedList(new ArrayList<>());
        List<Integer> range = IntStream.range(0, 20001).boxed().collect(Collectors.toList());
        ExecutorService executorService = Executors.newFixedThreadPool(20);

        for (int x : range) {
            executorService.submit(() -> {
                JSONObject jsonObject = HttpService.get(String.format(path, x));
                if (ObjectUtils.isNotEmpty(jsonObject)) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    if (ObjectUtils.isNotEmpty(data)) {
                        String name = data.getString("name");
                        list.add(name);
                    }
                }
            });
        }
        executorService.shutdown();
        //等待所有任务执行完成。
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        System.out.println(JSONObject.toJSONString(list));
    }

}
