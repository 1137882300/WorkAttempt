package com.zhong;

import com.google.common.base.*;
import com.google.common.collect.*;
import com.zhong.entity.Cat;
import com.zhong.entity.MultiLanguageString;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.springframework.util.StopWatch;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2022/1/21 11:34
 */
public class GuavaTest {

    /**
     * @author juzi
     * @date 2023/6/26 下午 2:24
     * @description Lists.reverse
     */
    @Test
    public void reverse() {
        List<Integer> list = Lists.newArrayList(3, 1, 2);
        List<Integer> reverseList = Lists.reverse(list);
        System.out.println(reverseList);
    }

    /**
     * @author juzi
     * @date 2023/6/26 下午 2:23
     * @description Lists.transform  流处理
     */
    @Test
    public void transform() {
        List<String> list = Lists.newArrayList("a", "b", "c");
        List<String> transformList = list.stream().map(String::toUpperCase).collect(Collectors.toList());
        List<String> transformList2 = Lists.transform(list, x -> x.toUpperCase());
        System.out.println(transformList);
    }

    /**
     * 双向Map
     * 注意：不能存储多对一的关系
     */
    @Test
    public void biMap() {
        BiMap<String, Integer> biMap = HashBiMap.create();
        biMap.put("A", 1);
        biMap.put("B", 2);
        biMap.put("C", null);
        biMap.put("D", 4);
        biMap.put("t", 4);
        biMap.put("u", 4);
        BiMap<Integer, String> inverse = biMap.inverse();
        String s = inverse.get(4);
        System.out.println(s);//value already present: 4
    }

    /**
     * @author juzi
     * @date 2023/7/31 下午 1:39
     * @description Maps.filterValues
     * 留下为true的数据
     */
    @Test
    public void map() {
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>() {{
            put("a", 1);
            put("b", 2);
            put("c", 3);
            put("d", 4);
            put("e", 4);
        }};
        Map<String, Integer> map = Maps.filterValues(hashMap, x -> x.equals(4));
        System.out.println(map);
    }

    /**
     * Maps.filterKeys
     * 过滤指定 key 的map
     */
    @Test
    public void filterKeys() {
        HashMap<String, String> hashMap = new HashMap<String, String>() {{
            put("1", "qq");
            put("2", "rr");
            put("3", "yy");
            put("4", "oo");
            put("5", "hh");
        }};
        Map<String, String> map = Maps.filterKeys(hashMap, x -> x.equals("3"));
        System.out.println(map);
    }

    /**
     * CollectionUtils.subtract
     * 减法
     * 保留自身不同的
     * list1 - list2
     */
    @Test
    public void subtract() {
        List<String> list1 = new ArrayList<>();
        list1.add("aa");
        list1.add("hh");
        List<String> list2 = Lists.newArrayList("aa", "55");
        Collection<String> subtract = CollectionUtils.subtract(list1, list2);
        System.out.println(subtract);
    }

    @Test
    public void one() {
        ArrayList<String> exist = Lists.newArrayList("43682");
        ArrayList<String> request = Lists.newArrayList("23910");

        Collection<String> subtract = CollectionUtils.subtract(exist, request);
        System.out.println(subtract);//[43682]
    }

    /**
     * CollectionUtils 的方法不是指针
     */
    @Test
    public void coTest() {
        ArrayList<String> list1 = Lists.newArrayList("aa", "bb", "cc", "pp");
        ArrayList<String> list2 = Lists.newArrayList("aa", "bb", "cc", "rr");

        //保留交叉的，共有的
        Collection<String> intersection = CollectionUtils.intersection(list2, list1);
        System.out.println(intersection);//[aa, bb, cc]

        Collection<String> subtract1 = CollectionUtils.subtract(list1, list2);
        System.out.println(subtract1);//[pp]

        Collection<String> subtract = CollectionUtils.subtract(list2, list1);
        System.out.println(subtract);//[rr]
    }

    /**
     * hashMultiMap，一个key，多个value
     * value 的 类型是set
     * 一对多的Map
     */
    @Test
    public void hashMultiMap() {
        HashMultimap<Integer, Integer> hashMultimap = HashMultimap.create();
        hashMultimap.put(1, 2);
        hashMultimap.put(1, 2);
        hashMultimap.put(1, 32);
        hashMultimap.put(1, 32);
        hashMultimap.put(1, 24);
        hashMultimap.put(1, 24);

        System.out.println(hashMultimap);

        Set<Integer> set = hashMultimap.get(1);
        System.out.println(set);

    }

    /**
     * Lists.partition用法
     */
    @Test
    public void partition() {

        List<Integer> numList = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);

