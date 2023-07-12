package com.zhong;

import com.zhong.entity.EntityTest;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.Test;


/**
 * @author: juzi
 * @date: 2023/7/12
 * @desc:
 */
public class Lang3Test {

    @Test
    public void isNotEmpty() {
        EntityTest test = new EntityTest();
        boolean b = ObjectUtils.isNotEmpty(test);// true
        System.out.println(b);
    }

}
