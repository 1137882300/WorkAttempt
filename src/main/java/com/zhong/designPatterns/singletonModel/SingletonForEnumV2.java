package com.zhong.designPatterns.singletonModel;

/**
 * @author: juzi
 * @date: 2023/6/1
 * @desc: 推荐使用
 * 这种⽅式解决了最主要的；线程安全、⾃由串⾏化、单⼀实例
 * 此种⽅式在存在继承场景下是不可⽤的。
 */
public class SingletonForEnumV2 {

    private SingletonForEnumV2() {
    }

    private static SingletonForEnumV2 getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    private enum Singleton {
        INSTANCE;
        private final SingletonForEnumV2 instance;

        // JVM 保证这个方法绝对只调用一次
        Singleton() {
            instance = new SingletonForEnumV2();
        }

        public SingletonForEnumV2 getInstance() {
            return instance;
        }
    }
}
