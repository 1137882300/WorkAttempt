package com.zhong.cache;

import com.zhong.entity.MultiLanguageString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @date 2022/3/4 17:51
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long currencyId;

    private MultiLanguageString currencyName;
    private String currencyAbbreviation;
    private String currencyCode;
    private String platform;
    private Integer status;
    private Long createTime;
    private Long updateTime;
    private Long creatorId;
    private Long modifiedId;
    private Long version;
    private Map<String,String> extendMap;
}
