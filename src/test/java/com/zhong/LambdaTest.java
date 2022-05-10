package com.zhong;

import com.google.common.collect.Lists;
import com.zhong.entity.Cat;
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
import java.util.stream.Stream;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/10/8 14:53
 */
public class LambdaTest {

    private User u1, u2, u3, u4, u5, u6, u7;
    private List<User> list = Lists.newArrayList();

    /*
        嵌套list 合成一个list
     */
    @Test
    public void flatMap() {
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> list1 = Lists.newArrayList(1, 2, 23, 45);
        list.add(list1);
        list.add(Lists.newArrayList(2, 1, 4, 45));

        List<Integer> collect = list.stream().flatMap(Collection::stream).distinct().collect(Collectors.toList());
        System.out.println(collect);


        List<Integer> integerList = Stream.of(Arrays.asList(1, 2, 4), Arrays.asList(1, 2, 5)).flatMap(Collection::stream).collect(Collectors.toList());

    }

    @Before
    public void init() {
        u1 = User.builder().id(1).age(4).build();
        u2 = User.builder().id(2).age(34).build();
        u3 = User.builder().id(4).age(15).build();
        u4 = User.builder().id(3).age(52).build();
        u5 = User.builder().id(6).age(23).build();
        u6 = User.builder().id(5).age(51).build();

        list.add(u1 );
        list.add( u2 );
        list.add( u3 );
        list.add( u4);
        list.add( u5 );
        list.add( u6);
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


    @Test
    public void test5(){
        List<User> users = Lists.newArrayList();
        List<String> collect = users.stream().map(User::toString).collect(Collectors.toList());
        System.out.println(collect);
    }


    @Test
    public void test6(){
        System.out.println(list);
        List<User> sorted = list.stream().sorted(Comparator.comparing(User::getId)).collect(Collectors.toList());
        System.out.println(sorted);

        List<User> revers = list.stream().sorted(Comparator.comparing(User::getId).reversed()).collect(Collectors.toList());
        System.out.println(revers);

    }

    @Test
    public void test7(){
        List<Long> list = Collections.emptyList();
        List<Long> collect = list.stream().distinct().collect(Collectors.toList());
        System.out.println(collect);
    }

    /**
     * 有一个匹配就返回true
     */
    @Test
    public void anyMatch(){
        List<Integer> list = Arrays.asList(3, 4, 2, 1, 5);
        boolean answer = list.stream().anyMatch(n -> (n * (n + 1)) / 4 == 5);

        System.out.println(answer);
    }

    @Test
    public void removeList(){
        Cat cat1 = Cat.builder().id(1).state(11).build();
        Cat cat2 = Cat.builder().id(2).state(22).build();
        Cat cat3 = Cat.builder().id(3).state(33).build();
        List<Cat> list = Lists.newArrayList(cat1,cat2,cat3);

//        list.removeIf(x -> x.getId() == 2 || x.getId() ==3);

        list.removeIf(x -> {
            boolean flag = false;
            if (x.getId() == 2){
                flag = true;
            }
            return flag;
        });


        System.out.println(list);


    }


}

