package com.zhong.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/10/8 17:55
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class People {

    private int sex;

}
