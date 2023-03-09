package com.zhong.designPatterns.factoryMode.ordinary;

import org.springframework.core.annotation.Order;

/**
 * 抽象 工厂
 */
@Order(3)
public interface ProjectFactory {

    Project getName();

}
