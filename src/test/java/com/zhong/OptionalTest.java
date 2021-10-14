package com.zhong;

import com.google.common.collect.Lists;
import com.zhong.entity.People;
import com.zhong.entity.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/10/13 14:08
 */
public class OptionalTest {

    private User u1,u2,u3,u4,u5,u6,u7;
    private People u8;

    @Before
    public void init(){
        u1 = User.builder().id(1).age(4).build();
        u2 = User.builder().id(2).age(34).build();
        u3 = User.builder().id(3).age(15).build();
        u4 = User.builder().id(4).age(52).build();
        u5 = User.builder().id(5).age(23).build();
        u6 = User.builder().id(6).age(51).build();

        u8 = People.builder().sex(22).build();
    }

    @Test
    public void test(){
        List<User> list = Lists.newArrayList(u1, u2);
        List<User> list2 = Lists.newArrayList(u5, u6);
        Optional<List<User>> optional1 = Optional.of(Lists.newArrayList(u3, u4));

        List<People> people = Lists.newArrayList(u8);

        Optional<List<User>> optional;
        optional = Optional.of(list);
        System.out.println(optional);
        System.out.println("-----------------------------");
        optional = Optional.of(list2);
        System.out.println(optional);
        System.out.println("--------------------------------");
        optional = optional1;
        System.out.println(optional);

    }
}
