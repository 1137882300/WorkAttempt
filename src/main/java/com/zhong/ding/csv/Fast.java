package com.zhong.ding.csv;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.zhong.ding.excel.Entity;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @date 2022/10/21 17:23
 */
public class Fast {


    public static void main(String[] args) {
        String path = "C:\\Users\\EDZ\\Downloads\\1020针对货仓批次不可售.csv";
        List<Entity> list = readLineCSV(path);
        String string = JSON.toJSONString(list);
        System.out.println(string);

    }


    @SneakyThrows
    public static List<Entity> readLineCSV(String path) {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        reader.readLine();//先读取第一行(过滤第一行)
        String line;
        List<Entity> list = Lists.newLinkedList();
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(",");
            Entity entity = new Entity();
            entity.setColumn1(split[0]);
            entity.setColumn2(split[1]);
            list.add(entity);

        }
        return list;

    }

}
