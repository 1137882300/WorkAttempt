package com.zhong;

import com.google.common.collect.Lists;
import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.util.List;
import java.util.function.Consumer;

/**
 * @date 2022/4/22 17:29
 */
public class FunctionalInterfaceTest {

    /*
     * Consumer<T>  接收T类型参数，不返回值
     */
    @Test
    public void ConsumerTest() {
        // 定义一个功能
        Consumer<String> consumer = System.out::println;


        consumer.accept("nihao 1");
        consumer.accept("nihao 2");
        consumer.accept("nihao 3");
        //输出结果
        //nihao 1
        //nihao 2
        //nihao 3
    }

    @Test
    public void ConsumerLambda() {
        List<Integer> list1 = Lists.newArrayList();
        List<Integer> list2 = Lists.newArrayList();

        Consumer<Integer> consumer = s -> {
            if (s % 2 == 0) {
                list1.add(s);
            } else {
                list2.add(s);
            }
        };

        Consumer<List<Integer>> listConsumer = l -> {
            l.forEach(System.out::println);
        };

        consumer.accept(2);
        consumer.accept(3);
        consumer.accept(4);
        consumer.accept(5);

        System.out.println("list1");
        listConsumer.accept(list1);
        System.out.println("list2");
        listConsumer.accept(list2);
    }

    /**
     * 先执行一个 consumer 再执行另一个 consumer
     */
    @Test
    public void andThen() {
        Consumer<Integer> consumer1 = System.out::println;
        consumer1.accept(1);
        consumer1.accept(2);
        consumer1.accept(3);

        Consumer<Integer> consumer2 = System.out::println;
        consumer2.accept(4);
        consumer2.accept(5);
        consumer2.accept(6);

        consumer1.andThen(consumer2);


    }

}
