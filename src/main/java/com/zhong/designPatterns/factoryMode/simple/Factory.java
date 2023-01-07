package com.zhong.designPatterns.factoryMode.simple;

import org.springframework.core.annotation.Order;

@Order(3)
public class Factory {

    public static void main(String[] args) {
        // 吃苹果！
        IFruit apple = new Apple();
        apple.eat();

        IFruit orange = new Orange();
        orange.eat();
    }
}
