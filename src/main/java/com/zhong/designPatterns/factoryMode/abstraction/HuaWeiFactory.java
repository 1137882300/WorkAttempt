package com.zhong.designPatterns.factoryMode.abstraction;

import org.springframework.core.annotation.Order;
/**
 * 具体工厂1
 */
@Order(3)
public class HuaWeiFactory implements Factory{


    @Override
    public PhoneProject projectPhone() {
        return new HuaWeiPhone();
    }

    @Override
    public LaptopProject projectLaptop() {
        return new HuaWeiLaptop();
    }
}
