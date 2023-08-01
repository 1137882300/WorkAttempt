package com.zhong;

import com.zhong.entity.EntityTest;
import org.junit.Test;

/**
 * @author: juzi
 * @date: 2023/8/1
 * @desc:
 */
public class SpecialTest {


    /**
     * @author juzi
     * @date 2023/8/1 上午 10:37
     * @description 获取类的全限定路径：entityTest.getClass().getTypeName();
     */
    @Test
    public void classTest(){
        EntityTest entityTest = new EntityTest();
        String typeName = entityTest.getClass().getTypeName();
        System.out.println(typeName);//com.zhong.entity.EntityTest
    }

}
