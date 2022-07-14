package com.zhong;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.math.MathUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.PageUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import com.alibaba.nacos.common.utils.ByteUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.zhong.cache.CacheMap;
import com.zhong.entity.*;
import lombok.var;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;

import javax.annotation.Resource;
import javax.sound.midi.Soundbank;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/10/14 10:20
 */
public class BaseTest {

    /**
     * final，第一次增加，第二次是原始值还是变化的值
     * final 不能加减
     */
    public static final Long finalNum = 10L;

    /**
     * 占位符
     * String.format
     * 来填充字符串
     */
    @Test
    public void format() {
        String format = String.format("http/%s/copy", "你好");//http/你好/copy
        System.out.println(format);

        //应用本地化
        String s = String.format(Locale.SIMPLIFIED_CHINESE, "you are very good %s and %S", "boy", "girl");
        System.out.println(s);

        //单字符
        String format1 = String.format("ni hao ya %C", 'b');
        System.out.println(format1);
        //boolean 类型
        String format2 = String.format("%b or %B", "false", "false");
        System.out.println(format2);
    }


    @Test
    public void finalTest() {
        for (int i = 0; i < 5; i++) {
//            finalNum++;
        }
        System.out.println(finalNum);

    }

    @Test
    public void LocaleTest() {
        String string = Locale.SIMPLIFIED_CHINESE.toString();
        System.out.println(string);
    }


    /**
     * null的不能用方法
     */
    @Test
    public void emptyForeach() {
        List<Long> list = null;
        list.forEach(System.out::println);
    }

    @Test
    public void LongT() {
        Long num = null;

//        NullPointerException
        System.out.println(num.intValue());

    }


    @Test
    public void split() {
        String ss = "sss 1654/*-/ ";
        String[] split = ss.split(",");
        System.out.println(Arrays.asList(split));//sss，没有指定符号是返回本身

    }

    @Test
    public void test() {
        int k = 10;
        if (k / 5 == 2) {
            System.out.println(111);
        } else if (k / 2 == 5) {
            System.out.println(222);
        } else {
            System.out.println(333);
        }
    }

    @Test
    public void test2() {
        String ss = "我是谁呀", qq = "zzqw";

        System.out.println(ss.length());
        System.out.println(qq.length());
        System.out.println(ss.getBytes().length);
        System.out.println(qq.getBytes().length);
        System.out.println(ss.getBytes(StandardCharsets.UTF_8).length);
        System.out.println(qq.getBytes(StandardCharsets.UTF_8).length);

    }

    /**
     * hutool
     * 测试枚举的 BeanUtil.copyProperties();
     * 枚举最好单独拿出来赋值，不要copy容易出错
     */
    @Test
    public void copy() {
        // 枚举 -> int
        Dog dog = Dog.builder().id(1).state(StateEnum.OPEN).build();
        Cat cat = new Cat();
        BeanUtil.copyProperties(dog, cat, CopyOptions.create().ignoreError());
        System.out.println(cat);//不行，是错误的
        //int -> 枚举
        Cat cat2 = Cat.builder().id(22).state(200).build();
//        Cat cat2 = Cat.builder().id(22).state(50).build();//会出错
        Dog dog2 = new Dog();
        BeanUtil.copyProperties(cat2, dog2, CopyOptions.create().ignoreError());
        System.out.println(dog2);
        System.out.println(dog2.getState().getCode() + "--" + dog2.getState().getMessage());

    }


    @Test
    public void test3() {
        Dog dog = Dog.builder().id(1).state(StateEnum.OPEN).build();
        Dog dog1 = new Dog();
        /**
         * 枚举类是会拷贝的
         */
        BeanUtil.copyProperties(dog, dog1);
        System.out.println(dog1);

        Cat cat = new Cat();
        /**
         * 枚举两种类型都可以拷贝的，只要有相同的类型
         */
        BeanUtil.copyProperties(dog, cat);
        System.out.println(cat);


    }

    @Test
    public void test4() {
        String name = "TEST NAME";
        String s = name.toLowerCase(Locale.ROOT);
        System.out.println(s);

        String ss = "SHI NI MA";
        String sss = ss.toLowerCase();
        System.out.println(sss);

    }


