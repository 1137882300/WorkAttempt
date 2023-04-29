package com.zhong.working.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 竹阳
 * @date: 2022-09-20
 **/
@Data
public class ScenicWeatherVO {
    @ApiModelProperty("天气信息")
    private CountyDetailVO.WeatherVO weather;
    @ApiModelProperty("景区人流量")
    private List<ScenicFlowDTO> scenicFlowList;
    @ApiModelProperty("景区人流量描述")
    private String scenicFlowLabel;

    @Data
    public static class ScenicFlowDTO {
        @ApiModelProperty("百分比0-100")
        private String percentage;
        @ApiModelProperty("是否是当前")
        private Integer current;
    }
}
