package com.zhong;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @date 2022/9/20 15:55
 */
public class AtomicTest {

    @Test
    public void ss() {
        List<String> list = Lists.newArrayList("1", "18", "91", "15", "13", "21");
        List<String> list2 = Lists.newArrayList("2", "28", "92", "25", "23", "22");
        Map<String, List<String>> map = new HashMap<String, List<String>>() {{
            put("AA", list);
            put("BB", list2);
        }};

        map.forEach((k, v) -> {
            AtomicInteger s = new AtomicInteger(-1);
            v.forEach(x -> {
                s.getAndIncrement();
                if (s.get() == 0) {
                    return;
                }
                System.out.println(s.get() + " " + x);
            });
        });


    }


    @Test
    public void ee() {
        AtomicReference<String> atomicReference = new AtomicReference<>();

    }

}
