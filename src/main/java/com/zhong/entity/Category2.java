package com.zhong.entity;

import com.zhong.cache.CategoryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/12/31 11:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category2 implements Serializable {

    private Long rootId;

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
    private Map<String,String> extendMap;

    private List<Category2> children;





}
