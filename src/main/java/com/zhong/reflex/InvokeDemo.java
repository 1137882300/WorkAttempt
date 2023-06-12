package com.zhong.reflex;


import com.zhong.entity.EntityTest;
import lombok.SneakyThrows;
import lombok.val;

/**
 * @author: juzi
 * @date: 2023/6/1
 * @desc:
 */
public class InvokeDemo {

    /**
     * @author juzi
     * @date 2023/6/1 下午 4:28
     * @description 利用反射给属性赋值
     */
    @SneakyThrows
    public static void main(String[] args) {
        EntityTest entityTest = new EntityTest();

        EntityTest.class.getMethod("set" + "Name", String.class)
                .invoke(entityTest, "你好呀");

        System.out.println(entityTest);

    }




}
