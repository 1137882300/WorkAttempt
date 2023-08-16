package com.zhong.enums;


import lombok.Getter;

/**
 * @author: juzi
 * @date: 2023/8/16
 * @desc:
 */
@Getter
public enum BooleanEnum {

    YES(1, Boolean.TRUE, "是"),
    NO(0, Boolean.FALSE, "否");

    private final Integer value;
    private final Boolean boo;
    private final String label;

    BooleanEnum(Integer value, Boolean boo, String label) {
        this.value = value;
        this.boo = boo;
        this.label = label;
    }

}
