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
import com.zhong.ding.excel.Entity;
import com.zhong.entity.*;
import lombok.var;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;

import javax.annotation.Resource;
import javax.sound.midi.Soundbank;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
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
     * Long.parseLong, 空字符串也不行
     */
    @Test
    public void toTong() {
        long l = Long.parseLong("11aa1");
        System.out.println(l);
    }

    @Test
    public void percent() {
        String ss = "+++++";
        final String replaceAll = ss.replaceAll("\\+", "%20");
        System.out.println(replaceAll);
    }

    /**
     * boolean
     */
    @Test
    public void and() {
        boolean ff = false && false;
        System.out.println(ff);//false

    }

    /**
     * 位运算
     */
    @Test
    public void move() {
        System.out.println(1 << 30);//1073741824
        System.out.println(1 << 4);//16
    }

    /**
     * 多个字段联合去重
     */
    @Test
    public void duplicateRemoval() {
        List<User> list = new ArrayList<User>() {{
            add(new User(1, 1));
            add(new User(2, 3));
            add(new User(3, 4));
            add(new User(6, 5));
            add(new User(6, 1));
            add(new User(6, 1));
            add(new User(6, 6));
//            add(new User(6,6));
        }};
        ArrayList<User> collect = list.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(
                () -> new TreeSet<>(Comparator.comparing(o -> o.getId() + ";" + o.getAge()))
        ), ArrayList::new));

        System.out.println(collect);
    }


    /**
     * 集合 引用
     * 集合里的值跟着变了
     */
    @Test
    public void aggregateQuote() {
        List<User> userList = Lists.newArrayList(new User(1, 111));
        userList.add(new User(2, 222));
        userList.add(new User(3, 333));
        userList.add(new User(4, 444));
        userList.add(new User(5, 555));

        Map<Integer, List<User>> groupMap = userList.stream().collect(Collectors.groupingBy(User::getId));

        userList.forEach(x -> {
            x.setAge(666);
        });

        groupMap.forEach((k, v) -> {
            System.out.printf("k=%s ; v=%s %n", k, v);
        });

    }

    /**
     * 位运算,
     * & : 两个都是1，则为 1
     * ~ : 取反,0变1，1变0
     */
    @Test
    public void bitwiseOperation() {
        int ss = 1 & ~Modifier.FINAL;
        System.out.println(ss);

        int dd = 1 & ~1;
        System.out.println(dd);
    }

    /**
     * 可以为null
     */
    @Test
    public void stringUtil() {
        String ss = null;
        String ff = "ss";
        String rr = "ss";
        boolean equals = StringUtils.equals(ff, rr);
        System.out.println(equals);

    }

    /**
     * 只提取末尾的 数字开头到结尾
     */
    @Test
    public void findNumByString() {
        String str = "1案例qweq4weqw2qwe";
        String str2 = str.replaceAll(".*[^\\d](?=(\\d+))", "");
        System.out.println(str2);//2qwe
    }

    @Test
    public void localeTT() {
        Locale locale = new Locale("syr-SY");
        System.out.println(locale);
    }

    @Test
    public void sqlString() {
        int a = 11;
        int b = 33;
        int c = 44;
        String ss = "INSERT INTO `t_area_info_language` " +
                "(`id`, `area_info_id`, `preferred_language`, `creator_id`, `modified_id`, `create_time`, `update_time`, `is_deleted`, `version`, `area_name`) " +
                "VALUES (" + a + ", " + b + ", 'zh_CN', -10, -10, 1659597815000, 1659597815000, 0, 0, '" + c + "');";
        System.out.println(ss);
    }

    @Test
    public void replaceT() {
        String ss = " asd dsa fjd ";
        String replace = ss.replace(" ", "");
        System.out.println(replace);
    }

    /**
     * 某个字符在字符串里的个数
     */
    @Test
    public void judge() {
        String ss = "[EX14 0AG, Yes, 50.799352, -3.187295, 316424, 100649, ST164006, \"Devon\", \"East Devon\", \"Honiton St Paul's\", E07000040, E05011798, England, E10000008, \"Tiverton and Honiton\", 2016-08-01, , \"Honiton\", \"\", , , \"Honiton\", \"\", \"East Devon 002B\", \"Urban city and town\", South West, 99, , E01019932, Devon, E02004130, \"Honiton North & East\", E04002967, E00100896, E14000996, 11603, 1, 1, 2022-05-26, \"Honiton\", 0.310163, EX, EX14, \"Devon & Cornwall\", \"South West Water\", 9C2RQRX7+P3, 35000, \"\", \"Exeter\", \"Devon\", \"Devon CC\", \"10092788332\", 12.8701]";
        String s2 = "[EX14 1AA, Yes, 50.798191, -3.190036, 316229, 100523, ST162005, \"Devon\", \"East Devon\", \"Honiton St Michael's\", E07000040, E05011797, England, E10000008, \"Tiverton and Honiton\", 1999-12-01, , \"Honiton\", \"\", 30, 27, \"Honiton\", \"\", \"East Devon 002A\", \"Urban city and town\", South West, 109, , E01019927, Devon, E02004130, \"Honiton North & East\", E04002967, E00100864, E14000996, 11056, 1, 0, 2022-05-26, \"Honiton\", 0.293523, EX, EX14, \"Devon & Cornwall\", \"South West Water\", 9C2RQRX5+7X, 35000, \"\", \"Exeter\", \"Devon\", \"Devon CC\", \"10000264310, 10025072959, 10090911023, 100040181221, 100040181222, 100040181223, 100040181224, 100040181225, 100040181226, 100040181227, 100040181228, 100040181229, 100040181230, 100040181231, 100040181232, 100040181233, 100040181234, 100040181235, 100040181236, 100040181237, 100040181238, 100040181239, 100040181240, 100040181241, 100040181242, 100040181243, 100040181244, 100040181245, 100040181246, 100040181247, 100040181248, 100040181249, 100040181250, 100040181251, 100040181252, 100041223272\", 12.7701]";
        String s3 = "[EX14 1AL, Yes, 50.796704, -3.198314, 315643, 100367, ST156003, \"Devon\", \"East Devon\", \"Honiton St Michael's\", E07000040, E05011797, England, E10000008, \"Tiverton and Honiton\", 1999-12-01, , \"Honiton\", \"\", 62, 22, \"Honiton\", \"\", \"East Devon 003A\", \"Urban city and town\", South West, 107, , E01019928, Devon, E02004131, \"Honiton South & West\", E04002967, E00100861, E14000996, 19116, 1, 0, 2022-05-26, \"Honiton\", 0.814538, EX, EX14, \"Devon & Cornwall\", \"South West Water\", 9C2RQRW2+MM, 39600, \"\", \"Exeter\", \"Devon\", \"Devon CC\", \"10000250573, 10000278338, 10023094622, 10023094637, 10024073107, 10024726305, 10093676580, 100040179917, 100040179921, 100040179922, 100040179923, 100040179925, 100040179931, 100040179941, 100040179946, 100040179950, 100040179955, 100040179958, 100040179961, 100040179962, 100040179963, 100040179964, 100040179966, 100040179967, 100040179982, 100040179983, 100041128499, 100041128766, 100041128812, 100041223240, 100041223241\", 12.691]";

        System.out.println(ss.split(",").length - 1);
        System.out.println(s2.split(",").length - 1);
        System.out.println(s3.split(",").length - 1);

    }

    @Test
    public void localeTest() {
        Locale locale = LocaleUtils.toLocale("id");
//        in
        System.out.println(locale);

        String language = Locale.CHINA.getLanguage();
//        zh
        System.out.println(language);

//        zh
        System.out.println(LocaleUtils.toLocale(Locale.CHINA.getLanguage()));

        Locale locale1 = new Locale("in_ID");
        System.out.println(locale1);
        String language1 = locale1.getLanguage();
        System.out.println(language1);
        String country = locale1.getCountry();
        System.out.println(country);

        Locale locale2 = LocaleUtils.toLocale("in_ID");
        System.out.println(locale2.getLanguage());
        String country1 = locale2.getCountry();
        System.out.println(country1);


        Locale forLanguageTag = Locale.forLanguageTag("id");//语言环境
        System.out.println(forLanguageTag);//in
    }

    @Test
    public void localeTest2() {
        Locale locale = LocaleUtils.toLocale("hy");
        String string = locale.getLanguage();
        System.out.println(string);

        List<Locale> en = LocaleUtils.countriesByLanguage("hy");
        System.out.println(en);

        List<Locale> hy = LocaleUtils.languagesByCountry("hy");
        System.out.println(hy);
    }

    /**
     * final，第一次增加，第二次是原始值还是变化的值
     * final 不能加减
     */
    public static final Long finalNum = 10L;

    /**
     * String的方法测试
     */
    @Test
    public void StringTest() {
        //取出最后匹配的字符串后面的所有
        String s = StringUtils.substringAfterLast("hi.shop.name.good.hello.shop.name.end", "shop.name.");//end
        System.out.println(s);

        String after = StringUtils.substringAfter("hi.shop.name.good.hello.shop.name.end", "shop.name.");//good.hello.shop.name.end
        System.out.println(after);

        String substringBefore = StringUtils.substringBefore("start.hi.shop.name.good.hello.shop.name.end", "shop.name.");//start.hi.
        System.out.println(substringBefore);

        //取最后匹配到的前面的字符串
        String beforeLast = StringUtils.substringBeforeLast("start.hi.shop.name.good.hello.shop.name.end", "shop.name.");//start.hi.shop.name.good.hello.
        System.out.println(beforeLast);

        String substringBetween = StringUtils.substringBetween("start.hi.shop.name.good.hello.shop.name.end", "shop.name.");//good.hello.
        System.out.println(substringBetween);

        String[] strings = StringUtils.substringsBetween("start.hi.shop.name.hi.good.hello.shop.name.wo.hi.end", "name", "hi");
        System.out.println(Arrays.asList(strings));

    }

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
        String ss = "1231asd,Thailand_britain_Address";
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

        String rr = "Thailand_britain_Address,1312 ,123e a, a,sd ,as,da,a ,d,,,as dad ,,";
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
