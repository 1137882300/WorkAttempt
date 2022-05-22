package com.zhong.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 地区多语言表
 * </p>
 *
 * @author hjh
 * @since 2021-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AreaInfoLanguagePO implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String TABLE_NAME = "t_area_info_language";

    /**
     * 主键
     */
    private Long id;

    /**
     * 地区表id
     */
    private Long areaInfoId;

    /**
     * 区划名称
     */
    private String areaName;

    /**
     * 语言地区：zh_CN 中国大陆
     */
    private String preferredLanguage;

    /**
     * 创建人id
     */
    private Long creatorId;

    /**
     * 修改人id
     */
    private Long modifiedId;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;

    /**
     * 是否删除，否0，是1
     */
    private Integer isDeleted;

    /**
     * 版本号
     */
    private Long version;

    /**
     * 预留后期的拓展字段，json格式
     */
    private String features;


}
