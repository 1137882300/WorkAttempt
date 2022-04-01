package com.zhong.cache;

import com.zhong.entity.MultiLanguageString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @date 2022/3/4 11:14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyValueModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long propertyValueId;
    private MultiLanguageString propertyValueName;
    private String platform;
    private Integer status;
    private Long createTime;
    private Long updateTime;
    private Long creatorId;
    private Long modifiedId;
    private Long version;
}
