package com.zhong.designPatterns.factoryMode.ordinary;

import org.springframework.core.annotation.Order;

/**
 * 产品类 抽象
 */
@Order(1)
public interface Project {

    void name();

}
