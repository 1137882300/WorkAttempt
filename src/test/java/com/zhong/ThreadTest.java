package com.zhong;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2022/1/7 10:43
 */
public class ThreadTest {

    @Test
    public void test(){
        CompletableFuture.runAsync(()->{
            System.out.println("ss");
        });


    }

}
