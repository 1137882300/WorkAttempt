package com.zhong.designPatterns.observerMode;

import org.springframework.core.annotation.Order;

/**
 * 观察者模式定义：定义了对象之间的一对多依赖，这样一来，当一个对象改变状态时，它的所有依赖者都会收到通知并自动更新
 */
@Order(5)
public class Client {
    public static void main(String[] args) {

        Observed observed = new SpecificObserved();

        //创建观察者
        Observer observer1 = new SpecificObserver("1号选手");
        Observer observer2 = new SpecificObserver("2号选手");
        Observer observer3 = new SpecificObserver("3号选手");

        //订阅
        observed.addObserver(observer1);
        observed.addObserver(observer2);
        observed.addObserver(observer3);

        //发出通知
        observed.notifyObservers("新消息");

        //删除创建者
        observed.deleteObserver(observer2);

        observed.notifyObservers("刚删除了一个");
    }
}
