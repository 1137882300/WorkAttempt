package com.zhong.working.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: 竹阳
 * @date: 2022-09-25
 **/
@Data
public class OrderLineListVO {

    private List<OrderLineItemDTO> dataList;

    @Data
    public static class OrderLineItemDTO {
        @ApiModelProperty("订单id")
        private Integer orderId;
        @ApiModelProperty("名称")
        private String name;
        @ApiModelProperty("图片")
        private String image;
        @ApiModelProperty("订单金额，免费订单返回免费，付费金额返回12.3")
        private String amountLabel;
        @ApiModelProperty("是否免费活动  0-不是，1-是")
        private Integer free;
        @ApiModelProperty("状态文案")
        private String statusLabel;
        @ApiModelProperty("出行人描述")
        private String memberLabel;
        @ApiModelProperty("日期")
        private String dateLabel;
        @ApiModelProperty("目标类型 0自身 1-外部小程序，3-h5")
        private Integer targetType;
        @ApiModelProperty("目标值")
        private String targetValue;
        @ApiModelProperty("小程序跳转信息")
        private MiniPageInfoVO miniPageInfo;
    }

}
