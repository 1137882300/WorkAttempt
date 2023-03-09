package com.zhong.designPatterns.builderMode;

import org.springframework.core.annotation.Order;

@Order(6)
public class client {

    public static void main(String[] args) {
        CommonHouseBuilder commonHouseBuilder = new CommonHouseBuilder();
//        House house = new HouseCommander(commonHouseBuilder).dealHouse();
        House house = new HouseCommander(new HighHouseBuilder()).dealHouse();

        System.out.println(house);
    }
}
