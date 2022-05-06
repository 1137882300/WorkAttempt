package com.zhong.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @date 2022/4/25 16:37
 */
public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        /*
            runAsync()    : 当你需要异步操作且不关心返回结果的时候 使用 runAsync() 方法。
            supplyAsync() : 当你需要异步操作且关心返回结果的时候,可以使用 supplyAsync() 方法。
         */
//        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> System.out.println("hello!"));
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> System.out.println("hello!"));
//        Void unused = future.get();


        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "hello!");
        String s = future2.get();
//        System.out.println(s);
    }

}
