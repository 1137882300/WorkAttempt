package com.zhong.thread;

/**
 * @author: juzi
 * @date: 2023/5/18
 * @desc: 双重锁检查 - 单例模式
 */
public class Singleton {

    private static volatile Singleton instance; //  使用volatile关键字

    // 双重锁检验
    public static Singleton getInstance() {
        if (instance == null) { // 第7行
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton(); // 第10行
                }
            }
        }
        return instance;
    }
}
