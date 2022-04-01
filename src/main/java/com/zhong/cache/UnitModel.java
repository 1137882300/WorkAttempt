package com.zhong.cache;

import com.zhong.entity.MultiLanguageString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @date 2022/3/4 18:34
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnitModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long unitId;

    private MultiLanguageString unitName;
    private Long unitCode;
    private Integer status;
    private String platform;
    private Long createTime;
    private Long updateTime;
    private Long creatorId;
    private Long modifiedId;
    private Long version;
    private Map<String,String> extendMap;


}
