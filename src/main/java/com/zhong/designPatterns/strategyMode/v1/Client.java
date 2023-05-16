package com.zhong.designPatterns.strategyMode.v1;

import org.springframework.core.annotation.Order;

@Order(4)
public class Client {

    public static void main(String[] args) {
        String filePath = "C://file/123.xlsx";
        String type = getFileExtension(filePath);
        OfficeHandlerStrategy strategy = OfficeHandlerStrategyFactory.getStrategy(type);
        strategy.handlerOffice(filePath);
    }

    private static String getFileExtension(String filePath) {
        // 解析文件名获取文件扩展名,比如 文档.docx，返回 docx
        return filePath.substring(filePath.lastIndexOf(".") + 1);
    }

}
