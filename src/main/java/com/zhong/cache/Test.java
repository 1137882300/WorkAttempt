package com.zhong.cache;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/12/28 11:25
 */
public class Test {


    public static void main(String[] args) throws IOException {
        List<Long> one = Lists.newArrayList();
        List<Long> two = Lists.newArrayList();
        List<Long> three = Lists.newArrayList();
        List<Long> four = Lists.newArrayList();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("D:\\IdeaProjects\\WorkAttempt\\src\\main\\resources\\汇总.csv"));//换成你的文件名
            reader.readLine();//第一行信息，为标题信息，不用，如果需要，注释掉
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] item = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                if(item.length == 0){
                    continue;
                }
                if (StringUtils.isNotEmpty(item[0])) {
                    one.add(Long.valueOf(item[0]));
                }
                if (item.length >= 2 && StringUtils.isNotEmpty(item[1])) {
                    two.add(Long.valueOf(item[1]));
                }
                if (item.length >= 3 &&StringUtils.isNotEmpty(item[2])) {
                    three.add(Long.valueOf(item[2]));
                }
                if (item.length >= 4 &&StringUtils.isNotEmpty(item[3])) {
                    four.add(Long.valueOf(item[3]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collection<Long> coll = CollectionUtils.union(CollectionUtils.union(one,two),CollectionUtils.union(three,four));
        System.out.println(JSON.toJSONString(coll.stream().distinct().collect(Collectors.toList())));
    }
}
