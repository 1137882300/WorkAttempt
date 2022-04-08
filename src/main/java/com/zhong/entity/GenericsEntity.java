package com.zhong.entity;

import lombok.Data;

/**
 * @date 2022/4/8 11:09
 */
@Data
public class GenericsEntity<T> {

    private Integer num;

    public GenericsEntity(T t) {

    }

    public Integer get(GenericsEntity<T> generics) {
        System.out.println("aaaaaaaaaaaaaaaa");
        return generics.num;
    }


}
