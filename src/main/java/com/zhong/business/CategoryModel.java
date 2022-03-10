package com.zhong.business;

import com.zhong.entity.MultiLanguageString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/12/31 19:10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String rootId;

    private Long categoryBackendId;
    private Long parentId;
    private MultiLanguageString categoryName;
    private String platform;
    private String outerCategoryId;
    private String categoryCode;
    private String iconUrl;
    private Integer status;
    private Integer level;
    private Long createTime;
    private Long updateTime;
    private Long creatorId;
    private Long modifiedId;
    private Long version;
    private Map<String,String> extendMap;

    private List<CategoryModel> childrenList;

}
