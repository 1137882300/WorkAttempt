package com.zhong.designPatterns.strategyMode;

import org.springframework.core.annotation.Order;

/**
 * 处理 docx 的策略
 */
@Order(2)
public class OfficeHandlerDocxStrategy implements OfficeHandlerStrategy {

    @Override
    public void handlerOffice(String filePath) {
        System.out.println("处理docx");
    }
}
