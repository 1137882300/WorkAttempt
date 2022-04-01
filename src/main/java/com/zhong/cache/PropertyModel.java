package com.zhong.cache;

import com.zhong.entity.MultiLanguageString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2022/2/25 10:36
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long propertyId;

    private String platform;
    private String outerPropertyId;
    private Boolean extendable;
    private Boolean searchable;
    private Integer propertyType;
    private MultiLanguageString propertyName;
    private Integer valueType;
    private List<PropertyValueModel> propertyValues;
    private Integer status;
    private Long createTime;
    private Long updateTime;
    private Long creatorId;
    private Long modifiedId;
    private Long version;

}