        //两个元素一组
        List<List<Integer>> partition = Lists.partition(numList, 2);
        System.out.println(partition);//[[1, 2], [3, 4], [5, 6], [7, 8]]

        partition.forEach(System.out::println);
    }

    @Test
    public void joinerNull(){
        List<String> lists = Lists.newArrayList();
        String join = Joiner.on(",").join(lists);
        System.out.println(join);

        String join1 = Joiner.on(";").join("aaa", join, "aaa");
        System.out.println(join1);
    }

    /**
     * Join连接器
     * 将集合转为特定规则的字符串
     * skipNulls
     */
    @Test
    public void JoinerToStr() {
        List<String> lists = Lists.newArrayList("a", "b", "g", null, "8", "", "9");
        String result = Joiner.on("--").skipNulls().join(lists);//跳过null的,不会跳过空串
        System.out.println(result); //a--b--g--8----9
    }

    /**
     * @author juzi
     * @date 2023/7/31 下午 1:48
     * @description useForNull
     */
    @Test
    public void JoinerToStr2() {
        // joiner useForNull(final String value)用value替换null元素值
        List<String> lists = Lists.newArrayList("a", "b", "g", null, "8", null, "9");
        String result = Joiner.on(",").useForNull("我来替换").join(lists);// 用值去替换空的
        System.out.println(result);
    }

    /**
     * @author juzi
     * @date 2023/7/31 下午 1:49
     * @description withKeyValueSeparator
     */
    @Test
    public void JoinMap() {
        // joiner withKeyValueSeparator(String value) map连接器，keyValueSeparator为key和value之间的分隔符
        Map<String, Integer> map = Maps.newHashMap();
        map.put("xiaoming", 12);
        map.put("xiaohong", 13);
        String result = Joiner.on("---").withKeyValueSeparator("===").join(map);
        System.out.println(result);//xiaohong===13---xiaoming===12
    }

    /**
     * Splitter拆分器
     * 将字符串拆分成指定的集合
     */
    @Test
    public void Splitter() {
        String test = "34344,34,34,哈哈";
        List<String> lists = Splitter.on(",").splitToList(test);
        System.out.println(lists);
//        结果：[34344, 34, 34, 哈哈]
    }

    /**
     * @author juzi
     * @date 2023/7/31 下午 1:55
     * @description trimResults
     * 删除首位的字符
     */
    @Test
    public void Splitter2() {
        //splitter trimResults 拆分去除前后空格
        String test = " =,  34344,34,34,哈哈  ,=  ";
        List<String> lists = Splitter.on(",").trimResults().splitToList(test);
        System.out.println(lists);
//        结果：[34344, 34, 34, 哈哈]

        List<String> lists2 = Splitter.on(",").trimResults(CharMatcher.is('=')).splitToList(test);
        System.out.println(lists2);
    }

    @Test
    public void Splitter3() {
        //splitter omitEmptyStrings 去除拆分出来空的字符串(去除空的)
        String test = "  ,,,,3434,434,34,,,,,,,哈哈 ";
        List<String> lists = Splitter.on(",").omitEmptyStrings().splitToList(test);
        System.out.println(lists);
//        结果：[  3434, 434, 34, 哈哈 ]
    }

    @Test
    public void Splitter4() {
        //splitter fixedLength(int lenght) 把字符串按固定长度分割
        String test = "343443434哈哈wo文档的你啊阿斯顿";
        List<String> lists = Splitter.fixedLength(3).splitToList(test);
        System.out.println(lists);
//        结果：[343, 443, 434, 哈哈]
    }

    /**
     * MoreObjects
     * 不用大量的重写 toString，用一种很优雅的方式实现重写，或者在某个场景定制使用。
     */
    @Test
    public void MoreObjects() {
        Cat cat = new Cat(22, MultiLanguageString.of(Maps.newHashMap()), 11, null);
        String str = MoreObjects.toStringHelper("catxxxx").add("state", cat.getState()).toString();
        System.out.println(str);
//输出Person{age=11}
    }

    /**
     * 代码的运行时间
     * 1. Guava 的方式
     * 计算中间代码的运行时间#
     */
    @Test
    public void StopWatch() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < 100000; i++) {
            // do some thing
        }
        long nanos = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        System.out.println(nanos);
    }

    /**
     * 2. spring的方式
     */
    @Test
    public void StopWatch2() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 100000; i++) {
        }
        stopWatch.stop();
        long totalTimeMillis = stopWatch.getTotalTimeMillis();
        System.out.println(totalTimeMillis);//2
    }

    @Test
    public void Lists() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(2, 2);
        map.put(3, 3);

        //直接放map的values
        List<Integer> list = Lists.newArrayList(map.values());
        System.out.println(list);


    }

}
