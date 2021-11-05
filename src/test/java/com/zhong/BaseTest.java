package com.zhong;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.PageUtil;
import com.zhong.entity.Cat;
import com.zhong.entity.Dog;
import com.zhong.entity.StateEnum;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

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



}
