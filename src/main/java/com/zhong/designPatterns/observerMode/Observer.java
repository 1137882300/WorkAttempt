package com.zhong.designPatterns.observerMode;

import org.springframework.core.annotation.Order;

/**
 * 观察者
 * 抽象一层
 */
@Order(1)
public interface Observer {

    void update(String message);

}
