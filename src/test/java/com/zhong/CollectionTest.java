package com.zhong;

import com.alibaba.nacos.shaded.org.checkerframework.checker.units.qual.C;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.zhong.entity.Dog;
import com.zhong.entity.People;
import com.zhong.entity.StateEnum;
import com.zhong.entity.User;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @date 2022/3/25 22:33
 */
public class CollectionTest {

    @Test
    public void single() {
        List<String> list = Collections.singletonList("nihao");
        System.out.println(list);

        List<String> list2 = Collections.singletonList(null);
        System.out.println(list2);
    }


    @Test
    public void end() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        int i = 0;
        for (Integer integer : list) {
            i++;

            if (list.size() == i) {
                System.out.println("最后一次" + integer);
            }
        }
    }

    @Test
    public void union() {
        Set<String> set1 = Sets.newHashSet();
        set1.add("1");
        set1.add("2");
        set1.add("3");
        Set<String> set2 = Sets.newHashSet();
        set2.add("1");
        set2.add("2");
        set2.add("5");
        /*
            union 合并+去重
         */
        List<String> list = new ArrayList<>(CollectionUtils.union(set1, set2));
        System.out.println(list);

    }

    @Test
    public void mutilFunctionMap(){
        //满足几点
        //1. 存md5
        //2. 存不同类型的值

        Map<StateEnum, List<Map<String, Object>>> map = new LinkedHashMap<>();

        List<Map<String, Object>> list = Lists.newLinkedList();

        Map<String, Object> innerMap = new HashMap<>();
        innerMap.put("unitId1", new User(11,22));
        innerMap.put("unitId2", new People(66));
        innerMap.put("unitId3", new Dog(99, StateEnum.CLOSE));
        list.add(innerMap);

        map.put(StateEnum.OPEN, list);

        System.out.println(map);


    }


}
