package com.zhong.working.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: 竹阳
 * @date: 2022-09-13
 **/
@Data
public class ScenicDetailVO {
    private Integer id;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("图片")
    private String image;
    @ApiModelProperty("图片列表")
    private List<String> imageList;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("防疫政策")
    private String remind;
    @ApiModelProperty("景区介绍")
    private String desc;
    @ApiModelProperty("入园须知")
    private String notice;
    @ApiModelProperty("VR跳转地址")
    private String vrUrl;
    @ApiModelProperty("语音讲解音频链接")
    private String guideUrl;
    @ApiModelProperty("坐标地点")
    private NoteDetailVO.PointVO point;
    @ApiModelProperty("是否可预约 1-可以，-1-不可以,2-无需预约")
    private Integer reserve;
    @ApiModelProperty("是否收费  -1-收费，1-免费")
    private Integer free;
    @ApiModelProperty("普通门票（后面弃用）")
    private List<TicketDTO> ticketList;
    @ApiModelProperty("演出门票（后面弃用）")
    private List<TicketDTO> showTicketList;
    @ApiModelProperty("是否显示打卡按钮")
    private Integer punchCardButton;
    @ApiModelProperty("是否已点去过")
    private Integer punchCard;
    @ApiModelProperty("是否已点想去")
    private Integer wantTo;
    @ApiModelProperty("营业执照名称")
    private String licenseName;
    @ApiModelProperty("营业执照图片路径")
    private String licenseUrl;
    @ApiModelProperty("分享标题")
    private String shareTitle;
    @ApiModelProperty("距离描述，例如：距我xxxkm")
    private String distanceDesc;
    @ApiModelProperty("门票组")
    private List<TicketGroupDTO> ticketGroupList;
    @ApiModelProperty("语音讲解")
    private List<ScenicGuideDTO> guideList;

    @Data
    public static class ScenicGuideDTO {
        private String name;
        private String image;
        private String url;
    }

    @Data
    public static class TicketGroupDTO {
        @ApiModelProperty("分类名称")
        private String name;
        @ApiModelProperty("门票列表")
        private List<TicketDTO> ticketList;
    }

    @Data
    public static class TicketDTO {
        private Integer id;
        @ApiModelProperty("标题")
        private String title;
        @ApiModelProperty("标签数组")
        private List<String> tagList;
        @ApiModelProperty("购买须知")
        private String notice;
        @ApiModelProperty("价格，例如：45.1")
        private String price;
        @ApiModelProperty("购买链接")
        private String purchaseUrl;
        @ApiModelProperty("划线价，例如：45.2")
        private String originPrice;
        @ApiModelProperty("红包描述，为空不展示")
        private String redPacket;
    }
}
