package com.zhong.designPatterns.factoryMode.ordinary;

import org.springframework.core.annotation.Order;

/**
 * 普通工厂模式
 */
@Order(5)
public class Client {
    public static void main(String[] args) {
        Project red = new RedFactory().getName();
        red.name();

        Project blue = new BlueFactory().getName();
        blue.name();
    }
}
