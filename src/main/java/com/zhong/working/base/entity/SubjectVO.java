package com.zhong.working.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 竹阳
 * @date: 2022-09-12
 **/
@Data
public class SubjectVO {
    @ApiModelProperty("专题code")
    private String code;
    @ApiModelProperty("标题，例如：趣溜娃")
    private String title;
    @ApiModelProperty("副标题，例如：神兽出游秘籍")
    private String subTitle;
    @ApiModelProperty("标签，例如['放飞童真','轻松带娃']")
    private List<String> tagList;
    @ApiModelProperty("感兴趣人数，例如1234")
    private String interestPeople;
    @ApiModelProperty("感兴趣人头像")
    private List<String> interestAvatar;
    @ApiModelProperty("榜单")
    private List<RankingVO> rankingList;
    @ApiModelProperty("活动信息")
    private SubjectActivityDTO activitInfo;
    @ApiModelProperty("亚运体验点")
    private SubjectNearbyDTO nearby;
    @ApiModelProperty("亚运纪念品")
    private List<SubjectGoodsDTO> goodsList;
    @ApiModelProperty("亚运体验线路")
    private List<SubjectPoiRouteDTO> routeList;


    @Data
    public static class RankingVO {
        @ApiModelProperty("标题，例如：十大亲子拍照地")
        private String title;
        @ApiModelProperty("副标题，例如：留下精彩瞬间")
        private String subTitle;
        @ApiModelProperty("图片")
        private String image;
        @ApiModelProperty("类型，0榜单列表，>0为指定榜单")
        private Integer type;
        @ApiModelProperty("榜单类型 0榜单列表 1：POI榜单 2：攻略榜单 3:线路榜单 4:手伴榜单 5:榜单合集")
        private Integer rankingType;
    }
    @Data
    public static class SubjectPoiRouteDTO {
        private Integer id;
        @ApiModelProperty("标题")
        private String title;
        @ApiModelProperty("图片")
        private String image;
    }

    @Data
    public static class SubjectGoodsDTO {
        private Integer id;
        @ApiModelProperty("标题")
        private String title;
        @ApiModelProperty("图片")
        private String image;
        @ApiModelProperty("价格，例如：32.1")
        private String price;
    }

    @Data
    public static class SubjectNearbyDTO {
        @ApiModelProperty("附近poi")
        private List<SubjectPoiDTO> poiList;
        @ApiModelProperty("玩法数量")
        private Integer playNum;
        @ApiModelProperty("头像")
        private List<String> avatarList;
        @ApiModelProperty("是否以市中心计算")
        private Integer downtown;
    }

    @Data
    public static class SubjectPoiDTO {
        private Integer id;
        private String longitude;
        private String latitude;
        private String title;
        private String subTitle;
        private String image;
        @ApiModelProperty("距离描述")
        private String distanceDesc;
    }

    @Data
    public static class SubjectActivityDTO {
        @ApiModelProperty("本周活动")
        private SubjectActivityInfoDTO week;
        @ApiModelProperty("下周活动")
        private SubjectActivityInfoDTO nextWeek;
    }

    @Data
    public static class SubjectActivityInfoDTO {
        private Integer id;
        @ApiModelProperty("标题")
        private String title;
        @ApiModelProperty("图片")
        private String image;
        @ApiModelProperty("体验日期")
        private String dateLabel;
        @ApiModelProperty("上限人数")
        private Integer limitCount;
        @ApiModelProperty("是否已经订阅了提醒，1是0否")
        private Integer remind;
        @ApiModelProperty("报名开始时间，yyyy-MM-dd HH:mm:ss")
        private String enterAt;
        @ApiModelProperty("剩余名额数量")
        private Integer surplusNum;
        @ApiModelProperty("报名截止时间，yyyy-MM-dd HH:mm:ss")
        private String expiryAt;
        @ApiModelProperty("是否已截止1是0否")
        private Integer expiry;
        @ApiModelProperty("销售状态 1：在售中 -1：已停售")
        private Integer saleStatus;
    }

}
