package com.zhong.working.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: 竹阳
 * @date: 2022-09-12
 **/
@Data
public class CultureCalendarActivityRQ {
    @ApiModelProperty(value = "文化活动，选择的日期，yyyy-MM-dd")
    private String date;
    @ApiModelProperty(value = "页码", required = true)
    private Integer page;
    @ApiModelProperty(value = "类别，1博物馆展览 2文化活动", required = true)
    private Integer category;
    @ApiModelProperty(value = "文化活动用tab切换", required = false)
    private Integer type;
}
