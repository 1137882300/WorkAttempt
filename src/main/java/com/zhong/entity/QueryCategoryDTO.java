package com.zhong.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/12/17 14:02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryCategoryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** id **/
    Long categoryId;
    /** 外部类目id **/
    String outerCategoryId;
    /** 类目名称 **/
    MultiLanguageString categoryName;
    /**
     * 类目编号
     */
    String categoryCode;
    /**
     * 产品数量
     */
    Integer productNum;
    /**
     * 启用状态 0启用 1停用
     */
    Integer status;
    /**
     * 上级id
     *
     */
    Long parentId;
    /** 下级类目 **/
    List<QueryCategoryDTO> categories;
    /** 图片地址 **/
    String imageUrl;
    /** 数据版本号 **/
    Long version;
    /** 创建时间13位时间戳 **/
    Long createTime;
    /** 修改时间戳 **/
    Long updateTime;
    /** 创建人id **/
    Long creatorId;
    /** 修改人id **/
    Long modifiedId;
    /** 层级 **/
    Integer level;

    /**
     * 扩展信息
     * */
    Map<String,String> extendMap;
}