package com.zhong.lock;

/**
 * @author: juzi
 * @date: 2023/5/18
 * @desc: java多线程的锁都是基于对象的，Java中的每一个对象都可以作为一个锁。
 * 还有一点需要注意的是，我们常听到的[类锁]其实也是[对象锁]。
 * Java类只有一个Class对象（可以有多个实例对象，多个实例共享这个Class对象），而Class对象也是特殊的Java对象。所以我们常说的类锁，其实就是Class对象的锁。
 */
public class SynchronizedDemo {


    /**
     * @author juzi
     * @date 2023/5/18 下午 6:34
     * @description 锁为当前实例
     */
    // 关键字在实例方法上，锁为当前实例
    public synchronized void instanceLock1() {
        // code
    }

    // 关键字在代码块上，锁为括号里面的对象
    public void instanceLock2() {
        synchronized (this) {
            // code
        }
    }

    // 关键字在代码块上，锁为括号里面的对象
    public void instanceLock3() {
        Object o = new Object();
        synchronized (o) {
            // code
        }
    }


    /**
     * @author juzi
     * @date 2023/5/18 下午 6:37
     * @description 锁为当前Class对象
     */

    // 关键字在静态方法上，锁为当前Class对象
    public static synchronized void classLock1() {
        // code
    }

    // 关键字在代码块上，锁为括号里面的对象
    public void classLock2() {
        synchronized (this.getClass()) {
            // code
        }
    }


}
