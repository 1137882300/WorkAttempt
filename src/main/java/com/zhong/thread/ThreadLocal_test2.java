package com.zhong.thread;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/12/27 11:12
 */
public class ThreadLocal_test2 {

    ThreadLocal<Long> longLocal = new ThreadLocal<>();

    public void set() {
        longLocal.set(Thread.currentThread().getId());
    }
    public long getLong() {
        return longLocal.get();
    }
    public static void main(String[] args) {
        ThreadLocal_test2 test = new ThreadLocal_test2();
        test.set();
        //注意:没有set之前，直接get，报null异常了
        System.out.println("-------threadLocal value-------" + test.getLong());
    }
}
