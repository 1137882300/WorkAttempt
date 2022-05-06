package com.zhong.aqs;

import java.util.concurrent.*;
/*
      使用场景：     可以用于多线程计算数据，最后合并计算结果的应用场景

 */

/**
 * @author Snailclimb
 * @date 2018年10月1日
 * @Description: 测试 CyclicBarrier 类中带参数的 await() 方法
 */
public class CyclicBarrierExample2 {
    // 请求的数量
    private static final int threadCount = 550;
    // 需要同步的线程数量,parties 就代表了有拦截的线程的数量，当拦截的线程数量达到这个值的时候就打开栅栏，让所有线程通过。
    // 每个线程调用 await() 方法告诉 CyclicBarrier 我已经到达了屏障，然后当前线程被阻塞。（意思就是被拦截了）
    private static final CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

    /**
     * 它要做的事情是：让一组线程到达一个屏障（也可以叫同步点）时被阻塞，直到最后一个线程到达屏障时，屏障才会开门，所有被屏障拦截的线程才会继续干活。
     */
    public static void main(String[] args) throws InterruptedException {
        // 创建线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            Thread.sleep(1000);
            threadPool.execute(() -> {
                try {
                    test(threadNum);
                } catch (InterruptedException | BrokenBarrierException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });
        }
        threadPool.shutdown();
    }

    public static void test(int threadnum) throws InterruptedException, BrokenBarrierException {
        System.out.println("threadnum: " + threadnum + " is ready");
        try {
            /**等待60秒，保证子线程完全执行结束**/ //等5个线程都完成，才继续往下执行
            cyclicBarrier.await(60, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println("-----CyclicBarrierException------");
        }
        System.out.println("threadnum: " + threadnum + "is finish");
    }

    /**
     * 高级方法
     * CyclicBarrier(int parties, Runnable barrierAction)
     * 意思：当线程达到屏障时，优先执行 barrierAction ，方便处理更复杂的业务场景
     */

}