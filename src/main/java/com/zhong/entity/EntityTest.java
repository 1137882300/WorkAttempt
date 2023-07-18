package com.zhong.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author: juzi
 * @date: 2023/5/12
 * @desc:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntityTest {

    private Long id;
    private String name;
    private Integer age;
    private Boolean sex;
    private Integer phone;

    private BigDecimal amount;


}
