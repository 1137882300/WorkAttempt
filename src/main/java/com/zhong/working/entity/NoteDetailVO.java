package com.zhong.working.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 竹阳
 * @date: 2022-09-13
 **/
@Data
public class NoteDetailVO {
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("图片")
    private List<String> imageList;
    @ApiModelProperty("地点")
    private List<PointVO> poiList;
    @ApiModelProperty("内容")
    private String content;
    @ApiModelProperty("发布时间，例如：2022-10-01")
    private String publishDate;
    @ApiModelProperty("来源作者")
    private String source;
    @ApiModelProperty("头像")
    private String avatar;
    @ApiModelProperty("用户喜好")
    private UserLikeVO userLike;
    @ApiModelProperty("统计数量")
    private StatInfoVO statInfo;


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

}