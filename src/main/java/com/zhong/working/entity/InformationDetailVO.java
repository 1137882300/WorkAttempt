package com.zhong.working.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: 竹阳
 * @date: 2022-09-12
 **/
@Data
public class InformationDetailVO {
    private Integer id;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("来源，例如：文旅绿码")
    private String source;
    @ApiModelProperty("发布时间，例如：2022-10-01")
    private String publishDate;
    @ApiModelProperty("内容")
    private String content;
    @ApiModelProperty("分享图")
    private String shareImage;
}
