package com.zhong.designPatterns.strategyMode.v1;

import org.springframework.core.annotation.Order;

/**
 * 处理 ppt 的策略
 */
@Order(2)
public class OfficeHandlerPptxStrategy implements OfficeHandlerStrategy {

    @Override
    public void handlerOffice(String filePath) {
        System.out.println("处理ppt");
    }
}
