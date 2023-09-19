package com.zhong.working.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author: 竹阳
 * @date: 2022-09-13
 **/
@Data
public class VideoDetailVO {
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("封面")
    private String cover;
    @ApiModelProperty("视频")
    private String video;
    @ApiModelProperty("地点")
    private List<NoteDetailVO.PointVO> poiList;
    @ApiModelProperty("内容")
    private String content;
    @ApiModelProperty("发布时间，例如：2022-10-01")
    private String publishDate;
    @ApiModelProperty("来源作者")
    private String source;
    @ApiModelProperty("头像")
    private String avatar;
    @ApiModelProperty("用户喜好")
    private NoteDetailVO.UserLikeVO userLike;
    @ApiModelProperty("统计数量")
    private NoteDetailVO.StatInfoVO statInfo;
}
