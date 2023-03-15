package com.zhong.designPatterns.strategyMode.v2;

/**
 * @author: juzi
 * @date: 2023/3/15
 */
public interface ConditionMethodFunction<T, R> {

    R invoke(T t);

}
