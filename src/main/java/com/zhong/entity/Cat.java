package com.zhong.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/10/15 16:47
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cat {

    private int id;

    private int state;

}
