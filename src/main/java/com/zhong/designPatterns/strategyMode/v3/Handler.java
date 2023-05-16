package com.zhong.designPatterns.strategyMode.v3;

import com.google.common.collect.Lists;
import com.zhong.entity.EntityTest;

import java.util.List;

/**
 * @author: juzi
 * @date: 2023/5/15
 * @desc:
 */
public interface Handler {

    /**
     * 处理器
     */
    void doHandler(Integer code);

    /**
     * 撤回消息
     */
    void recall(Void v);

    public static void main(String[] args) {

        List<EntityTest> list = Lists.newArrayList(
                EntityTest.builder().id(22L).build(),
                EntityTest.builder().id(55L).age(12).build()
        );
        System.out.println(list);

        list.forEach(x -> {
            if (x.getId() == 22L) {
                return;
            }
            System.out.println(x);
        });


    }
}
