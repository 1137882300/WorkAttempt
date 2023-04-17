package com.zhong.working.entity;

import com.zhong.entity.Dog;
import com.zhong.entity.ItemPropertyBO;
import com.zhong.entity.OldNew;
import com.zhong.entity.Relation;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author: 竹阳
 * @date: 2022-09-23
 **/
@Data
public class ActivityDetailVO {
    private Integer id;
    private String title;
    private List<Relation> relationList;
    private List<String> imageList;
    private List<String> tagList;
    private String travel;
    private String notice;
    private String dateLabel;
    private Integer surplusNum;
    private String teacherAvatar;
    private String teacherName;
    private String teacherDesc;
    private String enterAt;
    private String expiryAt;
    private Integer expiry;
    private String shareTitle;
    private Integer alreadyApply;
    private String originalPrice;

    private List<Integer> sssss;
    private Relation relation;

}
