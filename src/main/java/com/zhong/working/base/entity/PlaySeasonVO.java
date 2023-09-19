package com.zhong.working.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: 竹阳
 * @date: 2022-09-12
 **/
@Data
public class PlaySeasonVO {
    @ApiModelProperty("头部banner")
    private List<FlowBannerVO> topBannerList;
    @ApiModelProperty("榜单")
    private SubjectVO.RankingVO ranking;
    @ApiModelProperty("主题tab切换")
    private List<ScenicIndexVO.TabVO> themeList;
}
