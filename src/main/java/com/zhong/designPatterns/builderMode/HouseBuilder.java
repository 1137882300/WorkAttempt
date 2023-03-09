package com.zhong.designPatterns.builderMode;

import org.springframework.core.annotation.Order;

/**
 * 房子的建造者
 */
@Order(2)
public abstract class HouseBuilder {
    protected House house = new House();

    //将建造的流程写好，抽象的方法
    public abstract void buildBasic();

    public abstract void buildWall();

    public abstract void roofed();

    //建造
    public House buildHouse() {
        return house;
    }
}
