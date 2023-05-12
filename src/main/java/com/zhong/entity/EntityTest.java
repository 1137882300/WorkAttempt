package com.zhong.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author: juzi
 * @date: 2023/5/12
 * @desc:
 */
@Data
@Builder
public class EntityTest {

    private Long id;
    private String name;
    private Integer age;
    private Boolean sex;
    private Integer phone;


}
