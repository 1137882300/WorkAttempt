package com.zhong.entity;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/10/8 14:59
 */

@Data
@SuperBuilder(toBuilder = true)
public class User extends People{

    private int age;

    private int id;
}
