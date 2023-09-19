package com.zhong.working.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 竹阳
 * @date: 2022-09-26
 **/
@Data
public class CultureMuseumDetailVO {
    private Integer id;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("图片")
    private String image;
    @ApiModelProperty("商家电话")
    private String merchantTel;
    @ApiModelProperty("标签")
    private List<String> tagList;
    @ApiModelProperty("坐标地点")
    private PointVO point;
    @ApiModelProperty("参观须知")
    private String visitingInformation;
    @ApiModelProperty("展览开放信息")
    private String openingInformation;
    @ApiModelProperty("预约窗口开始时间")
    private String windowOpenTime;
    @ApiModelProperty("预约窗口结束时间")
    private String windowCloseTime;
    @ApiModelProperty("距离描述，例如：距我xxxkm")
    private String distanceDesc;
    @ApiModelProperty("介绍")
    private String introduce;
    @ApiModelProperty("目标类型 0自身,1-外部小程序,3-h5")
    private Integer targetType;
    @ApiModelProperty("小程序跳转信息")
    private MiniPageInfoVO miniPageInfo;
    @ApiModelProperty("h5链接")
    private String url;
    @ApiModelProperty("封面图")
    private String cover;

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
    public static class MiniPageInfoVO {
        private String appid;
        @ApiModelProperty("小程序类型 1-内部（不需要填写appid） 2-外部")
        private Integer type;
        @ApiModelProperty("小程序页面地址")
        private String url;
    }

}
