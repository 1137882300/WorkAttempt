package com.zhong;

import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/10/14 10:20
 */
public class BaseTest {


    @Test
    public void test(){
        int k = 10;
        if (k/5 == 2 ){
            System.out.println(111);
        }else if (k/2 == 5){
            System.out.println(222);
        } else {
            System.out.println(333);
        }
    }

    @Test
    public void test2(){
        String ss = "我是谁呀",qq = "zzqw";

        System.out.println(ss.length());
        System.out.println(qq.length());
        System.out.println(ss.getBytes().length);
        System.out.println(qq.getBytes().length);
        System.out.println(ss.getBytes(StandardCharsets.UTF_8).length);
        System.out.println(qq.getBytes(StandardCharsets.UTF_8).length);

    }



}
