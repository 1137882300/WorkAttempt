package com.zhong;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.PageUtil;
import cn.hutool.json.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhong.entity.Cat;
import com.zhong.entity.Dog;
import com.zhong.entity.StateEnum;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
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

}
