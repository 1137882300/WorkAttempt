package com.zhong.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author haiqu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkuPropertyBO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 属性key
     **/
    private PropertyKeyBO key;
    /**
     * values
     **/
    private PropertyValuesBO value;
    /**
     * 展示顺序
     **/
    private Integer order;
}
