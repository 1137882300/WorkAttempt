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
public class DiscoverIndexVO {

    @ApiModelProperty("城市列表")
    private List<DiscoverCountyDTO> countyList;
    @ApiModelProperty("默认选中的区县id")
    private Integer defaultCountyId;
    @ApiModelProperty("分享标题")
    private String shareTitle;
    @ApiModelProperty("分享图")
    private String shareImage;

    @Data
    public static class DiscoverCountyDTO {
        private Integer id;
        @ApiModelProperty("名称，例如：建德市")
        private String name;
        @ApiModelProperty("图片")
        private String image;
        @ApiModelProperty("描述，例如：高楼新住客，古建老街灯")
        private String desc;
        @ApiModelProperty("poi列表")
        private List<PointVO> poiList;
        @ApiModelProperty("景区列表")
        private List<DiscoverCountyScenicDTO> scenicList;
    }

    @Data
    public static class DiscoverCountyScenicDTO{
        @ApiModelProperty("标题")
        private String title;
        @ApiModelProperty("图片")
        private String image;
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
