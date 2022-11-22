package com.zhong;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.shaded.org.checkerframework.checker.units.qual.C;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.zhong.entity.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.apache.xmlbeans.impl.xb.substwsdl.TImport;
import org.junit.Test;
import org.springframework.core.env.PropertySource;

import javax.xml.crypto.Data;
import java.beans.Transient;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @date 2022/3/25 22:33
 */
public class CollectionTest {
    /**
     * computeIfAbsent 可以接着 put
     */
    @Test
    public void computeIfAbsent2() {
        Map<String, Map<String, String>> map = Maps.newHashMap();

        map.put("aaa", new HashMap<String, String>() {{
            put("a1", "11");
        }});

        Map<String, String> computeIfAbsent = map.computeIfAbsent("bbb", (key) -> Maps.newHashMap());
        computeIfAbsent.put("a2", "22");

        Map<String, String> computeIfAbsent2 = map.computeIfAbsent("aaa", (key) -> Maps.newHashMap());
        computeIfAbsent2.put("a3", "33");


        System.out.println(map);//{aaa={a1=11, a3=33}, bbb={a2=22}}

    }

    /**
     * map的computeIfAbsent方法
     * 1. 不存在key,则新增key/value
     * 2. 存在key,则保持原样
     */
    @Test
    public void computeIfAbsent() {
        HashMap<String, Integer> prices = new HashMap<>();

        prices.put("Shoes", 200);
        prices.put("Bag", 300);
        prices.put("Pant", 150);
        System.out.println("HashMap: " + prices);

        int shirtPrice = prices.computeIfAbsent("Shirt", key -> 280);
        System.out.println("Price of Sihrt: " + shirtPrice);

        System.out.println("Updated HashMap: " + prices);

        Integer aaa = prices.computeIfAbsent("Bag", key -> 100);
        System.out.println(aaa);//300

        System.out.println("Updated HashMap: " + prices);

    }

    /**
     * collect 多参数
     * collect(ArrayList::new, ArrayList::add, ArrayList::addAll)
     * <p>
     * xxxMap.values() = List<List>类型  DTO1 对应类型，转成里面的另一个对象类型 DTO2
     * List<DTO2> DTO2List = xxxMap.values().stream()
     * .map(QueryItemInfoDTO::getSkuList)
     * .filter(CollectionUtils::isNotEmpty)
     * .collect(Lists::newArrayList, List::addAll, List::addAll);
     * 或
     * List<DTO2> DTO2List = xxxMap.values().stream()
     * .map(QueryItemInfoDTO::getSkuList)
     * .filter(CollectionUtils::isNotEmpty)
     * .flatMap(Collection::stream)
     * .collect(Collectors.toList())
     */
    @Test
    public void collect() {
        List<String> list = Lists.newArrayList("aaa", "fff", "vvv");
        Stream<String> stringStream = Stream.of("xxx", "ooo");
        ArrayList<Object> collect = stringStream.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        System.out.println(collect);
    }


    /**
     * map的key是对象，对象里的属性是空的
     */
    @Test
    public void mapObj() {
        Map<Dog, List<String>> map = Maps.newHashMap();
        Dog cat = new Dog();
        map.put(cat, Lists.newArrayList());
        System.out.println(map);
    }

    @Test
    public void linkedHashSet() {
        Set<List<String>> propertySources = new LinkedHashSet<>();
        List<String> list = new ArrayList<>();
        list.add("aa");
        list.add("bb");
        list.add("cc");
        list.add("aa");
        propertySources.add(list);

        List<String> list2 = new ArrayList<>();
        list2.add("dd");
        list2.add("ff");
        list2.add("gg");
        list2.add("aa");
        propertySources.add(list2);
        System.out.println(propertySources);


    }

    /**
     * ConcurrentHashMap
     */
    @Test
    public void concurrentHashMap() {
        ConcurrentHashMap<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        Integer put = concurrentHashMap.put(1, 2);
        System.out.println(put);
        Integer integer = concurrentHashMap.get(1);
        System.out.println(integer);
    }

