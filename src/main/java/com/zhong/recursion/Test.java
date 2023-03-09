package com.zhong.recursion;

import com.google.common.collect.Lists;
import com.zhong.cache.CategoryEntity;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * @date 2022/10/12 17:49
 */
public class Test {


    public static void main(String[] args) {
        Test r = new Test();
        List<Integer> next = r.getNext(0);
        System.out.println(next);

    }

    //❌
    public List<Integer> getNext(int deliver) {
        List<Integer> result = Lists.newArrayList();
        int num = find(deliver);
        result.add(num);
        if (num != 4) {
            deliver = num;
            getNext(deliver);
        }
        return result;
    }

    public int find(int num) {
        switch (num) {
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
        }
        return 8;
    }


    //入参，根据入参查询，拿着结果再查询


}
