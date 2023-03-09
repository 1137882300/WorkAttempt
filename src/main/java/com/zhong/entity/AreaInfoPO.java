package com.zhong.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 地区表
 * </p>
 *
 * @author hjh
 * @since 2021-11-22
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class AreaInfoPO implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String TABLE_NAME = "t_area_info";

    /**
     * 主键
     */
    private Long id;

    /**
     * 父级区划id
     */
    private Long parentId;

    /**
     * 国别id
     */
    private Long countryId;

    /**
     * 行政区划等级
     */
    private Integer areaLevel;

    /**
     * 平台Id
     */
    private String platform;

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

    /**
     * 是否有子类，否0，是1
     */
    private Integer hasChildren;

    /**
     * 邮编
     */
    private Integer postCode;


}
