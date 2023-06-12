package com.zhong.designPatterns.singletonModel;

/**
 * @author: juzi
 * @date: 2023/6/1
 * @desc: 推荐使用的单例模式
 * 使⽤类的静态内部类实现的单例模式，既保证了线程安全有保证了懒加载，同时不会因为加锁的⽅式耗费性能。
 * 这主要是因为JVM虚拟机可以保证多线程并发访问的正确性，也就是⼀个类的构造⽅法在多线程环境下可以被正确的加载。
 * 此种⽅式也是⾮常推荐使⽤的⼀种单例模式
 */
public class SingletonForInner {

    private static class SingletonHolder {
        private static final SingletonForInner instance = new SingletonForInner();
    }

    private SingletonForInner() {
    }

    public static SingletonForInner getInstance() {
        return SingletonHolder.instance;
    }

}
