package com.zhong.working.base.entity;

import lombok.Data;

import java.util.List;

/**
 * @author: 竹阳
 * @date: 2022-12-06
 **/
@Data
public class CultureMuseumShowRecommendVO {
    private List<CultureCalendarActivityVO.DayActivityVO> dataList;
}
