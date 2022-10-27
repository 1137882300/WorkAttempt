package com.zhong.reflex;

import com.zhong.entity.Dog;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @date 2022/10/27 15:06
 */
public class Demo {


    @SneakyThrows
    public static void main(String[] args) {
        Dog dog = new Dog();

        Field field = Dog.class.getDeclaredField("state");
        System.out.println(field);
        String name = field.getName();
        System.out.println(name);
        System.out.println(Arrays.stream(field.getDeclaredAnnotations()).toArray());

        System.out.println(dog);
    }

}
