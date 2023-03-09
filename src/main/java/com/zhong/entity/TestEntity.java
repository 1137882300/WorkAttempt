package com.zhong.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @date 2022/8/5 13:43
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestEntity {


    private String name;

    private Integer age;


}
