package com.zhong.designPatterns.observerMode;

import org.springframework.core.annotation.Order;

/**
 * 具体的观察者
 */
@Order(2)
public class SpecificObserver implements Observer {

    private String name;

    SpecificObserver(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println(name + " 观察到消息：" + message);
    }
}
