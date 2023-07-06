package com.zhong;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
}
