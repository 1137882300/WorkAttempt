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
public class WholesalePriceBO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 价格
     **/
    String price;
    /**
     * 最大商品数 ，无最大值字段表示无限
     **/
    Long maxSize;
    /**
     * 最小商品数量
     **/
    Long minSize;

    /**
     * 比例来源： 类目继承：category、商品发布：item
     **/
    private String percentSource;
    /**
     * 加价比例：0.08
     **/
    private String addPercent;
}
