package com.zhong.constants;

import lombok.*;

import java.io.Serializable;

/**
 * @date 2022/6/8 10:37
 */
public class QueryValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private String value;

    @Getter
    private String conditionOp;

    /**
     * 默认精确匹配
     */
    public QueryValue() {
        this.accurateMatch();
    }

    /**
     * 精确匹配，即 =
     *
     * @return
     */
    public QueryValue accurateMatch() {
        this.conditionOp = OperatorConstants.equals;
        return this;
    }

    /**
     * 模糊匹配，即 like
     *
     * @return
     */
    public QueryValue fuzzyMatch() {
        this.conditionOp = OperatorConstants.like;
        return this;
    }


}
