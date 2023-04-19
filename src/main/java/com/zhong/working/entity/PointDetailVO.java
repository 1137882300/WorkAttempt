package com.zhong.working.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 竹阳
 * @date: 2022-09-14
 **/
@Data
public class PointDetailVO {
    private Integer id;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("图片数组")
    private List<String> imageList;
    @ApiModelProperty("标签数组")
    private List<String> tagList;
    @ApiModelProperty("简介")
    private String introduction;
    @ApiModelProperty("开放时间")
    private String openTime;
    @ApiModelProperty("是否开放 0：关闭 1：开放")
    private Integer openStatus;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("经度")
    private String longitude;
    @ApiModelProperty("纬度")
    private String latitude;
    @ApiModelProperty("用户喜好")
    private UserLikeVO userLike;
    @ApiModelProperty("人均")
    private String average;
    @ApiModelProperty("关键字")
    private List<String> keywordList;
    @ApiModelProperty("等级")
    private String level;
    @ApiModelProperty("交通攻略")
    private String traffic;
    @ApiModelProperty("出行贴士")
    private String notice;
    @ApiModelProperty("电话")
    private String phone;
    @ApiModelProperty("预约信息")
    private PointDetailBookDTO book;
    @ApiModelProperty("语音url")
    private String voice;
    @ApiModelProperty("分享标题")
    private String shareTitle;
    @ApiModelProperty("是否已点去过")
    private Integer punchCard;
    @ApiModelProperty("是否已点想去")
    private Integer wantTo;
    @ApiModelProperty("分类id")
    private Integer categoryId;
    @ApiModelProperty("距离描述，例如：距我xxxkm")
    private String distanceDesc;
    @ApiModelProperty("推荐玩法")
    private List<PointDetailContentDTO> playContentList;
    @ApiModelProperty("语音讲解")
    private List<PointGuideDTO> guideList;

    @Data
    public static class PointGuideDTO {
        private String name;
        private String image;
        private String url;
    }

    @Data
    public static class PointDetailContentDTO {
        private Integer id;
        @ApiModelProperty("类型1：攻略 2：短笔记 3：视频")
        private Integer type;
        private String title;
        private String image;
        @ApiModelProperty("视频url")
        private String videoUrl;//TODO YU
        @ApiModelProperty("动图")
        private String gifImage;
    }
    @Data
    public static class UserLikeVO {
        @ApiModelProperty("当前用户是否已点赞1是0否")
        private Integer like;
        @ApiModelProperty("当前用户是否已收藏1是0否")
        private Integer favorite;
    }
    @Data
    public static class PointDetailBookDTO {
        private Integer id;
        @ApiModelProperty("类型 1景区 3博物馆 5免费线路 6收费线路 7酒店 8民宿")
        private Integer type;
        @ApiModelProperty("目标类型 0自身 1-外部小程序，3-h5")
        private Integer targetType;
//        @ApiModelProperty("小程序跳转信息")
//        private MiniPageInfoVO miniPageInfo;
        @ApiModelProperty("h5目标类型跳转链接")
        private String url;
    }
}
