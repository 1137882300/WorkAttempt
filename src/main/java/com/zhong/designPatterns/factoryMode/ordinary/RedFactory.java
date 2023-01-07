package com.zhong.designPatterns.factoryMode.ordinary;

import org.springframework.core.annotation.Order;

/**
 * 具体 工厂2
 */
@Order(4)
public class RedFactory implements ProjectFactory {

    @Override
    public Project getName() {
        return new RedPen();
    }
}
