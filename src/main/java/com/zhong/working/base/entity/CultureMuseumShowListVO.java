package com.zhong.working.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 竹阳
 * @date: 2022-12-06
 **/
@Data
public class CultureMuseumShowListVO {
    @ApiModelProperty("时间筛选，page=1时返回")
    private List<TabVO> timeTabList;
    @ApiModelProperty("列表")
    private List<DayActivityVO> dataList;

    @Data
    public static class TabVO {
        private String title;
        private String subTitle;
        private String image;
        private Integer type;
    }

    @Data
    public static class DayActivityVO {
        private Integer id;
        @ApiModelProperty("标题")
        private String title;
        @ApiModelProperty("图片")
        private String image;
        @ApiModelProperty("日期")
        private String dateLabel;
        @ApiModelProperty("区县")
        private String county;
        @ApiModelProperty("地址")
        private String address;
        @ApiModelProperty("价格，例如：1.23")
        private String price;
        @ApiModelProperty("经度")
        private String longitude;
        @ApiModelProperty("纬度")
        private String latitude;
        @ApiModelProperty("距离描述，例如：距我xxxkm")
        private String distanceDesc;
    }

}
