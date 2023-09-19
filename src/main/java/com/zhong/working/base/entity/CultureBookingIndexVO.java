package com.zhong.working.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: 竹阳
 * @date: 2022-09-14
 **/
@Data
public class CultureBookingIndexVO {
    @ApiModelProperty("博物馆列表")
    private List<FlowMuseumVO> museumList;

    @Data
    public static class FlowMuseumVO {
        private Integer id;
        @ApiModelProperty("标题")
        private String title;
        @ApiModelProperty("副标题")
        private String subTitle;
        @ApiModelProperty("图片")
        private String image;
        @ApiModelProperty("距离当前定位，为空说明没有定位，例如2.2km")
        private String distance;
        @ApiModelProperty("经度")
        private String longitude;
        @ApiModelProperty("维度")
        private String latitude;
        @ApiModelProperty("小程序跳转信息")
        private MiniPageInfoVO miniPageInfo;
        @ApiModelProperty("目标类型 0自身,1-外部小程序,3-h5")
        private Integer targetType;
        @ApiModelProperty("h5链接")
        private String url;
        @ApiModelProperty("分类名称")
        private String categoryLabel;
        @ApiModelProperty("分类码")
        private String categoryCode;
        @ApiModelProperty("logo图")
        private String logo;
        @ApiModelProperty("标签")
        private List<String> tagList;
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
