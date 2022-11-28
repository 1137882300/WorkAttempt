package com.zhong.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author YangLiu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkuPriceBO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 建议采购价类型
     **/
    String suggestedPriceType;
    /**
     * 零售价
     **/
    private String retailPrice;
    /**
     * 建议采购价
     **/
    private String suggestedPrice;
    /**
     * 划线价
     **/
    private String lineationPrice;
    /**
     * 最低销售价
     **/
    private String lowestRetailPrice;
    /**
     * 成本价
     **/
    private String costPrice;
    /**
     * 商品单位
     **/
    @Deprecated
    private String unit;
    /**
     * 币种 ： USD
     **/
    private String currency;
    /**
     * 阶梯批发价
     **/
    private List<WholesalePriceBO> wholesalePrice;
    /**
     * 分销收益方式类型  1.保持收益比例不变，2.保持代购价不变
     **/
    private String distributePriceType;
    /**
     * 分销加价类型 1.百分比，2.固定加价
     **/
    private String distributeIncomeType;
    /**
     * 分销加价明细  例子： 百分比：0.15， 固定加价：15 （单位元）
     **/
    private String distributeIncomeDetail;
}
