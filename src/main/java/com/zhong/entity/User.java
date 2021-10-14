package com.zhong.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/10/8 14:59
 */

@Data
//@Builder(toBuilder = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class User extends People{

    private int id;
    private int age;

}
