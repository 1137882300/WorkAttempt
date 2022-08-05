package com.zhong;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.shaded.org.checkerframework.checker.units.qual.C;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.zhong.entity.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.apache.xmlbeans.impl.xb.substwsdl.TImport;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @date 2022/3/25 22:33
 */
public class CollectionTest {

    /**
     * 不变集合处理元素数据
     */
    @Test
    public void deal() {
        List<TestEntity> list = new ArrayList<TestEntity>() {{
            add(new TestEntity("Bob rR", 23));
            add(new TestEntity("aLex TT", 88));
            add(new TestEntity("meaL PP", 66));
        }};

        list.forEach(x -> x.setName(x.getName().toLowerCase().replace(" ", "")));

        System.out.println(JSON.toJSONString(list));
    }

    /**
     * collections4 的 CaseInsensitiveMap 不区分大小写
     */
    @Test
    public void CaseInsensitiveMap() {
        Map<String, String> hashMap = new HashMap<String, String>() {{
            put("asdsd", "1");
            put("sd sd", "2");
            put("sd Asd", "3");
        }};

        CaseInsensitiveMap<String, String> caseInsensitiveMap = new CaseInsensitiveMap<String, String>(hashMap);


        String s1 = "ASDSD";
        String s2 = "SD ASD";
        String s = caseInsensitiveMap.get(s2);
        System.out.println(s);

    }

    /**
     * 测试map的 containsKey 是否忽略大小写 ? 没有忽略！
     */
    @Test
    public void MapTest() {
        Map<String, String> hashMap = new HashMap<String, String>() {{
            put("asdsd", "1");
            put("sd asd", "2");
            put("sd Asd", "3");
        }};

        String s1 = "ASDSD";
        String s2 = "asdsd";
        String s = hashMap.get(s1);
        System.out.println(s);

    }

    /**
     * 初始化list的写法
     */
    @Test
    public void ListTT() {
        List<Integer> integers = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
        }};

        System.out.println(integers);

    }

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
