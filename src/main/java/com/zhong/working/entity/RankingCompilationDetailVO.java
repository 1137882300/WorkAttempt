package com.zhong.working.entity;

import lombok.Data;

import java.util.List;

/**
 * @author: 竹阳
 * @date: 2022-10-11
 **/
@Data
public class RankingCompilationDetailVO {
    private String title;
    private String subTitle;
    private List<String> tagList;
    private List<FlowIndexVO> list;
    private int totalPage;
}
