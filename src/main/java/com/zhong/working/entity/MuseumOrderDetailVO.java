package com.zhong.working.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: 竹阳
 * @date: 2022-09-26
 **/
@Data
public class MuseumOrderDetailVO {
//    @ApiModelProperty("订单信息")
//    private MuseumOrderInfoDTO orderInfo;
    @ApiModelProperty("产品信息")
    private MuseumOrderDetailProductInfoDTO productInfo;
//    @ApiModelProperty("取消订单按钮，为空表示没有")
//    private OrderButtonVO cancelButton;
//    @ApiModelProperty("防疫政策")
//    private String remind;
    @Data
    public static class OrderButtonVO {
        @ApiModelProperty("按钮文案")
        private String buttonLabel;
        @ApiModelProperty("按钮状态，1显示可操作，点击提示confirmTip；2显示不可操作，点击提示canNotTip")
        private Integer showStatus;
        @ApiModelProperty("二次确认提示语，为空不提示")
        private String confirmTip;
        @ApiModelProperty("不可操作提示语，为空不提示")
        private String canNotTip;
    }

    @Data
    public static class MuseumOrderInfoDTO {
        @ApiModelProperty("订单id")
        private Integer orderId;
        @ApiModelProperty("订单号")
        private String orderNo;
        @ApiModelProperty("下单时间")
        private String orderTime;
        @ApiModelProperty("订单状态，例如：报名成功")
        private String statusLabel;
        @ApiModelProperty("出行日期")
        private String travelDate;
        @ApiModelProperty("时间场次")
        private String sessionTime;
        @ApiModelProperty("入场凭证url")
        private String marketEntryUrl;
        @ApiModelProperty("联系人姓名")
        private String linkName;
        @ApiModelProperty("联系人手机号")
        private String linkPhone;
        @ApiModelProperty("联系人证件号")
        private String linkCardNo;
    }

    @Data
    public static class MuseumOrderDetailProductInfoDTO {
        private Integer productId;
        @ApiModelProperty("名称")
        private String name;
        @ApiModelProperty("坐标地点")
        private NoteDetailVO.PointVO point;
    }

}
