package com.zhong;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.math.MathUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.PageUtil;
import cn.hutool.json.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhong.cache.CacheMap;
import com.zhong.entity.*;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/10/14 10:20
 */
public class BaseTest {


    @Test
    public void test(){
        int k = 10;
        if (k/5 == 2 ){
            System.out.println(111);
        }else if (k/2 == 5){
            System.out.println(222);
        } else {
            System.out.println(333);
        }
    }

    @Test
    public void test2(){
        String ss = "我是谁呀",qq = "zzqw";

        System.out.println(ss.length());
        System.out.println(qq.length());
        System.out.println(ss.getBytes().length);
        System.out.println(qq.getBytes().length);
        System.out.println(ss.getBytes(StandardCharsets.UTF_8).length);
        System.out.println(qq.getBytes(StandardCharsets.UTF_8).length);

    }


    @Test
    public void test3(){
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
    public void test4(){
        String name = "TEST NAME";
        String s = name.toLowerCase(Locale.ROOT);
        System.out.println(s);

        String ss = "SHI NI MA";
        String sss = ss.toLowerCase();
        System.out.println(sss);

    }


    @Test
    public void test5(){
        Map<String, String> skuExtendMap = Maps.newHashMap();

                skuExtendMap.put("orderType", "12");
                skuExtendMap.put("goodsCode", "123");
                skuExtendMap.put("guarantee", "13213");
                skuExtendMap.put("isTaxIncluded", "0");
        System.out.println(skuExtendMap.toString());

    }


    @Test
    public void ssss(){
        String s = "qw";
        System.out.println(s.length());
        String upperCase = s.toUpperCase(Locale.ROOT);
        System.out.println(upperCase);


    }

    @Test
    public void clearMap(){
        Map<String, Integer> map = new HashMap<>();
        map.clear();
        System.out.println(map);
        map.put("", 1);
        System.out.println(map);
        map.put("", null);
        System.out.println(map);
    }


    /**
     *    %03d : 0表示填充的地方，3表示位数，d表示整型
     */
    @Test
    public void stringFormat(){
        int i1 = 3;
        int i2 = 13;
        int i3 = 33;

        String format1 = String.format("%03d", i1);
        String format2 = String.format("%03d", i2);
        String format3 = String.format("%03d", i3);

        System.out.println(format1 +" , "+ format2+" , "+  format3);

    }

    @Test
    public void fin(){
        System.out.println(StandardCharsets.UTF_8);
        System.out.println(StandardCharsets.UTF_8.canEncode());
        System.out.println(StandardCharsets.UTF_8.name());
    }

    @Test
    public void replace(){
        String ss = "bbmall\\home\\sdf";
        String replace = ss.replace(File.separator, "/");
        System.out.println(replace);


    }


    @Test
    public void equals(){
        String s1 = "nnnnn";
        Object s2 = "nnnnn";

        boolean equals = s2.equals(s1);
        System.out.println(equals);
    }

    @Test
    public void bytes(){
        String ss = "abcsdqiladlkjadji{}。，/，‘；’~#@！#@￥#￥……%&……（&*——+——";
        byte[] bytes = ss.getBytes();
        System.out.println(bytes.toString());


        byte[] ssBytes = ss.getBytes(StandardCharsets.UTF_8);
        System.out.println(ssBytes.toString());


    }


    @Test
    public void test43(){
        Boolean b = null;
        System.out.println(b);//null

        b = false;
        System.out.println(b);//false

    }

    @Test
    public void testEqus(){
        boolean equals = Objects.equals("", null);
        System.out.println(equals);

        boolean equals2 = Objects.equals("nihao", null);
        System.out.println(equals2);

        String bb = null;
        boolean equals3 = Objects.equals("nihao", bb);
        System.out.println(equals3);
    }

    @Test
    public void testMap_value(){
        Map<Object, Object> map = new HashMap<>();
        map.put(1,null);
        map.put(2,null);
        map.put(3,null);
        map.put(null, null);
        //{null=null, 1=null, 2=null, 3=null}
        System.out.println(map);

        map.put(null, 1);
        //{null=1, 1=null, 2=null, 3=null}
        System.out.println(map);
    }

    @Test
    public void trs(){
        Long ll = 4L;
        this.trs_to(ll);
        System.out.println(ll);

        ll++;
        System.out.println(ll);
    }
    private void trs_to(Long ll){
        System.out.println("trs_to " + ll );
        ll++;
        System.out.println("trs_to " + ll );
    }



    @Test
    public void oo(){
        ArrayList<Object> objects = Lists.newArrayList();
        objects.add(null);
        Object o = objects.get(0);
        System.out.println(o);//null

        int size = objects.size();
        System.out.println(size);//1

    }



    @Test
    public void BidiMap(){
        HashMap<Long, String> map = new HashMap<>();
        map.put(1L, "nihao");
        map.put(2L, "nihao2");
        map.put(3L, "nihao3");

        BidiMap<Long, String> bidiMap = new DualHashBidiMap<>(map);


        System.out.println(bidiMap);

        System.out.println(bidiMap.getKey("nihao2"));

    }

    @Test
    public void enums(){
        StateEnum stateEnum = StateEnum.getByCode(200);
        if (Objects.isNull(stateEnum)){
            System.out.println("is null");
        }else {
            System.out.println("non null");
        }
        System.out.println(stateEnum);
    }



    @Test
    public void findAnyAndMap(){

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
    public void stringM(){
        String ss = "1231asd,asd";
        boolean contains = ss.contains("!");
        System.out.println(contains);


    }



    @Test
    public void overMap(){
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
    public void Stringbuffer(){
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

        IOUtils.readLines(Objects.requireNonNull(BaseTest.class.getClassLoader().getResourceAsStream("Yearning_Data (21).csv")),"UTF-8").forEach(x->{

        });
    }



    @Test
    public void enum2List(){

        Set<String> stateSet = StateEnum.getStateSet();
        System.out.println(stateSet);

    }


    @Test
    public void div(){
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

        int ccc = aaa%bbb == 0 ? (aaa/bbb) : (aaa/bbb)+1;
        System.out.println(ccc);


    }


    @Test
    public void exc(){
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


}
