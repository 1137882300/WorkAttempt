package com.zhong.working.youxiake;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: juzi
 * @date: 2023/10/31
 * @desc:
 */
public class ReadLogFile {


    public static void main(String[] args) {
        String logFilePath = "C:\\Users\\root\\Downloads\\log_admin_2023-10-31.log";
//        String regex = "value:\\[\"(.*?)\"]";
//        String regex = "errList size:\\d+,value:\\\\[\"(.*?)]";
//        String regex = "errList size:\\d+,value\\s+(\\{.*?}|\\[.*?])";
        String regex = "errList size:\\d+,value:\\[\"(.*?)\"]";

        List<JSONArray> list = Lists.newArrayList();

        try (BufferedReader reader = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String jsonValue = "[\"" + matcher.group(1) + "\"]";
                    JSONArray parse = (JSONArray) JSONArray.parse(jsonValue);
                    list.add(parse);
//                    System.out.println("JSON Array: " + parse);
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        Set<String> distinct = Sets.newHashSet();
        list.forEach(x -> x.forEach(o -> {
            distinct.add("\"" + "https://hztour-img.youxiake.com/" + o.toString() + "\",");
        }));
        System.out.println(distinct.size());
        FileUtil.writeUtf8Lines(distinct, new File("去重img.txt"));

//        StringBuilder stringBuilder = new StringBuilder();
//        for (JSONArray jsonArray : list) {
//            for (Object o : jsonArray) {
//                o = "https://hztour-img.youxiake.com/" + o.toString();
//                stringBuilder.append("\"").append(o).append("\"").append(",\n");
//            }
//        }
//        FileUtil.writeUtf8String(stringBuilder.toString(), new File("img.txt"));
    }

}
