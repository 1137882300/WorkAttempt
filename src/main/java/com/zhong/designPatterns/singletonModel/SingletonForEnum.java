package com.zhong.designPatterns.singletonModel;

/**
 * @author: juzi
 * @date: 2023/6/1
 * @desc: 推荐使用
 * 这种⽅式解决了最主要的；线程安全、⾃由串⾏化、单⼀实例
 * 此种⽅式在存在继承场景下是不可⽤的。
 */
public enum SingletonForEnum {

    INSTANCE;

    public void test() {
        System.out.println("~");
    }
}
