package com.zhong.cache;

import com.zhong.entity.MultiLanguageString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2022/3/2 20:11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AreaModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long areaId;

    private Long parentId;
    private Long countryId;
    private Integer areaLevel;
    private String platform;
    private Long creatorId;
    private Long modifierId;
    private Long createTime;
    private Long updateTime;
    private Long version;
    private MultiLanguageString areaName;
    private Integer hasChildren;
    private Integer postCode;
    private Map<String,String> extendMap;

}
