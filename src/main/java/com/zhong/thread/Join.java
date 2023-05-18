package com.zhong.thread;

/**
 * @author: juzi
 * @date: 2023/5/18
 * @desc: join()方法是Thread类的一个实例方法。它的作用是让当前线程陷入“等待”状态，等join的这个线程执行完成后，再继续执行当前线程。
 * 有时候，主线程创建并启动了子线程，如果子线程中需要进行大量的耗时运算，主线程往往将早于子线程结束之前结束。
 * 如果主线程想等待子线程执行完毕后，获得子线程中的处理完的某个数据，就要用到join方法了。
 */
public class Join {
    static class ThreadA implements Runnable {

        @Override
        public void run() {
            try {
                System.out.println("我是子线程，我先睡一秒");
                Thread.sleep(1000);
                System.out.println("我是子线程，我睡完了一秒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @author juzi
     * @date 2023/5/18 下午 2:42
     * @description sleep方法是不会释放当前的锁的，而wait方法会
     * 它们还有这些区别：
     * wait可以指定时间，也可以不指定；而sleep必须指定时间。
     * wait释放cpu资源，同时释放锁；sleep释放cpu资源，但是不释放锁，所以易死锁。
     * wait必须放在同步块或同步方法中，而sleep可以再任意位置
     */
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new ThreadA());
        thread.start();
        thread.join();
        System.out.println("如果不加join方法，我会先被打出来，加了就不一样了");
    }
}