    /**
     * map.merge
     * 1. 替换value
     * 2. 没有key的可以新增上
     */
    @Test
    public void merge() {
        HashMap<String, String> oldMap = new HashMap<String, String>() {{
            put("1", "旧的1");
            put("2", "旧的2");
            put("3", "旧的3");
        }};

        HashMap<String, String> newMap = new HashMap<String, String>() {{
            put("1", "新的1");
            put("4", "新的4");
            put("5", "新的5");
        }};

        Optional.of(oldMap).ifPresent(x -> x.forEach((k, v) -> {
            newMap.merge(k, v, (v1, v2) -> v1);//v1是新的
        }));

        System.out.println(newMap);
    }

    /**
     * list 排序
     * nullsLast ：null的大于非null的
     * 对象可以为空，以及对象内的属性
     */
    @Test
    public void sort() {
        ArrayList<Integer> list = Lists.newArrayList(3, 5, 6, 1, 8, 2, null, 6, null);
        list.sort(Comparator.nullsLast(Comparator.naturalOrder()));
//        System.out.println(list);


        Dog dog5 = new Dog(null, StateEnum.OPEN);
        Dog dog1 = new Dog(9, StateEnum.OPEN);
        Dog dog4 = new Dog(null, StateEnum.OPEN);
        Dog dog2 = new Dog(4, StateEnum.OPEN);
        Dog dog3 = new Dog(6, StateEnum.OPEN);
        Dog dog6 = new Dog(11, StateEnum.OPEN);
        ArrayList<Dog> arrayList = Lists.newArrayList(dog1, dog2, dog3, dog4, dog5, dog6);
        List<Dog> collect = arrayList.stream()
//                .filter(x -> x.getId() != null)
//                .sorted(Comparator.nullsLast(Comparator.comparing(Dog::getId)))
                .sorted(Comparator.comparing(Dog::getId, Comparator.nullsLast(Integer::compareTo)))
                .collect(Collectors.toList());
//        collect.addAll(arrayList.stream().filter(x -> x.getId() == null).collect(Collectors.toList()));
        System.out.println(collect);


//        arrayList.stream().sorted(Comparator.comparing(Dog::getId, Comparator.nullsLast(Long::compareTo))).collect(Collectors.toList());

    }

    /**
     * 初始化 list
     * Stream.of(xxx,xx)
     */
    @Test
    public void initList() {
        List<Integer> collect = Stream.of(
                111,
                222,
                555,
                777,
                888
        ).collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    public void values() {
        Map<String, List<String>> listMap = Maps.newHashMap();
        listMap.put("aa", Lists.newArrayList("11", "22"));
        listMap.put("bb", Lists.newArrayList("11", "22"));
        System.out.println(listMap.values());//[[11, 22], [11, 22]]
        List<String> collect = listMap.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
        System.out.println(collect);//[11, 22, 11, 22]
    }

    /**
     *
     */
    @Test
    public void mapTT() {
        HashMap<Object, List<Cat>> map = Maps.newHashMap();
        List<Integer> collect = map.values().stream().flatMap(Collection::stream).map(Cat::getId).collect(Collectors.toList());
        System.out.println(collect);
    }

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
    public void mutilFunctionMap() {
        //满足几点
        //1. 存md5
        //2. 存不同类型的值

        Map<StateEnum, List<Map<String, Object>>> map = new LinkedHashMap<>();

        List<Map<String, Object>> list = Lists.newLinkedList();

        Map<String, Object> innerMap = new HashMap<>();
        innerMap.put("unitId1", new User(11, 22));
        innerMap.put("unitId2", new People(66));
        innerMap.put("unitId3", new Dog(99, StateEnum.CLOSE));
        list.add(innerMap);

        map.put(StateEnum.OPEN, list);

        System.out.println(map);


    }

    @Test
    public void addListEmpty() {
        List<Object> list = Lists.newArrayList(1, 2);
        List<Object> objects = Collections.emptyList();

        List<Object> newArrayList = Lists.newArrayList();

        Optional.of(list).ifPresent(newArrayList::addAll);
        Optional.of(objects).ifPresent(newArrayList::addAll);

        System.out.println(newArrayList);

    }

    @Test
    public void setAddEmpty() {
        Set<Object> hashSet = Sets.newHashSet();
        ArrayList<Object> objects = Lists.newArrayList();
        ArrayList<Object> list = Lists.newArrayList();
        Collection<Object> subtract = CollectionUtils.intersection(objects, list);
        System.out.println(subtract);
        hashSet.addAll(subtract);
        System.out.println(hashSet);
        System.out.println(hashSet.size());

    }


}
