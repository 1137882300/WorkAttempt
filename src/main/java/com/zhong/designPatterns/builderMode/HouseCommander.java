package com.zhong.designPatterns.builderMode;

import org.springframework.core.annotation.Order;

/**
 * 指挥者 指定制作流程 返回产品
 */
@Order(5)
public class HouseCommander {
    HouseBuilder houseBuilder = null;

    //构造器传入house builder
    public HouseCommander(HouseBuilder houseBuilder) {
        this.houseBuilder = houseBuilder;
    }

    //setter 传入house builder
    public void setHouseBuilder(HouseBuilder houseBuilder) {
        this.houseBuilder = houseBuilder;
    }

    //如何处理建造房子的流程，交给指挥者
    public House dealHouse() {
        houseBuilder.buildBasic();
        houseBuilder.buildWall();
        houseBuilder.roofed();
        return houseBuilder.buildHouse();
    }

}
