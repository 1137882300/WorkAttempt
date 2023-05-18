package com.zhong.thread;

import java.util.stream.IntStream;

/**
 * @author: juzi
 * @date: 2023/5/18
 * @desc:
 */
public class ObjectLock {

    private static final Object lock = new Object();

    /**
     * @author juzi
     * @date 2023/5/18 下午 2:27
     * @description 需要注意的是等待/通知机制使用的是使用 同一个对象锁，如果你两个线程使用的是不同的对象锁，那它们之间是不能用等待/通知机制通信的。
     */
    static class ThreadA implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                IntStream.range(1, 100).forEach(x -> {
                    lock.notify();
                    try {
                        lock.wait();
                    } catch (InterruptedException ignored) {
                    }
                    System.out.println("Thread A " + x);
                });
                lock.notify();
            }
        }
    }

    static class ThreadB implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                IntStream.range(1, 100).forEach(x -> {
                    lock.notify();
                    try {
                        lock.wait();
                    } catch (InterruptedException ignored) {
                    }
                    System.out.println("Thread B " + x);
                });
                lock.notify();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(new ThreadA()).start();
        Thread.sleep(10);
        new Thread(new ThreadB()).start();
    }
}