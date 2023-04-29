package com.zhong.working.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: 竹阳
 * @date: 2022-09-26
 **/
@Data
public class OrderMuseumListVO {

    private List<OrderMuseumItemDTO> dataList;

    @Data
    public static class OrderMuseumItemDTO {
        @ApiModelProperty("订单id")
        private Integer orderId;
        @ApiModelProperty("名称")
        private String name;
        @ApiModelProperty("预约时间")
        private String dateLabel;
        @ApiModelProperty("出行人")
        private String linkName;
        @ApiModelProperty("图片")
        private String image;
        @ApiModelProperty("状态文案")
        private String statusLabel;
        @ApiModelProperty("目标类型 0自身 1-外部小程序，3-h5")
        private Integer targetType;
        @ApiModelProperty("目标值")
        private String targetValue;
        @ApiModelProperty("小程序跳转信息")
        private MiniPageInfoVO miniPageInfo;
    }
}
