package com.zhong.working.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: 竹阳
 * @date: 2022-09-15
 **/
@Data
public class FolkCultureIndexVO {
    @ApiModelProperty("背景图")
    private String background;
    @ApiModelProperty("地方文化")
    private List<LocalCultureDTO> localCultureList;

    @Data
    public static class LocalCultureDTO {
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
