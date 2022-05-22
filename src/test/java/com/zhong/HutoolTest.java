package com.zhong;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

/**
 * @date 2022/5/7 14:04
 */
public class HutoolTest {


    @Test
    public void copy() {

        Map<String, String> extendMap = Maps.newHashMap();
        extendMap.put("key1", "value1");
        extendMap.put("key2", "value2");
        extendMap.put("key3", "value3");

        Map<String, String> extendMap2 = Maps.newHashMap();

        /*
            hutool 可以copy map, CopyOptions 复制选项
         */
        BeanUtil.copyProperties(extendMap, extendMap2, CopyOptions.create().ignoreNullValue().ignoreError());

        System.out.println(extendMap2.values());


    }


}
