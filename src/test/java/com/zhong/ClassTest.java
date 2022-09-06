package com.zhong;

import org.junit.Test;

/**
 * @date 2022/9/2 17:42
 */
public class ClassTest {

    @Test
    public void test() {

        String name = ClassTest.class.getName();
        String simpleName = ClassTest.class.getSimpleName();

        System.out.println(
                name + "\n" +
                        simpleName
        );


    }
}
