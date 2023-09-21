package com.zhong;

import cn.hutool.core.util.StrUtil;
import com.google.common.base.Utf8;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import javax.xml.bind.SchemaOutputResolver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author: juzi
 * @date: 2023/7/6
 * @desc:
 */
public class StringTest {


    /**
     * @author juzi
     * @date 2023/7/6 下午 6:17
     * @description 转String数组
     */
    @Test
    public void test() {
        List<String> list = Lists.newArrayList("aaa", "bbb", "ccc");
        String[] strings = list.toArray(new String[0]);
        System.out.println(Arrays.toString(strings));
    }

    @Test
    public void replaceAll() {
        String s = "asda/asdasd-asda-asdasd-".replaceAll("/", "-");
        System.out.println(s);
    }

    @Test
    public void split() {
        String[] split = "1;2;3".split(";");
        if (ArrayUtils.isNotEmpty(split)) {
            if (split.length >= 1) {
                String s0 = split[0];
                System.out.println(s0);
            }
            if (split.length >= 2) {
                String s1 = split[1];
                System.out.println(s1);
            }
            if (split.length >= 3) {
                String s2 = split[2];
                System.out.println(s2);
            }
        }
    }

    @Test
    public void len() {
        StringBuilder stringBuilder = new StringBuilder();
//        IntStream.range(1, 200).forEach(x -> {
//            stringBuilder.append("a");
//            if (stringBuilder.length() >= 128) {
//                System.out.println(stringBuilder);
//            }
//        });
        String ss = "运河夜游（武林门码头）【成人】运河游船往返（武林门码头-三堡船闸-钱塘江-武林门码头）-18:15-21:15场";
        System.out.println(ss.length());


        String str = "运河夜游(武林门码头)【成人】运河游船往返(武林门码头-三堡船闸-钱塘江-武林门码头)-18:15-21:15场";
        //hutool的用法
        int byteLength = StrUtil.utf8Bytes(str).length;
        System.out.println("字节数：" + byteLength);
        System.out.println("字符数：" + str.length());

        //google的用法
        int length = Utf8.encodedLength(str);
        System.out.println("字节数：" + length);
        System.out.println("字符数：" + str.length());
    }
}
