package com.zhong.designPatterns.factoryMode.abstraction;

import org.springframework.core.annotation.Order;

/**
 * 具体工厂2
 */
@Order(3)
public class XiaomiFactory implements Factory{


    @Override
    public PhoneProject projectPhone() {
        return new XiaomiPhone();
    }

    @Override
    public LaptopProject projectLaptop() {
        return new XiaomiLaptop();
    }
}
