package com.zhong.working.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: 竹阳
 * @date: 2022-09-12
 **/
@Data
public class RankingDetailVO {
    @ApiModelProperty("榜单tab切换，page=1时返回数据")
    private List<ScenicIndexVO.TabVO> tabList;
    @ApiModelProperty("榜单类型 1：POI榜单 2：攻略榜单 3:线路榜单 4:手伴榜单")
    private Integer type;
//    private List<FlowIndexVO> list;
//    private int totalPage;
    @ApiModelProperty("分享标题")
    private String shareTitle;
    @ApiModelProperty("分享图")
    private String shareImage;

    @Data
    public static class RankingExtendDTO {
        @ApiModelProperty("推荐理由")
        private String recommendReason;
        @ApiModelProperty("区县")
        private String county;
    }
}
