package com.zhong.designPatterns.factoryMode.abstraction;

import org.springframework.core.annotation.Order;

@Order(4)
public class HuaWeiLaptop implements LaptopProject {
    @Override
    public void getId() {
        System.out.println("华为ID");
    }

    @Override
    public void printInfo() {
        System.out.println("华为info");
    }
}
