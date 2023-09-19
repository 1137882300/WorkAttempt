package com.zhong.working.base.entity;

import com.zhong.entity.Relation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.aop.target.LazyInitTargetSource;

import java.util.List;

/**
 * @author: 竹阳
 * @date: 2022-09-12
 **/
@Data
public class ConfigInfoVO {

    //基本类型
    @ApiModelProperty("企微客服")
    private String wechatService;
    @ApiModelProperty("分享标题")
    private String shareTitle;
    @ApiModelProperty("分享图")
    private String shareImage;
    @ApiModelProperty("是否灰色首页，1是0否")
    private Integer homeGray;

}
