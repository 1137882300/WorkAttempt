package com.zhong.designPatterns.strategyMode.v1;

import org.springframework.core.annotation.Order;

/**
 * 处理 xlsx 的策略
 */
@Order(2)
public class OfficeHandlerXlsxStrategy implements OfficeHandlerStrategy {

    @Override
    public void handlerOffice(String filePath) {
        System.out.println("处理xlsx");
    }
}
