package com.zhong.working.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 竹阳
 * @date: 2022-10-12
 **/
@Data
public class ScenicQueryItemVO {
    private Integer id;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("图片")
    private String image;
    @ApiModelProperty("星级")
    private String starLabel;
    @ApiModelProperty("标签")
    private String tagLabel;
    @ApiModelProperty("是否可预约 1-可以，-1-不可以")
    private Integer reserve;
    @ApiModelProperty("区县")
    private String county;
    @ApiModelProperty("价格，例如：1.23")
    private String price;
    @ApiModelProperty("经度")
    private String longitude;
    @ApiModelProperty("维度")
    private String latitude;
    @ApiModelProperty("距离描述，例如：距我1.2km")
    private String distanceDescV2;
    @ApiModelProperty("景区容纳程度，-1表示未知，0-100为占比")
    private String capacityPercent;
    @ApiModelProperty("是否收费  -1-收费，1-免费")
    private Integer free;
    @ApiModelProperty("是否有门票，1是0否")
    private Integer hasTicket;
    @ApiModelProperty("景区标签")
    private List<String> tagList;
    @ApiModelProperty("是否已打卡")
    private Integer punchCard;
    @ApiModelProperty("是否免费预约，1是0否")
    private Integer freeReserve;
    @ApiModelProperty("poi的id")
    private Integer poiId;
    @ApiModelProperty("花开程度，-1表示未知，0-100为占比")
    private String bloomPercent;
    @ApiModelProperty("最佳花开时间描述")
    private String bestBloom;
    @ApiModelProperty("一周适宜度")
    private List<ScenicTourLevelDay> tourLevelDayList;
    @ApiModelProperty("负氧离子")
    private String negativeOxygenIon;
    @ApiModelProperty("空气质量")
    private String airQuality;
    @Data
    public static class ScenicTourLevelDay{
        private String dateLabel;
        private Integer level;
        private String prompt;
    }
}
