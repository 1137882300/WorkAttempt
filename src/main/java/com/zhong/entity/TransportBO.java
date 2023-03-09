package com.zhong.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author haiqu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransportBO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 来源: 1自建(在商品库通过创建商品保存)，2行云优品(行云优品)
     **/
    Long itemSource;
    /**
     * 商品重量 单位G
     **/
    Float itemWeight;
    /**
     * 商品高默认CM
     **/
    Float itemHeight;
    /**
     * 商品长默认CM
     **/
    Float itemLength;
    /**
     * 商品宽默认CM
     **/
    Float itemWidth;
    /**
     * 商品毛重量 单位G
     **/
    Float grossWeight;
    /**
     * 商品毛高默认CM
     **/
    Float grossHeight;
    /**
     * 商品毛长默认CM
     **/
    Float grossLength;
    /**
     * 商品毛宽默认CM
     **/
    Float grossWidth;
    /**
     * 商品箱重量 单位G
     **/
    Float containerWeight;
    /**
     * 商品箱高默认CM
     **/
    Float containerHeight;
    /**
     * 商品箱长默认CM
     **/
    Float containerLength;
    /**
     * 商品箱宽默认CM
     **/
    Float containerWidth;
    /**
     * 箱规
     **/
    Long containerSpecs;
    /**
     * 商品箱毛重量 单位G
     **/
    String containerGrossWeight;
    /**
     * 发货时效（天）
     **/
    private String fromDay;
    /**
     * 发货地，发货仓id
     **/
    private String fromId;
    /**
     * 运费模板id
     **/
    private String templateId;
    /**
     * 退货地址id
     **/
    private String returnAddress;
    /**
     * 发货地
     **/
    private List<Long> fromCountryIds;
    /**
     * 到货地
     **/
    private List<Long> toCountryIds;

}
