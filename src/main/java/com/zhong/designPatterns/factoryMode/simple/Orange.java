package com.zhong.designPatterns.factoryMode.simple;

import org.springframework.core.annotation.Order;

@Order(2)
public class Orange implements IFruit {
    @Override
    public void eat() {
        System.out.println("吃橘子！");
    }
}
