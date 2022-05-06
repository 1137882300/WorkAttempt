package com.zhong.atomic;

import com.zhong.entity.People;
import com.zhong.entity.User;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @date 2022/4/25 13:51
 */

public class AtomicIntegerFieldUpdaterTest {
/*
这是一个基于反射的工具类，它能对指定类的【指定的volatile引用字段】进行【原子更新】。(注意这个字段不能是private的)
简单理解：就是对某个类中，被volatile修饰的字段进行原子更新。
 */

    public static void main(String[] args) {
        AtomicReferenceFieldUpdater<People, Integer> updater = AtomicReferenceFieldUpdater.newUpdater(People.class, Integer.class, "sex");

        People people = new People();
        people.setSex(23);

        boolean compareAndSet = updater.compareAndSet(people, 23, 66);
        System.out.println(compareAndSet);

        int sex = people.getSex();
        System.out.println(sex);

    }

}
