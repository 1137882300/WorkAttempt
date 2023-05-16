package com.zhong.designPatterns.strategyMode.v1;

import org.springframework.core.annotation.Order;

/**
 * 策略的抽象
 */
@Order(1)
public interface OfficeHandlerStrategy {

    void handlerOffice(String filePath);

}
