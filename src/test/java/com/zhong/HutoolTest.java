package com.zhong;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import com.google.common.collect.Maps;
import com.zhong.entity.EntityTest;
import lombok.Data;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

/**
 * @date 2022/5/7 14:04
 */
public class HutoolTest {

    @Data
    static class AA{
        private Long id;
        private Integer name;
        private Integer age;
        private String sex;
        private Integer phone;
    }

    @Test
    public void copy2() {
        EntityTest entityTest = EntityTest.builder().id(1L).amount(BigDecimal.ZERO).name("ss").sex(false).build();
        AA aa = BeanUtil.copyProperties(entityTest, AA.class, "name");
        System.out.println(aa);
    }


    @Test
    public void SecureUtil() {
        String s = SecureUtil.md5("123456");
        System.out.println(s);//e10adc3949ba59abbe56e057f20f883e
    }

    /**
     * @author juzi
     * @date 2023/3/29 16:43
     * @description 日期
     */
    @Test
    public void date() {
        Date offset = DateUtil.date().offset(DateField.MINUTE, 30);
        System.out.println(offset);
        String formatDate = DateUtil.format(offset, DatePattern.NORM_DATETIME_FORMAT);
        System.out.println(formatDate);
    }


    /**
     * @author juzi
     * @date 2023/3/29 16:43
     * @description 复制
     */
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
