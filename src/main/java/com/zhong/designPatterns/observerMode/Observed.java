package com.zhong.designPatterns.observerMode;

import org.springframework.core.annotation.Order;

/**
 * 被观察者
 * 抽象一层
 */
@Order(3)
public interface Observed {

    //添加观察者
    void addObserver(Observer observer);

    //删除观察者
    void deleteObserver(Observer observer);

    //通知所有的观察者有更新
    void notifyObservers(String message);


}
