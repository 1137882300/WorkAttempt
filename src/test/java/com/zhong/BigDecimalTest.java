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

}