    @Test
    public void test5() {
        Map<String, String> skuExtendMap = Maps.newHashMap();

        skuExtendMap.put("orderType", "12");
        skuExtendMap.put("goodsCode", "123");
        skuExtendMap.put("guarantee", "13213");
        skuExtendMap.put("isTaxIncluded", "0");
        System.out.println(skuExtendMap.toString());

    }


    @Test
    public void ssss() {
        String s = "qw";
        System.out.println(s.length());
        String upperCase = s.toUpperCase(Locale.ROOT);
        System.out.println(upperCase);


    }

    @Test
    public void clearMap() {
        Map<String, Integer> map = new HashMap<>();
        map.clear();
        System.out.println(map);
        map.put("", 1);
        System.out.println(map);
        map.put("", null);
        System.out.println(map);
    }


    /**
     * %03d : 0表示填充的地方，3表示位数，d表示整型
     */
    @Test
    public void stringFormat() {
        int i1 = 3;
        int i2 = 13;
        int i3 = 33;

        String format1 = String.format("%03d", i1);
        String format2 = String.format("%03d", i2);
        String format3 = String.format("%03d", i3);

        System.out.println(format1 + " , " + format2 + " , " + format3);

    }

    @Test
    public void fin() {
        System.out.println(StandardCharsets.UTF_8);
        System.out.println(StandardCharsets.UTF_8.canEncode());
        System.out.println(StandardCharsets.UTF_8.name());
    }

    @Test
    public void replace() {
        String ss = "bbmall\\home\\sdf";
        String replace = ss.replace(File.separator, "/");
        System.out.println(replace);


    }


    @Test
    public void equals() {
        String s1 = "nnnnn";
        Object s2 = "nnnnn";

        boolean equals = s2.equals(s1);
        System.out.println(equals);
    }

    @Test
    public void bytes() {
        String ss = "abcsdqiladlkjadji{}。，/，‘；’~#@！#@￥#￥……%&……（&*——+——";
        byte[] bytes = ss.getBytes();
        System.out.println(bytes.toString());


        byte[] ssBytes = ss.getBytes(StandardCharsets.UTF_8);
        System.out.println(ssBytes.toString());


    }


    @Test
    public void test43() {
        Boolean b = null;
        System.out.println(b);//null

        b = false;
        System.out.println(b);//false

    }

    @Test
    public void testEqus() {
        boolean equals = Objects.equals("", null);
        System.out.println(equals);

        boolean equals2 = Objects.equals("nihao", null);
        System.out.println(equals2);

        String bb = null;
        boolean equals3 = Objects.equals("nihao", bb);
        System.out.println(equals3);
    }

    @Test
    public void testMap_value() {
        Map<Object, Object> map = new HashMap<>();
        map.put(1, null);
        map.put(2, null);
        map.put(3, null);
        map.put(null, null);
        //{null=null, 1=null, 2=null, 3=null}
        System.out.println(map);

        map.put(null, 1);
        //{null=1, 1=null, 2=null, 3=null}
        System.out.println(map);
    }

    @Test
    public void trs() {
        Long ll = 4L;
        this.trs_to(ll);
        System.out.println(ll);

        ll++;
        System.out.println(ll);
    }

    private void trs_to(Long ll) {
        System.out.println("trs_to " + ll);
        ll++;
        System.out.println("trs_to " + ll);
    }


    @Test
    public void oo() {
        ArrayList<Object> objects = Lists.newArrayList();
        objects.add(null);
        Object o = objects.get(0);
        System.out.println(o);//null

        int size = objects.size();
        System.out.println(size);//1

    }


    @Test
    public void BidiMap() {
        HashMap<Long, String> map = new HashMap<>();
        map.put(1L, "nihao");
        map.put(2L, "nihao2");
        map.put(3L, "nihao3");

        BidiMap<Long, String> bidiMap = new DualHashBidiMap<>(map);


        System.out.println(bidiMap);

        System.out.println(bidiMap.getKey("nihao2"));

    }

    @Test
    public void enums() {
        StateEnum stateEnum = StateEnum.getByCode(200);
        if (Objects.isNull(stateEnum)) {
            System.out.println("is null");
        } else {
            System.out.println("non null");
        }
        System.out.println(stateEnum);
    }


