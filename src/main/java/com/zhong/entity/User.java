package com.zhong.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/10/8 14:59
 */

@Data
@Builder
public class User {

    private int age;

    private int id;
}
