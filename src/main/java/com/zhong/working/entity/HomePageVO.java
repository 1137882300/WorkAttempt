package com.zhong.working.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 竹阳
 * @date: 2022-09-12
 **/
@Data
public class HomePageVO {
    @ApiModelProperty("头部banner轮播")
    private List<FlowBannerVO> topBannerList;
    @ApiModelProperty("专题")
    private List<FlowBannerVO> subjectList;
    @ApiModelProperty("分享标题")
    private String shareTitle;
    @ApiModelProperty("分享图")
    private String shareImage;
    @ApiModelProperty("中间banner轮播")
    private List<FlowBannerVO> secondBannerList;
    @ApiModelProperty("文博预约banner")
    private FlowBannerVO cultureMuseumBanner;
    @ApiModelProperty("侧边栏banner")
    private FlowBannerVO homeSidebarBanner;
    @ApiModelProperty("home弹窗")
    private FlowBannerVO popupBanner;
    @ApiModelProperty("城市漫步+亚运体验+文博展览+文化活动")
    private List<HomeCityActivityDTO> cityActivity;
    @ApiModelProperty("必玩榜单")
    private HomeRankingDTO ranking;
    @ApiModelProperty("发现区县")
    private HomeCountyDTO county;
    @ApiModelProperty("行程定制")
    private FlowBannerVO travelCustomizationBanner;

    @Data
    public static class FlowBannerVO {
        private Integer id;
        @ApiModelProperty("标题")
        private String title;
        @ApiModelProperty("副标题")
        private String subTitle;
        @ApiModelProperty("图片")
        private String image;
        @ApiModelProperty("目标类型 1-外部小程序，2-小程序页面 ，3-h5")
        private Integer targetType;
        @ApiModelProperty("目标值")
        private String targetValue;
        @ApiModelProperty("内部小程序使用：广告类型  1-菜单、2-线路、3-商品、4-寺庙、5-博物馆、6-榜单、7-poi、8攻略、9-笔记、10-视频、11-资讯、12-公告、13-景区")
        private Integer type;
        @ApiModelProperty("内部小程序关联资源id")
        private String associateId;
        @ApiModelProperty("小程序跳转信息")
        private MiniPageInfoVO miniPageInfo;

        @Data
        public static class MiniPageInfoVO {
            private String appid;
            @ApiModelProperty("小程序类型 1-内部（不需要填写appid） 2-外部")
            private Integer type;
            @ApiModelProperty("小程序页面地址")
            private String url;
        }

    }
    @Data
    public static class HomeCountyDTO {
        private List<HomeCountyDetailDTO> countyList;
    }

    @Data
    public static class HomeCountyDetailDTO {
        private Integer id;
        private String title;
        private String subTitle;
        private String image;
    }

    @Data
    public static class HomeRankingDTO {
        private List<HomeRankingDetailDTO> rankingList;
    }

    @Data
    public static class HomeRankingDetailDTO {
        private Integer id;
        private String title;
        private List<HomeRankingPoiDTO> poiList;
    }

    @Data
    public static class HomeRankingPoiDTO {
        private Integer id;
        private String title;
        private String county;
        private String image;
        @ApiModelProperty("推荐理由")
        private String recommendReason;
        private String longitude;
        private String latitude;
        @ApiModelProperty("距离描述")
        private String distanceDesc;
    }

    @Data
    public static class HomeNearbyDTO {
        @ApiModelProperty("附近poi")
        private List<HomePoiDTO> poiList;
        @ApiModelProperty("玩法数量")
        private Integer playNum;
        @ApiModelProperty("头像")
        private List<String> avatarList;
        @ApiModelProperty("是否以市中心计算")
        private Integer downtown;
    }

    @Data
    public static class HomePoiDTO {
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
    public static class HomeCityActivityDTO {
        private Integer id;
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
        @ApiModelProperty("类型")
        private Integer type;
    }
}
