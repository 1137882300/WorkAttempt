package com.zhong.working.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: 竹阳
 * @date: 2022-09-12
 * 广告位
 **/
@Data
public class FlowBannerVO {
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
