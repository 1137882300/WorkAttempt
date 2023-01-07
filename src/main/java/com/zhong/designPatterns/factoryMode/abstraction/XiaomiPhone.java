package com.zhong.designPatterns.factoryMode.abstraction;

import org.springframework.core.annotation.Order;

@Order(5)
public class XiaomiPhone implements PhoneProject {
    @Override
    public void getId() {
        System.out.println("小米手机ID");
    }

    @Override
    public void printInfo() {
        System.out.println("小米手机info");
    }
}
