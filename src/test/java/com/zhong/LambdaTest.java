package com.zhong;

import com.google.common.collect.Lists;
import com.zhong.entity.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/10/8 14:53
 */
public class LambdaTest {

    @Test
    public void test1(){
        User u1 = User.builder().age(1).id(1).build();
        User u2 = User.builder().age(2).id(2).build();
        User u3 = User.builder().age(1).id(1).build();
        User u4 = User.builder().age(4).id(2).build();
        ArrayList<User> users = Lists.newArrayList(u1, u2, u3, u4);
        Map<Integer, List<Integer>> collect = users.stream()
                .collect(Collectors.groupingBy(User::getId, Collectors.mapping(User::getAge, Collectors.toList())));
        System.out.println(collect);
    }

    @Test
    public void test2(){
        User user = User.builder().id(1).build();
    }

}
