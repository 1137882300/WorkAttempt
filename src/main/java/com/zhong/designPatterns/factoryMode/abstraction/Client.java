package com.zhong.designPatterns.factoryMode.abstraction;

import org.springframework.core.annotation.Order;

/**
 * 定义：为创建一组相关或相互依赖的对象提供一个接口，而且无需指定他们的具体类。
 */
@Order(6)
public class Client {
    public static void main(String[] args) {
        PhoneProject huawei = new HuaWeiFactory().projectPhone();
        huawei.printInfo();
        huawei.getId();

        LaptopProject huawei1 = new HuaWeiFactory().projectLaptop();
        huawei1.printInfo();

        PhoneProject xiaomi = new XiaomiFactory().projectPhone();
        xiaomi.printInfo();

    }

}
