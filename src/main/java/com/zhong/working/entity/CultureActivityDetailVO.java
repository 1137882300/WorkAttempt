package com.zhong.working.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: 竹阳
 * @date: 2022-09-21
 **/
@Data
public class CultureActivityDetailVO {
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
    @ApiModelProperty("类别，1博物馆展览 2文化活动")
    private Integer category;
    @ApiModelProperty("文化活动，小程序跳转信息")
    private MiniPageInfoVO miniPageInfo;
    @ApiModelProperty("博物馆展览，博物馆信息")
    private CultureActivityDetailMuseumDTO museumInfo;
    @ApiModelProperty("幻灯片")
    private List<String> imageList;

    @Data
    public static class CultureActivityDetailMuseumDTO {
        @ApiModelProperty("博物馆id")
        private Integer id;
//        @ApiModelProperty("小程序跳转信息")
//        private MiniPageInfoVO miniPageInfo;
        @ApiModelProperty("目标类型 0自身 1-外部小程序,3-h5")
        private Integer targetType;
        @ApiModelProperty("h5链接")
        private String url;
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
