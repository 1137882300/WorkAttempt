package com.zhong.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/11/3 17:57
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryCountryLocaleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long countryId;
    /**
     * 国别代码(海关代码)
     * @link https://www.zjport.gov.cn/query/param.shtml?loca=GBDQ
     */
    private String countryCode;
    /**
     * 国别图标
     */
    private String countryIcon;
    /**
     * 币种代码
     */
    @Deprecated
    private String currencyCode;
    /**
     * 国家名称
     */
    private MultiLanguageString countryName;
    /**
     * 国家2位简称
     */
    private String countryTwoAbbreviation;
    /**
     * 平台
     */
    private String platform;
    /**
     * 邮编
     */
    private Integer postCode;
    /**
     * 长途电话区号
     */
    private Integer phoneAreaCode;
    /**
     * 国别简称
     */
    @Deprecated
    private MultiLanguageString countryAbbreviation;
    /**
     * 国别全称
     */
    @Deprecated
    private String countryFullName;
    /**
     * 币种简称
     */
    @Deprecated
    private String currencyAbbreviation;

    /**
     * 状态
     */
    private Integer status;
    private Long createTime;
    private Long updateTime;
    private Long creatorId;
    private Long modifiedId;
    private Long version;
}
