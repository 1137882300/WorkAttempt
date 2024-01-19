package com.zhong.jdk17;


import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author: juzi
 * @date: 2024/1/19
 * @desc:
 */
public class Test {


    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        list.forEach((var n) -> System.out.println(n));

    }


}
