package com.zhong.designPatterns.builderMode;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.core.annotation.Order;

/**
 * 产品 房子 实体
 */
@Order(1)
@Getter
@Setter
@ToString
public class House {

    private String basie;
    private String wall;
    private String roofed;

}
