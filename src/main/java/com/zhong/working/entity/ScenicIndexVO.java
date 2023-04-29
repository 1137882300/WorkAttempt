package com.zhong.working.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: 竹阳
 * @date: 2022-09-12
 **/
@Data
public class ScenicIndexVO {
    @ApiModelProperty("区县id")
    private Integer countyId;
    @ApiModelProperty("分类")
    private List<TabVO> tabList;
    @ApiModelProperty("筛选条件")
    private ScenicFilterDTO filter;

    @Data
    public static class ScenicFilterDTO{
        @ApiModelProperty("区县")
        private List<ScenicFilterValueDTO> countyList;
        @ApiModelProperty("星级")
        private List<ScenicFilterValueDTO> starList;
    }

    @Data
    public static class ScenicFilterValueDTO{
        private Integer value;
        private String label;
        @ApiModelProperty("是否选中")
        private Integer selected;
    }
    @Data
    public static class TabVO {
        private String title;
        private String subTitle;
        private String image;
        private Integer type;
    }
}
