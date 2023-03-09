package com.zhong.constants;

import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

/**
 * @date 2022/6/8 10:15
 */
@Data
public class OperatorValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    private String conditionOp;

    public OperatorValue() {
        this.accurateMatch();
    }

    /**
     * 精确匹配，即 =
     *
     * @return
     */
    public OperatorValue accurateMatch() {
        this.conditionOp = OperatorConstants.equals;
        return this;
    }

    /**
     * 模糊匹配，即 like
     *
     * @return
     */
    public OperatorValue fuzzyMatch() {
        this.conditionOp = OperatorConstants.like;
        return this;
    }


}
