package com.zhong.working.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: 竹阳
 * @date: 2022-09-27
 **/
@Data
public class ScenicFilterQueryRQ {
    @ApiModelProperty(value = "页码", required = true)
    private Integer page;
    @ApiModelProperty("区县id")
    private Integer countyId;
    @ApiModelProperty("星级")
    private Integer startId;
    @ApiModelProperty("是否可预订，1可预订")
    private Integer reserve;
    @ApiModelProperty("分类id")
    private Integer categoryId;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("免费游页面使用传1")
    private Integer free;
    @ApiModelProperty("poi标签id")
    private Integer poiTagId;
}
