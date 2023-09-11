package com.zhong.decryption;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.zhong.utils.FileUtils;
import com.zhong.decryption.util.HzTourAesCbcAndBase64Util;
import lombok.SneakyThrows;

import java.io.File;
import java.util.List;

/**
 * @author: juzi
 * @date: 2023/4/7
 * @description:
 */
public class Test2 {


    /**
     * @author juzi
     * @date 2023/4/7 15:02
     * @description
     * String.valueOf(jsonObject) 搭配 result.toString()  生成json格式
     */
    @SneakyThrows
    public static void main(String[] args) {

        String slatKey = "gMhXfD9ePF8YhXQx";
        String vectorKey = "hm3qvrxGaeJLrvx9";

        String path = "E:\\新建文本文档.txt";

        List<String> list = FileUtils.readFile(path);
        List<String> result = Lists.newArrayList();
        list.stream().distinct().forEach(x -> {
            try {
                JSONObject json = new JSONObject();
                json.fluentPut("cardNo", x);
                json.fluentPut("name", "ha");
                json.fluentPut("phone", "15397073571");
                String encrypt = HzTourAesCbcAndBase64Util.encrypt(json.toJSONString(), slatKey, vectorKey);
                JSONObject jsonObject = new JSONObject();
                jsonObject.fluentPut("type", 1);
                jsonObject.fluentPut("member", encrypt);
                result.add(String.valueOf(jsonObject));
            } catch (Exception ignored) {
            }
        });
        FileUtil.writeUtf8String(result.toString(), new File("入参.json"));
    }


    public static void main7(String[] args) {
        File file = new File("C:\\Users\\root\\Desktop\\入参.json");
        String string = FileUtil.readUtf8String(file);
        JSONArray jsonArray = JSONObject.parseArray(string);
        System.out.println(jsonArray.get(0));
        System.out.println(jsonArray.get(1));
    }

}