    @Test
    public void findAnyAndMap() {

        List<Integer> ids = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);

        Map<Integer, String> map = new HashMap<>();
        map.put(1, "w s  11");
        map.put(2, "w s  22");
        map.put(3, "w s  33");

        Optional<String> any = ids.stream().filter(map::containsKey).map(map::get).findAny();
        String orElse = any.orElse("没有");

        System.out.println(orElse);

    }


    @Test
    public void stringM() {
        String ss = "1231asd,asd";
        boolean contains = ss.contains("!");
        System.out.println(contains);


    }


    @Test
    public void overMap() {
        Map<Long, String> copy = new HashMap<>();
        copy.put(1L, "111");
        copy.put(2L, "222");
        copy.put(3L, "333");
        CacheMap.putAll(copy);
        System.out.println(CacheMap.getAll());

        System.out.println("--------------------------");

        Map<Long, String> map = new HashMap<>();
        map.put(4L, "444");
        map.put(5L, "555");
        map.put(6L, "666");
        CacheMap.putAll(map);
        System.out.println(CacheMap.getAll());
    }


    @Test
    public void Stringbuffer() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("1231321");
        stringBuffer.setLength(0);
        System.out.println(stringBuffer.toString());

        stringBuffer.append("asdasdasd");
        System.out.println(stringBuffer.toString());


    }

    public static void main(String[] args) throws IOException {
        List<Long> one = Lists.newArrayList();
        List<Long> two = Lists.newArrayList();
        List<Long> three = Lists.newArrayList();
        List<Long> four = Lists.newArrayList();

        IOUtils.readLines(Objects.requireNonNull(BaseTest.class.getClassLoader().getResourceAsStream("Yearning_Data (21).csv")), "UTF-8").forEach(x -> {

        });
    }


    @Test
    public void enum2List() {

        Set<String> stateSet = StateEnum.getStateSet();
        System.out.println(stateSet);

    }


    @Test
    public void div() {
        int aaa = 49;
        int bbb = 23;
//        for (int i = 0; i < aaa / bbb+1; i++) {
//            System.out.print( i+1 + "   ");
//        }

//        System.out.println(aaa/bbb+"----");
//        System.out.println(aaa%bbb+"++++");

//        int subtractExact = Math.subtractExact(aaa, bbb);
//        System.out.println(subtractExact);

        int floorDiv = Math.floorDiv(aaa, bbb);
        System.out.println(floorDiv);

        int ceil = (int) Math.ceil(aaa / bbb);
        System.out.println(ceil);

        int ccc = aaa % bbb == 0 ? (aaa / bbb) : (aaa / bbb) + 1;
        System.out.println(ccc);


    }


    @Test
    public void exc() {
        try {
            int k = 10 / 0;
            System.out.println("会执行吗？");
        } catch (Exception e) {
            System.out.println("eeeee");
        }


    }


    /**
     * <?> 和 <T>的区别
     */
    @Test
    public void testWenHao() {
        GenericsEntity<User> generics = new GenericsEntity<>(new User());
        generics.get(generics);

        GenericsEntity<?> f = new GenericsEntity<>(new User());
//        f.get(f);
    }


    private void wenhao(GenericsEntity<?> gg) {
        System.out.println("ggg ");
    }

    private void TT(GenericsEntity<T> tt) {
        System.out.println("TT");
    }

    @Test
    public void testWenhaoAndT() {
        this.wenhao(new GenericsEntity<>(new User()));

        this.TT(new GenericsEntity(new Dog()));
    }

    @Test
    public void listWenHaoAndT() {
        List<Object> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        List<Integer> list3 = new ArrayList<>();
        List<Long> list4 = new ArrayList<>();
        List<?> list5 = new ArrayList<>();
        List<T> list6 = new ArrayList<>();

//        this.listT(list1);
//        this.listT(list2);
//        this.listT(list3);
//        this.listT(list4);
//        this.listT(list5);
        this.listT(list6);

        this.listWenHao(list1);
        this.listWenHao(list2);
        this.listWenHao(list3);
        this.listWenHao(list4);
        this.listWenHao(list5);
        this.listWenHao(list6);
    }

    public void listT(List<T> list) {
        System.out.println("list t");
    }

    public void listWenHao(List<?> list) {
        System.out.println(list);
        System.out.println("list ?");
    }


    @Test
    public void setT() {
        Set<Integer> set = new HashSet<>();

        Set<Integer> set1 = Sets.newHashSet(1, 2, 3, 4);
        set.addAll(set1);
        Set<Integer> set2 = Sets.newHashSet(1, 5, 7);
        set.addAll(set2);
        Set<Integer> set3 = Sets.newHashSet(1, 2, 5);
        set.addAll(set3);
        Set<Integer> set4 = Sets.newHashSet(1, 3, 5);
        set.addAll(set4);
        Set<Integer> set5 = Sets.newHashSet(1, 6, 2);
        set.addAll(set5);

        System.out.println(set);


    }

    @Test
    public void tte() {
        boolean ff = false;
        this.booleanTest(ff);
    }

    //boolean 小写的 为必传的
    private void booleanTest(boolean fa) {
        System.out.println(fa);
    }

    @Test
    public void MapPut() {
        HashMap<String, String> map = new HashMap<String, String>() {{
            put("ItemBasePO.TABLE_NAME", "sss");
            put("ItemBaseLanguagePO.TABLE_NAME", "((sql, tableName) -> getTargetTableName(tableName))");
            put("ItemImagePO.TABLE_NAME", "((sql, tableName) -> getTargetTableName(tableName))");
            put("ItemDescPO.TABLE_NAME", "((sql, tableName) -> getTargetTableName(tableName))");
            put("ItemExtendPO.TABLE_NAME", "((sql, tableName) -> getTargetTableName(tableName))");
            put("SkuLanguagePO.TABLE_NAME", "((sql, tableName) -> getTargetTableName(tableName))");
        }};
        System.out.println(map.size());
        System.out.println(map);

        System.out.println(map.get("ItemDescPO.TABLE_NAME"));
    }

    @Test
    public void ArrTest() {
        String ss = ",123 , 12312 , , 123 , ";
        String[] split = ss.split(",");
        List<String> collect = Arrays.stream(split).filter(StringUtils::isNotBlank).collect(Collectors.toList());
        System.out.println(collect);
        System.out.println(Arrays.asList(split));
//        0:[123, 12312, , 123]
//       -1:[123, 12312, , 123, ]
//        1:[123,12312,,123,]
        List<String> strings = Lists.newArrayList(Arrays.asList(split));
        System.out.println(strings);

        String rr = "asd,1312 ,123e a, a,sd ,as,da,a ,d,,,as dad ,,";
        System.out.println(rr);
        Arrays.stream(rr.split(Character.toString(StrUtil.C_COMMA))).filter(StringUtils::isNotBlank).forEach(System.out::print);

    }

    @Test
    public void str() {
        String sss = " , ,11, 202, , 33 ,000 ,";
        List<String> collect = Arrays.stream(sss.split(Character.toString(StrUtil.C_COMMA))).filter(StringUtils::isNotBlank).collect(Collectors.toList());
        System.out.println(collect);
        collect.forEach(x -> System.out.println(Long.valueOf(x.trim())));//转long类型 有空格会报错


        System.out.println("ss:" + Character.toString(StrUtil.C_SPACE) + "aa");


    }

    @Test
    public void getLong() {
        Long sd = Long.getLong("lang");//null
        System.out.println(sd);
        String oop = "20 0";
        for (char c : oop.toCharArray()) {
            //if 是空格
            if (StrUtil.C_SPACE == c) {
                System.out.println("is" + c + "space");
            }
        }
    }

    /**
     * 空.trim() :NullPointerException
     */
    @Test
    public void trimE() {
        String ss = "";
        String trim = ss.trim();
        System.out.println(trim);

        String dd = null;
        String trim1 = dd.trim();
        System.out.println(trim1);
    }

    /**
     * 测试 byte[0] 是否为空：为空
     */
    @Test
    public void byteTest() {
        int n = 0;
        byte[] bytes = new byte[n];
        if (ByteUtils.isEmpty(bytes)) {
            System.out.println("byte[" + n + "] 是空的");
        } else {
            System.out.println("byte[" + n + "] 不是空的,bytes=" + Arrays.toString(bytes));
        }

    }

}
