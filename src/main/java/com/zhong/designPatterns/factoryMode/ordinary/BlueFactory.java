package com.zhong.designPatterns.factoryMode.ordinary;

import org.springframework.core.annotation.Order;

/**
 * 具体 工厂1
 */
@Order(4)
public class BlueFactory implements ProjectFactory {

    @Override
    public Project getName() {
        return new BluePen();
    }
}
