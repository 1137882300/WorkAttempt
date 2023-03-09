package com.zhong.designPatterns.factoryMode.abstraction;

import org.springframework.core.annotation.Order;

/**
 * 抽象工厂
 */
@Order(2)
public interface Factory {

    PhoneProject projectPhone();

    LaptopProject projectLaptop();

}
