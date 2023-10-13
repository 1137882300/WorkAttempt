package com.zhong;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhong.entity.EntityTest;
import lombok.Builder;
import lombok.Data;
import org.junit.Test;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @date 2022/5/7 14:04
 */
public class HutoolTest {

    @Data
    static class AA {
        private Long id;
        private Integer name;
        private Integer age;
        private String sex;
        private Integer phone;
    }

    @Data
    @Builder
    public static class FlowData {
        private String name;
        private String ts;
        private double tengxun_lng;
        private double tengxun_lat;
        private String type;
        private int value;
        private String createdAt;
        private String deletedAt;
        private String updatedAt;
    }

    @Data
    @Builder
    public static class MongoAsianFlowDataModel implements Serializable {
        private static final long serialVersionUID = 1L;
        private Integer id;
        private Integer MongoAsianDataId;
        private Integer value;
        private String name;
        private String ts;
        private String tengxun_lng;
        private String tengxun_lat;
        private String type;
        private Byte deleted;
        private Date createdAt;
        private Date deletedAt;
        private Date updatedAt;
    }

    //String -> double
    //Integer -> int
    //都是ok的
    @Test
    public void copyList() {
        ArrayList<MongoAsianFlowDataModel> list = Lists.newArrayList(
                MongoAsianFlowDataModel.builder().id(1).name("111").ts("112").tengxun_lng("113").tengxun_lat("114").value(115).updatedAt(new Date()).createdAt(new Date()).build(),
                MongoAsianFlowDataModel.builder().id(1).name("211").ts("212").tengxun_lng("213").tengxun_lat("214").value(215).updatedAt(new Date()).createdAt(new Date()).build(),
                MongoAsianFlowDataModel.builder().id(1).name("311").ts("312").tengxun_lng("313").tengxun_lat("314").value(315).updatedAt(new Date()).createdAt(new Date()).build()
        );
        List<FlowData> flowData = BeanUtil.copyToList(list, FlowData.class, CopyOptions.create().setIgnoreProperties("updatedAt", "createdAt"));
        System.out.println(flowData);
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
