package com.zhong.designPatterns.strategyMode;

import org.springframework.core.annotation.Order;

import java.util.HashMap;
import java.util.Map;

/**
 * 策略工厂
 */
@Order(3)
public class OfficeHandlerStrategyFactory {

    private static final Map<String, OfficeHandlerStrategy> map = new HashMap<>();

    static {
        map.put("docx", new OfficeHandlerDocxStrategy());
        map.put("xlsx", new OfficeHandlerXlsxStrategy());
        map.put("pptx", new OfficeHandlerPptxStrategy());
    }

    public static OfficeHandlerStrategy getStrategy(String type) {
        return map.get(type);
    }

}
