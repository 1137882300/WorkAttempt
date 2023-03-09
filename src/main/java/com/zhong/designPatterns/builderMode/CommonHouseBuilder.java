package com.zhong.designPatterns.builderMode;

import org.springframework.core.annotation.Order;

/**
 * 具体的公共的建造者
 */
@Order(3)
public class CommonHouseBuilder extends HouseBuilder {

    @Override
    public void buildBasic() {
        System.out.println("common build basic");
    }

    @Override
    public void buildWall() {
        System.out.println("common build wall");
    }

    @Override
    public void roofed() {
        System.out.println("common roofed");
    }
}
