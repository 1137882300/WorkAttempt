package com.zhong;

import com.google.common.collect.Lists;
import com.zhong.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/10/8 14:53
 */
public class LambdaTest {

    private User u1,u2,u3,u4,u5,u6,u7;

    @Before
    public void init(){
        u1 = User.builder().id(1).age(4).build();
        u2 = User.builder().id(2).age(34).build();
        u3 = User.builder().id(3).age(15).build();
        u4 = User.builder().id(4).age(52).build();
        u5 = User.builder().id(5).age(23).build();
        u6 = User.builder().id(6).age(51).build();
    }

    /**
     * groupingBy
     * mapping
     */
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
        User uu = User.builder().sex(1).id(1).age(2).build();
        int sex = uu.getSex();
        System.out.println(sex);
        System.out.println(uu);
    }

    /**
     * Optional
     */
    @Test
    public void test3(){
        List<User> users = Lists.newArrayList(u1, u2, u3, u4,u5,u6,u7);
        Optional.of(users).orElse(Collections.emptyList()).forEach(System.out::println);
        users.forEach(System.out::println);

        Optional<User> optional = Optional.ofNullable(User.builder().build());
        if (optional.isPresent()){
            String s = optional.get().toString();
            System.out.println(s);
        }
    }

    /**
     * toBuilder
     */
    @Test
    public void test4(){
        User user = User.builder().age(1).id(1).build();
        System.out.println(user);
//        User build = user.toBuilder().id(2).age(2).build();
//        System.out.println(build);
//        System.out.println(user == build);
//        System.out.println(user.equals(build));
    }

}

