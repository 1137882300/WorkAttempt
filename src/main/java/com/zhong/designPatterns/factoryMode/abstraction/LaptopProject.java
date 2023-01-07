package com.zhong.designPatterns.factoryMode.abstraction;

import org.springframework.core.annotation.Order;

@Order(1)
public interface LaptopProject {

    void getId();
    void printInfo();

}
