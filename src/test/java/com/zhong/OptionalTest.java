package com.zhong;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.zhong.entity.People;
import com.zhong.entity.User;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.junit.Before;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import javax.annotation.Resource;
import java.awt.image.ImageProducer;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/10/13 14:08
 */
public class OptionalTest {

    private User u1, u2, u3, u4, u5, u6, u7;
    private People u8;
    private List<User> userList;

    @Before
    public void init() {
        u1 = User.builder().id(1).age(4).build();
        u2 = User.builder().id(2).age(34).build();
        u3 = User.builder().id(3).age(15).build();
        u4 = User.builder().id(4).age(52).build();
        u5 = User.builder().id(5).age(23).build();
        u6 = User.builder().id(6).age(51).build();

        u8 = People.builder().sex(22).build();
    }

    /**
     * @author juzi
     * @date 2023/5/16 下午 2:56
     * @description Optional.map 不會空指針
     */
    @Test
    public void map() {
//        userList = null; 也不會空指針
        List<Integer> integers = Optional.ofNullable(userList)
                .map(x -> x.stream().map(User::getId)
                        .collect(Collectors.toList()))
                .orElseGet(Lists::newArrayList);
        System.out.println(integers);
    }

    /**
     * @author juzi
     * @date 2023/4/11 13:56
     * @desc 当ss不是空时，orElse依然会执行，orElseGet才不会执行
     * 当ss是空时，orElse会执行，orElseGet会执行
     */
    @Test
    public void orElse() {
        Integer ss = null;
        Integer orElse = Optional.ofNullable(ss).orElse(orElseTest("orElse"));
        System.out.println(orElse);

        Integer orElseGet = Optional.ofNullable(ss).orElseGet(() -> orElseTest("orElseGet"));
        System.out.println(orElseGet);
    }

    private Integer orElseTest(String ss) {
        System.out.println(ss + " 依然执行了");
        return 20;
    }

    /**
     * @author juzi
     * @date 2023/4/4 9:48
     * @description 纯用Optional报错，String类型不合适
     */
    @Test
    public void orElseThrow() {
        String expiryDate = "";
        Optional.ofNullable(expiryDate).orElseThrow(() -> new RuntimeException("sss"));
    }

    /**
     * 区别
     * .flatMap：返回的不是Optional，需要自己包装成Optional
     * .map：返回的就是Optional
     */
    @Test
    public void flatMap_pk_map() {
//        Optional.ofNullable(u8).flatMap(People::getSex);//报错
        Optional<Integer> flatMap = Optional.ofNullable(u8).flatMap(x -> Optional.ofNullable(x.getSex()));
        System.out.println(flatMap);
        Optional<Integer> map = Optional.ofNullable(u8).map(People::getSex);
        System.out.println(map);
    }


    @Test
    public void test() {
        List<User> list = Lists.newArrayList(u1, u2);
        List<User> list2 = Lists.newArrayList(u5, u6);
        Optional<List<User>> optional1 = Optional.of(Lists.newArrayList(u3, u4));

        List<People> people = Lists.newArrayList(u8);

        Optional<List<User>> optional;
        optional = Optional.of(list);
        System.out.println(optional);
        System.out.println("-----------------------------");
        optional = Optional.of(list2);
        System.out.println(optional);
        System.out.println("--------------------------------");
        optional = optional1;
        System.out.println(optional);

    }

    /**
     * Optional.ofNullable
     */
    @Test
    public void test2() {
        Integer k = null;
        Integer integer = Optional.ofNullable(k).orElse(11);
        System.out.println(integer);

    }

    @Test
    public void emptyList() {

        List<User> list = new ArrayList<>();

//        Optional.of(list).ifPresent(s -> s.stream().map(User::getAge).collect(Collectors.toList()));
        Optional.of(list).ifPresent(s -> s.stream().filter(q -> q.getId() == 1).collect(Collectors.toList()));

    }

    /**
     * 嵌套 optical
     */
    @Test
    public void testt() {
        String ss = "" +
                "tero:\n" +
                "  file:\n" +
                "    aliyun:\n" +
                "      oss:\n" +
                "        access-key-id: 11111111111111\n" +
                "        access-key-secret: 2222222222222\n" +
                "        expired-minutes: 30\n" +
                "        bucket-name: 5555555555\n" +
                "        endpoint: 333333333333333\n" +
                "        internet-file-url: xxxx/\n" +
                "        uploadPath: 44444444444444444\n" +
                "        maxSize: 10000000\n"
                + "    suffix: JPG,PNG,PDF,XLS,XLSX,JPEG,DOC,DOCX,ZIP,PPT,PPTX,JSON";

        Yaml yaml = new Yaml();
        Map map = yaml.loadAs(ss, Map.class);

        Optional.ofNullable(map)
                .map(tero -> (Map) map.get("tero"))
                .map(file -> (Map) file.get("file"))
                .map(aliyun -> (Map) aliyun.get("aliyun"))
                .map(oss -> (Map) oss.get("oss"))
                .ifPresent(r -> {
                    System.out.println(
                            "1: " + r.get("endpoint") + "\n" +
                                    "2: " + r.get("access-key-id") + "\n" +
                                    "3: " + r.get("access-key-secret") + "\n" +
                                    "4: " + r.get("bucket-name") + "\n" +
                                    "5: " + r.get("uploadPath"));
                });


//        System.out.println(map);
//        Optional.ofNullable((Map) map.get("tero"))
//                .flatMap(x -> Optional.ofNullable((Map) x.get("file"))
//                        .filter(t -> BooleanUtils.toBoolean(String.valueOf(t.remove("suffix"))))
//                        .flatMap(o -> Optional.ofNullable((Map) o.get("aliyun"))
//                                .flatMap(p -> Optional.ofNullable((Map) p.get("oss")))))
//                .ifPresent(r -> {
//                    System.out.println(
//                            "1: " + r.get("endpoint") + "\n" +
//                                    "2: " + r.get("access-key-id") + "\n" +
//                                    "3: " + r.get("access-key-secret") + "\n" +
//                                    "4: " + r.get("bucket-name") + "\n" +
//                                    "5: " + r.get("uploadPath"));
//                });


    }

//                   .ifPresent(r -> {
//                    System.out.println(
//                            "1: " + r.get("endpoint") + "\n" +
//                            "2: " + r.get("access-key-id") + "\n" +
//                            "3: " + r.get("access-key-secret") + "\n" +
//                            "4: " + r.get("bucket-name") + "\n" +
//                            "5: " + r.get("uploadPath"));
//                      });
}
