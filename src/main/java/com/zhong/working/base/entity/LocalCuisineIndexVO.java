package com.zhong.working.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: 竹阳
 * @date: 2022-09-16
 **/
@Data
public class LocalCuisineIndexVO {
    @ApiModelProperty("头部图片")
    private String topImage;
    @ApiModelProperty("杭帮菜")
    private List<LocalCuisineDTO> dishesList;

    @Data
    public static class LocalCuisineDTO{
        private Integer id;
        @ApiModelProperty("标题")
        private String title;
        @ApiModelProperty("副标题")
        private String subTitle;
        @ApiModelProperty("图片")
        private String image;
        @ApiModelProperty("类型 8攻略、9-笔记、11-资讯")
        private Integer type;
    }
}
