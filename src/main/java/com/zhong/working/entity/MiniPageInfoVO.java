package com.zhong.working.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: 竹阳
 * @date: 2022-09-16
 **/
@Data
public class MiniPageInfoVO {
    private String appid;
    @ApiModelProperty("小程序类型 1-内部（不需要填写appid） 2-外部")
    private Integer type;
    @ApiModelProperty("小程序页面地址")
    private String url;
}
