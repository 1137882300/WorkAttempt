package com.zhong.request;

import com.zhong.constants.OperatorValue;
import com.zhong.constants.QueryValue;
import lombok.*;
import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;

/**
 * @date 2022/6/7 17:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class QueryTagListRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long tagId;
    /**
     * 父标签ID
     */
    private Long parentTagId;
    /**
     * 标签code,全局唯一，注意：即使标签被删除,code也不能重复使用
     */
    private String tagCode;
    /**
     * 平台
     */
    private String platform;
    /**
     * 标签名称（多语言）
     */
    private Pair<String, OperatorValue> displayName;

    private QueryValue tagName;

    /**
     * 标签关联实体类型，1-商品，2-类目，3-账号，4-店铺，5-品牌
     * {@link com.xingyun.global.meta.domain.enums.EntityTypeEnum}
     */
    private Integer bizType;
    /**
     * 标签生效域， 1-全域，2-交易&详情，3-导购，4-卖家搜索
     * {@link com.xingyun.global.meta.domain.enums.ScopeEnum}
     */
    private Integer scope;


}
