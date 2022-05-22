package com.zhong.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @date 2022/4/14 20:06
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Relation {


    private Long master;

    private Long slave;

    private Long shopId;

}
