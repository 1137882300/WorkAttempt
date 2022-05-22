package com.zhong.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @date 2022/4/25 16:44
 */
public class Test2 {
/*
    CompletableFuture 异步编程的类
    业务场景：    项目中应用到并发的场景主要是调用远程RPC接口，即IO密集型的场景。
                1. 第一个场景是需要分别调用两个RPC接口，无先后顺序关系，数据处理是需要同时依赖两个接口的返回数据。
                2. 第二个场景是需要循环调用RPC接口，调用100次以内，所有调用结束后统一对返回结果进行处理。
 */

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //thenApply() 方法接受一个 Function 实例，用它来处理结果。
        CompletableFuture<String> future = CompletableFuture.completedFuture("hello!").thenApply(s -> s + "world!");

        // 这次调用将被忽略。
        future.thenApply(s -> s + "nice!");

        String ss = future.get();
        System.out.println(ss);

        //流式调用：
        CompletableFuture<String> future2 = CompletableFuture.completedFuture("hello!").thenApply(s -> s + "world!").thenApply(s -> s + "nice!");
        System.out.println(future2.get());

        // thenAccept() 能访问异步计算的结果。
        CompletableFuture.completedFuture("hello!").thenApply(s -> s + "world!").thenApply(s -> s + "nice!").thenAccept(System.out::println);//hello!world!nice!

        // thenRun() 不能访问异步计算的结果。
        CompletableFuture.completedFuture("hello!").thenApply(s -> s + "world!").thenApply(s -> s + "nice!").thenRun(() -> System.out.println("hello!"));//hello!

        // allOf() 并行运行多个 CompletableFuture ,allOf() 方法会等到所有的 CompletableFuture 都运行完成之后再返回
        CompletableFuture<Void> task1 =
                CompletableFuture.supplyAsync(() -> {
                    //自定义业务操作
                    System.out.println("hi");
                    return null;
                });
        CompletableFuture<Void> task6 =
                CompletableFuture.supplyAsync(() -> {
                    //自定义业务操作
                    System.out.println("lo");
                    return null;
                });
        //allOf() 方法会等到所有的 CompletableFuture 都运行完成之后再返回,    anyOf() 方法不会等待所有的 CompletableFuture 都运行完成之后再返回，只要有一个执行完成即可！
        CompletableFuture<Void> headerFuture = CompletableFuture.allOf(task1, task6);

        System.out.println(headerFuture.get());

        try {
            //调用 join() 可以让程序等future1 和 future2 都运行完了之后再继续执行。
            Void join = headerFuture.join();
        } catch (Exception ex) {
        }
        System.out.println("all done. ");

        //thenCompose() 可以两个 CompletableFuture 对象，并将前一个任务的返回结果作为下一个任务的参数，它们之间存在着先后顺序。
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "hello!").thenCompose(s -> CompletableFuture.supplyAsync(() -> s + "world!"));

        //thenCombine() 会在两个任务都执行完成后，把两个任务的结果合并。两个任务是并行执行的，它们之间并没有先后依赖顺序。
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> "hello!").thenCombine(CompletableFuture.supplyAsync(() -> "world!"), (s1, s2) -> s1 + s2)
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + "nice!"));
    }
}
