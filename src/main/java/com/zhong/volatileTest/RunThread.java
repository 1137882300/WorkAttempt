package com.zhong.volatileTest;

/**
 * @date 2022/8/11 10:16
 */
public class RunThread extends Thread {
    /**
     * volatile
     */
//    private volatile boolean isRunning = true;
    private boolean isRunning = true;

    private void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    public void run() {
        System.out.println("进入 run() 方法中...");
        while (isRunning) {
//            System.out.println("正在执行任务！");
        }
        System.out.println("线程结束了...");
    }

    public static void main(String[] args) throws InterruptedException {
        RunThread myThread = new RunThread();
        myThread.start();
        Thread.sleep(4000);
        myThread.setRunning(false);
        System.out.println("isRunning 的值已经设置为了 false");
        Thread.sleep(1000);
        System.out.println(myThread.isRunning);
    }
    /**
     * 当修饰：volatile， isRunning 设置成了false 就直接结束了。线程能直接感知到变量的变化
     * 没用 volatile时， 程序就正常执行到结束，线程感知不到
     */
}