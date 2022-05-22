package com.zhong.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @date 2022/5/13 17:37
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DivisionInfoPO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long parentId;

    private String divisionName;

    private Integer divisionLevel;

    private Long creatorId;

    private Long modifierId;

    private Long isDeleted;

    private Long version;

    private String tenantPlatform;

    private Long createTime;

    private Long updateTime;

    private Integer hasChildren;


}
