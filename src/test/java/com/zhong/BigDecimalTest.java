package com.zhong;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author: juzi
 * @date: 2023/4/10
 * @description:
 */
public class BigDecimalTest {

    /**
     * @author juzi
     * @date 2023/4/10 13:38
     * @desc 比较
     */
    @Test
    public void comp(){
        BigDecimal one = new BigDecimal("1");
        BigDecimal two = new BigDecimal("1.00");
        int compare = one.compareTo(two);
        System.out.println(compare);

    }

    /**
     * @author juzi
     * @date 2023/7/20 上午 9:53
     * @description 转成负数
     */
    @Test
    public void negate(){
        BigDecimal aa = new BigDecimal("10");
        aa = aa.negate();
        System.out.println("aa = " + aa); // 输出 -10
    }

}
