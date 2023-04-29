package com.zhong;

import org.junit.Test;

import java.util.stream.IntStream;

/**
 * @author: juzi
 * @date: 2023/4/27
 * @desc: java.util.stream 測試類
 */
public class StreamTest {

    /**
     * @author juzi
     * @date 2023/4/27 下午 2:40
     * @description 范围循环，简化for
     */
    @Test
    public void IntStream() {
        IntStream.range(55, 66).forEach(System.out::println);
    }

}
