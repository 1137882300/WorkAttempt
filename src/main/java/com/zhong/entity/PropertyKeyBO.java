package com.zhong.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author haiqu
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyKeyBO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自定义输入值
     **/
    private String custom;
    /**
     * 关联属性id
     **/
    private Long propertyId;
    /**
     * 展示类型 1.文本输入 2.单选 3.多选 4.下拉选择
     **/
    private Integer valueType;
}
