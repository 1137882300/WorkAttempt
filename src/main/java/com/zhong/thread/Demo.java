package com.zhong.thread;

import lombok.SneakyThrows;

import java.util.stream.IntStream;

/**
 * @author: juzi
 * @date: 2023/5/18
 * @desc:
 */
public class Demo {
    public static class T1 extends Thread {
        @Override
        public void run() {
            super.run();
            System.out.printf("当前执行的线程是：%s，优先级：%d%n",
                    Thread.currentThread().getName(),
                    Thread.currentThread().getPriority()
            );
        }
    }

    public static void main1(String[] args) {
        IntStream.range(1, 10).forEach(i -> {
            Thread thread = new Thread(new T1());
            thread.setPriority(i);
            thread.start();
        });
    }


    public static void main2(String[] args) {
        ThreadGroup threadGroup = new ThreadGroup("t1");
        threadGroup.setMaxPriority(6);

        Thread thread = new Thread(threadGroup,"thread");
        thread.setPriority(9);
        System.out.println("我是线程组的优先级"+threadGroup.getMaxPriority());
        System.out.println("我是线程的优先级"+thread.getPriority());
    }
    /**
     * @author juzi
     * @date 2023/5/18 下午 2:09
     * @description Thread.join()：等待线程执行完毕，底层调用的是Object实例的wait方法；
     */
    public static void main(String[] args) {
//        testStateNew();
        blockedTest();
    }

    private static void testStateNew() {
        //尚未启动指的是还没调用Thread实例的start()方法
        Thread thread = new Thread(() -> {});

//        两次调用 .start()会报错
//        thread.start(); // 第一次调用
//        thread.start(); // 第二次调用
        System.out.println(thread.getState()); // 输出 NEW
    }

    @SneakyThrows
    public static void blockedTest() {

        Thread a = new Thread(Demo::testMethod, "a");
        Thread b = new Thread(Demo::testMethod, "b");

        a.start();
        a.join();//
        b.start();
        System.out.println(a.getName() + ":" + a.getState()); // 输出？
        System.out.println(b.getName() + ":" + b.getState()); // 输出？
    }

    // 同步方法争夺锁
    private  static synchronized void testMethod() {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}