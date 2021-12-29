package com.zhong.cache;

import com.zhong.entity.MultiLanguageString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/12/27 19:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryEntity {

    private Long categoryBackendId;
    private MultiLanguageString categoryName;
    private String platform;
    private String outerCategoryId;
    private String categoryCode;
    private Long parentId;
    private String iconUrl;
    private Integer status;
    private Integer isDeleted;
    private Long createTime;
    private Long updateTime;
    private Long creatorId;
    private Long modifiedId;
    private String features;
    private Integer level;
    private Long version;

    private CategoryEntity children;

}
