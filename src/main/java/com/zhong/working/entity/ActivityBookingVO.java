package com.zhong.working.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: 竹阳
 * @date: 2022-09-23
 **/
@Data
public class ActivityBookingVO {
    private Integer id;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("图片")
    private String image;
    @ApiModelProperty("标签")
    private List<String> tagList;
    @ApiModelProperty("报名是否限制人数：1-限制，0-不限制")
    private Integer limited;
    @ApiModelProperty("报名上限成人人数，limited为1使用")
    private Integer limitAldult;
    @ApiModelProperty("报名上限儿童人数，limited为1使用")
    private Integer limtiChild;
    @ApiModelProperty("体验时间")
    private String dateLabel;
    @ApiModelProperty("疫情须知")
    private String remind;
    @ApiModelProperty("是否为权益产品：1-是，0-否")
    private Integer equityProduct;
}
