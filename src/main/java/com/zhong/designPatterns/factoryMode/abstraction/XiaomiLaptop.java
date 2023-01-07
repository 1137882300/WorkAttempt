package com.zhong.designPatterns.factoryMode.abstraction;

import org.springframework.core.annotation.Order;

@Order(4)
public class XiaomiLaptop implements LaptopProject {
    @Override
    public void getId() {
        System.out.println("小米ID");
    }

    @Override
    public void printInfo() {
        System.out.println("小米info");
    }
}
