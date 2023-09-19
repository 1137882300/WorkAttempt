package com.zhong.working.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: 竹阳
 * @date: 2022-09-27
 **/
@Data
public class MuseumBookingApproachTimeVO {
    @ApiModelProperty("入场时间选项")
    private List<MuseumBookingApproachTimeDTO> approachTimeList;

    @Data
    public static class MuseumBookingApproachTimeDTO {
        @ApiModelProperty("入场时间，例如：09:00-12:00")
        private String time;
        @ApiModelProperty("库存")
        private Integer stock;
    }
}
