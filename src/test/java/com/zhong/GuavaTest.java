package com.zhong;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2022/1/21 11:34
 */
public class GuavaTest {

    /**
     * Lists.partition用法
     */
    @Test
    public void partition(){

        List<Integer> numList = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8);

        List<List<Integer>> partition = Lists.partition(numList, 2);
        System.out.println(partition);//[[1, 2], [3, 4], [5, 6], [7, 8]]

        partition.forEach(System.out::println);
    }




}
