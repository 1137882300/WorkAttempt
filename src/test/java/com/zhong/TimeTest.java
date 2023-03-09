package com.zhong;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @date 2022/7/18 9:53
 */
public class TimeTest {

    /**
     * 时间转换
     * 比如：小时 -> 分钟
     */
    @Test
    public void time() {
        //分钟 -> 小时
        long toMillis = TimeUnit.MINUTES.toHours(60);
        System.out.println(toMillis);

        long toHours = TimeUnit.DAYS.toHours(1);
        System.out.println(toHours);

    }

}
