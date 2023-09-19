package com.zhong.working.base.entity;

import com.zhong.entity.Dog;
import com.zhong.entity.ItemPropertyBO;
import com.zhong.entity.OldNew;
import com.zhong.entity.Relation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author: 竹阳
 * @date: 2022-09-23
 **/
@Data
public class ActivityDetailVO {
    private Integer id;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("图片列表")
    private List<String> imageList;
    @ApiModelProperty("标签")
    private List<String> tagList;
    @ApiModelProperty("活动行程")
    private String travel;
    @ApiModelProperty("活动说明")
    private String notice;
    @ApiModelProperty("体验日期")
    private String dateLabel;
    @ApiModelProperty("剩余名额数量")
    private Integer surplusNum;
    @ApiModelProperty("漫步者导师头像")
    private String teacherAvatar;
    @ApiModelProperty("漫步者导师名称")
    private String teacherName;
    @ApiModelProperty("漫步者导师简介")
    private String teacherDesc;
    @ApiModelProperty("报名开始时间，yyyy-MM-dd HH:mm:ss")
    private String enterAt;
    @ApiModelProperty("报名截止时间，yyyy-MM-dd HH:mm:ss")
    private String expiryAt;
    @ApiModelProperty("是否已截止1是0否")
    private Integer expiry;
    @ApiModelProperty("分享标题")
    private String shareTitle;
    @ApiModelProperty("是否已报名1是0否")
    private Integer alreadyApply;
    @ApiModelProperty("原价")
    private String originalPrice;

}
