package com.zhong.entity;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author haiqu
 */
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ItemBaseLanguagePO implements Serializable {

    /**
     * 主键id
     **/
    private Long itemLanId;
    /**
     * 商品id
     **/
    private Long itemId;
    /**
     * 店铺id
     **/
    private Long shopId;
    /**
     * 商品标题
     **/
    private String title;
    /**
     * 商品副标题
     **/
    private String subTitle;
    /**
     * 商品型号
     **/
    private String itemType;
    /**
     * 商品属性信息
     **/
    private String propertyInfo;
    /**
     * 商品销售属性信息
     **/
    private String salePropertyExtend;
    /**
     * 语言地区：zh_CN 中国大陆
     **/
    private String preferredLanguage;
    /**
     * 是否删除 0 未删除 1 删除
     **/
    private Integer isDeleted;
    /**
     * 创建时间13位时间戳
     **/
    private Long createTime;
    /**
     * 修改时间戳
     **/
    private Long updateTime;
    /**
     * 系统修改时间戳
     **/
    private Long systemUpdateTime;
    /**
     * 创建人id
     **/
    private Long creatorId;
    /**
     * 修改人id
     **/
    private Long modifiedId;
    /**
     * 商品预留后期 K V 结构字段
     **/
    private String features;

    private Long version;
}
