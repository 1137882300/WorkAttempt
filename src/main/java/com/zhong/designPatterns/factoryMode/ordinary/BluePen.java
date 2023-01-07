package com.zhong.designPatterns.factoryMode.ordinary;

import org.springframework.core.annotation.Order;

/**
 * 具体 产品
 */
@Order(2)
public class BluePen implements Project {

    @Override
    public void name() {
        System.out.println("这是一个蓝色的笔");
    }
}
