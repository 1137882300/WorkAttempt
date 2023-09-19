package com.zhong.working.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: 竹阳
 * @date: 2022-09-12
 **/
@Data
public class FlowIndexVO {
    @ApiModelProperty("类型2-线路、3-商品、4-寺庙、5-博物馆、6-榜单、7-poi、8攻略、9-笔记、10-视频、11-资讯、12-公告、13-景点，21博物馆展览，22文化活动，23民宿，24酒店，99banner广告")
    private Integer type;
    @ApiModelProperty("唯一性标识，用于前端去重")
    private String identity;
    @ApiModelProperty("数据信息，根据type区分")
    private Object data;
    @ApiModelProperty("扩展数据信息")
    private Object extend;

}
