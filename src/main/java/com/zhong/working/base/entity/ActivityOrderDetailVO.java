package com.zhong.working.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: 竹阳
 * @date: 2022-09-23
 **/
@Data
public class ActivityOrderDetailVO {
    @ApiModelProperty("订单信息")
    private ActivityOrderInfoDTO orderInfo;
    @ApiModelProperty("产品信息")
    private ActivityOrderDetailProductInfoDTO productInfo;
    @ApiModelProperty("取消订单按钮，为空表示没有")
    private MuseumOrderDetailVO.OrderButtonVO cancelButton;
    @ApiModelProperty("防疫政策")
    private String remind;
    @ApiModelProperty("企微客服")
    private String wechatService;
    @ApiModelProperty("进群引导语")
    private String groupEntryGuide;

    @Data
    public static class ActivityOrderInfoDTO {
        private Integer orderId;
        @ApiModelProperty("订单号")
        private String orderNo;
        @ApiModelProperty("下单时间")
        private String orderTime;
        @ApiModelProperty("订单状态，例如：报名成功")
        private String statusLabel;
        @ApiModelProperty("成人数量")
        private Integer adult;
        @ApiModelProperty("儿童数量")
        private Integer child;
        @ApiModelProperty("联系人姓名")
        private String linkName;
        @ApiModelProperty("联系人手机号")
        private String linkPhone;
        @ApiModelProperty("联系人证件号类型，例如：身份证")
        private String linkCardType;
        @ApiModelProperty("联系人证件号")
        private String linkCardNo;
        @ApiModelProperty("订单金额，免费订单返回免费，付费金额返回12.3")
        private String amountLabel;
        @ApiModelProperty("是否免费活动  0-不是，1-是")
        private Integer free;
        @ApiModelProperty("出行人信息")
        private List<ActivityOrderMemberDTO> memberList;
    }

    @Data
    public static class ActivityOrderMemberDTO {
        @ApiModelProperty("姓名")
        private String realname;
        @ApiModelProperty("证件类型，例如：身份证")
        private String cardTypeLabel;
        @ApiModelProperty("证件号")
        private String idcard;
        @ApiModelProperty("手机号")
        private String phone;
        @ApiModelProperty("是否成人，1成人0儿童")
        private Integer adult;
    }

    @Data
    public static class ActivityOrderDetailProductInfoDTO {
        private Integer productId;
        @ApiModelProperty("名称")
        private String name;
        @ApiModelProperty("出行日期")
        private String dateLabel;
        @ApiModelProperty("商家电话")
        private String merchantTel;
        @ApiModelProperty("销售状态 1：在售中 -1：已停售")
        private Integer saleStatus;
    }

}
