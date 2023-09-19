package com.zhong.working.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 竹阳
 * @date: 2023-01-17
 **/
@Data
public class RouteDetailVO {
    private Integer id;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("图片")
    private String image;
    @ApiModelProperty("天列表")
    private List<RouteDetailDayDTO> dayList;

    @Data
    public static class RouteDetailDayDTO {
        @ApiModelProperty("第几天")
        private Integer day;
        @ApiModelProperty("数据")
        private List<RouteDetailDayItemDTO> list = new ArrayList<>();
    }

    @Data
    public static class RouteDetailDayItemDTO {
        @ApiModelProperty("第几天")
        private Integer day;
        @ApiModelProperty("标题")
        private String title;
        @ApiModelProperty("图片")
        private String image;
        @ApiModelProperty("简介")
        private String introduction;
        @ApiModelProperty("游玩小时数")
        private String hours;
    }

}
