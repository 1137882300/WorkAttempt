package com.zhong.working.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: 竹阳
 * @date: 2022-12-05
 **/
@Data
public class CultureMuseumShowDetailVO {
    private Integer id;
    @ApiModelProperty("发布者")
    private String publish;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("日期")
    private String dateLabel;
    @ApiModelProperty("开放时间")
    private String openTime;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("内容")
    private String content;
    @ApiModelProperty("价格")
    private String priceLabel;
    @ApiModelProperty("博物馆展览，博物馆信息")
    private CultureMuseumShowDetailDTO museumInfo;
    @ApiModelProperty("幻灯片")
    private List<String> imageList;
    @ApiModelProperty("坐标地点")
    private PointVO point;
    @ApiModelProperty("距离描述，例如：距我xxxkm")
    private String distanceDesc;
    @ApiModelProperty("统计数量")
    private StatInfoVO statInfo;
    @ApiModelProperty("偏好信息")
    private CultureMuseumShowDetailLookDTO lookInfo;
    @ApiModelProperty("用户喜好")
    private UserLikeVO userLike;
    @ApiModelProperty("截止时间")
    private String expLabel;

    @Data
    public static class PointVO {
        private Integer id;
        @ApiModelProperty("经度")
        private String longitude;
        @ApiModelProperty("纬度")
        private String latitude;
        @ApiModelProperty("地点名称")
        private String siteName;
        @ApiModelProperty("图片")
        private String image;
        @ApiModelProperty("地址")
        private String address;
    }

    @Data
    public static class CultureMuseumShowDetailDTO {
        @ApiModelProperty("博物馆id")
        private Integer id;
        @ApiModelProperty("博物馆名称")
        private String name;
        @ApiModelProperty("小程序跳转信息")
        private MiniPageInfoVO miniPageInfo;
        @ApiModelProperty("目标类型 0自身,1-外部小程序,3-h5")
        private Integer targetType;
        @ApiModelProperty("h5链接")
        private String url;
        @ApiModelProperty("博物馆在展数量")
        private Integer showNum;
    }

    @Data
    public static class UserLikeVO {
        @ApiModelProperty("当前用户是否已点赞1是0否")
        private Integer like;
        @ApiModelProperty("当前用户是否已收藏1是0否")
        private Integer favorite;
    }

    @Data
    public static class StatInfoVO {
        @ApiModelProperty("产品类型，1-poi,2-post(内容),3-免费/收费线路,4-景区,5-榜单,6-专题频道,7-博物馆,8-文创手办,9-博物馆展览,10-文化活动,11-酒店/民宿")
        private Integer type;
        @ApiModelProperty("相关联 id")
        private Integer associateId;
        @ApiModelProperty("浏览数量")
        private Integer viewCount;
        @ApiModelProperty("点赞数量")
        private Integer likeCount;
        @ApiModelProperty("收藏数量")
        private Integer favoriteCount;
        @ApiModelProperty("浏览数量超过万带单位万，例如：1.2万")
        private String viewCountUnit;
        @ApiModelProperty("点赞数量超过万带单位万，例如：1.2万")
        private String likeCountUnit;
        @ApiModelProperty("收藏数量超过万带单位万，例如：1.2万")
        private String favoriteCountUnit;
    }

    @Data
    public static class CultureMuseumShowDetailLookDTO {
        @ApiModelProperty("当前用户想看状态，1是0否")
        private Integer wantLook;
        @ApiModelProperty("当前用户看过状态，1是0否")
        private Integer alreadyLook;
        @ApiModelProperty("想看数量展示")
        private String wantLookCountUnit;
        @ApiModelProperty("看过数量展示")
        private String alreadyLookCountUnit;
    }

}
