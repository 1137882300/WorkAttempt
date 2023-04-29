package com.zhong.working.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: 竹阳
 * @date: 2022-09-12
 **/
@Data
public class SubjectFlowRQ {
    @ApiModelProperty(value = "专题id", required = true)
    private Integer id;
    @ApiModelProperty(value = "页码", required = true)
    private Integer page;
}
