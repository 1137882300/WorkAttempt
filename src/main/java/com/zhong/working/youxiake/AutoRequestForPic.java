package com.zhong.working.youxiake;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.log.Log;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.zhong.network.HttpService;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: juzi
 * @date: 2023/10/31
 * @desc:
 */
@Slf4j
public class AutoRequestForPic {

    public static void main(String[] args) {

        String path = "https://fxhz.wgly.hangzhou.gov.cn/dashboard/api/oss/transfer/pic";

        List<String> list = FileUtil.readUtf8Lines(new File("img.txt"));
        List<String> filteredList = CollUtil.removeNull(CollUtil.removeEmpty(list));
        List<String> collect = filteredList.stream().filter(Objects::nonNull)
                .map(o -> o.trim().substring(1, o.length() - 2))
                .distinct()
                .collect(Collectors.toList());

        Lists.partition(collect, 200).forEach(x -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("urlList", x);
            HttpService.post(path, jsonObject);
            log.info(x.get(x.size() - 1));
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                log.info("error:end：{}", x.get(x.size() - 1));
            }
        });
    }

}
