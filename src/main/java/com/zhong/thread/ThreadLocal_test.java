package com.zhong.thread;

import java.util.stream.IntStream;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/12/27 11:00
 */
public class ThreadLocal_test {
    public static void main(String[] args) {

        ThreadLocal<String> local = new ThreadLocal<>();

        IntStream.range(0, 10).forEach(i -> new Thread(() -> {
            local.set(Thread.currentThread().getName() + ":" + i);
            System.out.println("线程：" + Thread.currentThread().getName() + ",local:" + local.get());
        }).start());


        IntStream.range(0, 10).forEach(System.out::println);
    }
}
