package com.zhong.working.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: juzi
 * @date: 2023/4/18
 * @desc:
 */
public class SubjectActivityDTO {
    @ApiModelProperty("本周活动")
    private SubjectActivityInfoDTO week;
    @ApiModelProperty("下周活动")
    private SubjectActivityInfoDTO nextWeek;

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
