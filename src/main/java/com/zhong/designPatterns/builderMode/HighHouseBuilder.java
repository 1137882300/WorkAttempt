package com.zhong.designPatterns.builderMode;

import org.springframework.core.annotation.Order;

/**
 * 具体的建造者
 */
@Order(4)
public class HighHouseBuilder extends HouseBuilder {
    @Override
    public void buildBasic() {
        System.out.println("high house build basic");
    }

    @Override
    public void buildWall() {
        System.out.println("high build wall");
    }

    @Override
    public void roofed() {
        System.out.println("high roofed");
    }
}
