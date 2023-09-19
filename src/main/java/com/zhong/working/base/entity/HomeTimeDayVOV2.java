package com.zhong.working.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: 竹阳
 * @date: 2022-12-29
 **/
@Data
public class HomeTimeDayVOV2 {
    @ApiModelProperty("标题，例如：此时此刻")
    private String title;
    @ApiModelProperty("副标题，例如：在杭州的一天")
    private String subTitle;
    @ApiModelProperty("tab切换")
    private List<HomeTimeDayTabDTO> timeDayTabList;
    private String enterData;

    @Data
    public static class HomeTimeDayTabDTO {
        @ApiModelProperty("标题，例如：早安杭州")
        private String title;
        @ApiModelProperty("未选中的图片")
        private String image;
        @ApiModelProperty("选中的图片")
        private String checkedImage;
        @ApiModelProperty("类型id")
        private Integer type;
        @ApiModelProperty("是否选中1是0否")
        private Integer selected;
//        @ApiModelProperty("该时间段下对应的数据")
//        private List<FlowIndexVO> list;
    }
}
