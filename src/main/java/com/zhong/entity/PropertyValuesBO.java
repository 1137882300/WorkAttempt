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
public class PropertyValuesBO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 别名
     **/
    private String alias;
    /**
     * 自定义输入值
     **/
    private String custom;
    /**
     * 关联属性值id
     **/
    private Long relationId;
    /**
     * 属性值顺序
     **/
    private Integer order;
    /**
     * 当showType为 2 3 时 1为选中，0为未选中
     **/
    private Integer isSelected;
}
