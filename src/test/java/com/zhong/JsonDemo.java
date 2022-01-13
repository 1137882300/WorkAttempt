package com.zhong;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhong.entity.*;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/11/26 17:50
 */
public class JsonDemo {


    @Test
    public void te (){


        List<Dog> dogs = new ArrayList<>();
        Dog dog = Dog.builder().id(1).state(StateEnum.OPEN).build();
        dogs.add(dog);
        MultiLanguageString string = new MultiLanguageString();
        string.registerDefaultLanguage("巴林");
        ObList obList = ObList.builder().id(23).dogList(dogs).fromCountries(string).build();
        String s = JSON.toJSONString(obList);
        System.out.println(s);

    }
//    itemTransport:{}
//    "fromCountries":[{"countryId":1,"countryAbbreviation":"火星"}],"toCountries":[{"countryId":2,"countryAbbreviation":"巴林"}]


//    "fromCountries":[{"countryId":1,"countryAbbreviation":{"allLanguageString":{"in_ID":"火星"},"defaultLanguageString":"火星","defaultLocale":"in_ID","languageStringMap":{"$ref":"$.fromCountries.allLanguageString"}}}],"toCountries":[{"countryId":2,"countryAbbreviation":{"allLanguageString":{"in_ID":"巴林"},"defaultLanguageString":"巴林","defaultLocale":"in_ID","languageStringMap":{"$ref":"$.fromCountries.allLanguageString"}}}]
//    "fromCountries":[{"countryId":6,"countryAbbreviation":"缅甸"}],"toCountries":[{"countryId":10,"countryAbbreviation":"香港"}]


//    {            "countryAbbreviation":{"allLanguageString":{"in_ID":"巴林"},"defaultLanguageString":"巴林","defaultLocale":"in_ID","languageStringMap":{"$ref":"$.fromCountries.allLanguageString"}}       }
//    {            "countryAbbreviation":{"allLanguageString":{"in_ID":"缅甸"},"defaultLanguageString":"缅甸","defaultLocale":"in_ID","languageStringMap":{"$ref":"$.fromCountries.allLanguageString"}}       }

//    ,"fromCountries":[{"countryId":6,"countryAbbreviation":{"allLanguageString":{"in_ID":"巴林"},"defaultLanguageString":"巴林","defaultLocale":"in_ID","languageStringMap":{"$ref":"$.fromCountries.allLanguageString"}}}]
//    ,"toCountries":[{"countryId":10,"countryAbbreviation":{"allLanguageString":{"in_ID":"缅甸"},"defaultLanguageString":"缅甸","defaultLocale":"in_ID","languageStringMap":{"$ref":"$.fromCountries.allLanguageString"}}}]


//    "fromCountries":[{"countryId":6,"countryAbbreviation":{"allLanguageString":{"in_ID":"缅甸"},"defaultLanguageString":"缅甸","defaultLocale":"in_ID","languageStringMap":{"$ref":"$.fromCountries.allLanguageString"}}}],"toCountries":[{"countryId":10,"countryAbbreviation":{"allLanguageString":{"in_ID":"香港"},"defaultLanguageString":"香港","defaultLocale":"in_ID","languageStringMap":{"$ref":"$.fromCountries.allLanguageString"}}}]}


    @Test
    public void categoryJSON(){
        QueryCategoryDTO queryCategoryDTO2 = QueryCategoryDTO.builder()
                .categoryId(1L)
                .outerCategoryId("2")
                .categoryName(MultiLanguageString.of(Maps.newHashMap()))
                .categoryCode("222")
                .status(1)
                .parentId(1L)
                .categories(Lists.newArrayList())
                .imageUrl("sssss")
                .version(12L)
                .createTime(1321L)
                .updateTime(123123L)
                .creatorId(12L)
                .modifiedId(234L)
                .level(2)
                .build();

        QueryCategoryDTO queryCategoryDTO = QueryCategoryDTO.builder()
                .categoryId(1L)
                .outerCategoryId("2")
                .categoryName(MultiLanguageString.of(Maps.newHashMap()))
                .categoryCode("222")
                .status(1)
                .parentId(1L)
                .categories(Lists.newArrayList())
                .imageUrl("sssss")
                .version(12L)
                .createTime(1321L)
                .updateTime(123123L)
                .creatorId(12L)
                .modifiedId(234L)
                .level(2)
                .build();
        String s = JSON.toJSONString(queryCategoryDTO);
        System.out.println(s);

    }



    @Test
    public void categoryjson(){
        Category category = Category.builder()
                 .categoryId(1L)
                .outerCategoryId("2")
                .categoryName(MultiLanguageString.of(Maps.newHashMap()))
                .categoryCode("222")
                .status(1)
                .parentId(1L)
                .imageUrl("sssss")
                .version(12L)
                .createTime(1321L)
                .updateTime(123123L)
                .creatorId(12L)
                .modifiedId(234L)
                .level(2)
                .extendMap(Maps.newHashMap())
                .build();
        HashMap<Long, List<Category>> map = Maps.newHashMap();
        map.put(1L, Collections.singletonList(category));
        String s = JSON.toJSONString(map);
        System.out.println(s);


    }


    @Test
    public void tree(){
        LinkedList<RootCategory> list = Lists.newLinkedList();
        Category category2 = Category.builder()
                .categoryId(1L)
                .outerCategoryId("2")
                .categoryName(MultiLanguageString.of(Maps.newHashMap()))
                .categoryCode("222")
                .status(1)
                .parentId(1L)
                .imageUrl("sssss")
                .version(12L)
                .categoryList(Lists.newArrayList())
                .createTime(1321L)
                .updateTime(123123L)
                .creatorId(12L)
                .modifiedId(234L)
                .level(2)
                .extendMap(Maps.newHashMap())
                .build();
        for (int i = 0; i < 5; i++) {
            Category category = Category.builder()
                    .categoryId(1L)
                    .outerCategoryId("2")
                    .categoryName(MultiLanguageString.of(Maps.newHashMap()))
                    .categoryCode("222")
                    .status(1)
                    .categoryList(Collections.singletonList(category2))
                    .parentId(1L)
                    .imageUrl("sssss")
                    .version(12L)
                    .createTime(1321L)
                    .updateTime(123123L)
                    .creatorId(12L)
                    .modifiedId(234L)
                    .level(2)
                    .extendMap(Maps.newHashMap())
                    .build();
            RootCategory rootCategory = RootCategory.builder()
                    .rootId(Long.valueOf(i))
                    .categoryTree(category)
                    .build();
            list.add(rootCategory);
        }

        System.out.println(list);
        System.out.println();
        String jsonString = JSON.toJSONString(list);
        System.out.println(jsonString);


    }


    @Test
    public void analysis(){
        String ss = "[{\"rootId\":123,\"categoryTree\":{\"categoryCode\":\"222\",\"categoryId\":1,\"createTime\":1321,\"creatorId\":12,\"extendMap\":{},\"categoryName\":{\"languageStringMap\":{\"zh_CN\":\"xx\",\"ja_JP\":\"xx\",\"in_ID\":\"xx\",\"en_US\":\"xx\"},\"defaultLocale\":\"in_ID\",\"allLanguageString\":{\"zh_CN\":\"xx\",\"ja_JP\":\"xx\",\"in_ID\":\"xx\",\"en_US\":\"xx\"},\"defaultLanguageString\":\"xx\"},\"categoryList\":[{\"categoryCode\":\"222\",\"categoryId\":1,\"createTime\":1321,\"creatorId\":12,\"extendMap\":{},\"categoryName\":{},\"categoryList\":[],\"imageUrl\":\"sssss\",\"level\":2,\"modifiedId\":234,\"outerCategoryId\":\"2\",\"parentId\":1,\"status\":1,\"updateTime\":123123,\"version\":12}],\"imageUrl\":\"sssss\",\"level\":2,\"modifiedId\":234,\"outerCategoryId\":\"2\",\"parentId\":1,\"status\":1,\"updateTime\":123123,\"version\":12}},{\"rootId\":123,\"categoryTree\":{\"categoryCode\":\"222\",\"categoryId\":1,\"createTime\":1321,\"creatorId\":12,\"extendMap\":{},\"categoryName\":{},\"categoryList\":[],\"imageUrl\":\"sssss\",\"level\":2,\"modifiedId\":234,\"outerCategoryId\":\"2\",\"parentId\":1,\"status\":1,\"updateTime\":123123,\"version\":12}}]";
        Object parse = JSON.parse(ss);

        JSONArray jsonArray = JSONArray.parseArray(ss);
        System.out.println(jsonArray);

//        Category category = JSON.parseObject(parse.toString(), new TypeReference<Category>() {});
//        System.out.println(category);
    }

    /**
     * 1. byte[] 转 string 转 jsonString
     * 2. byte[] 转 string 转 jsonArray
     */
    @Test
    public void da(){
        Dog dog = new Dog();
        dog.setId(1);
        dog.setState(StateEnum.OPEN);
        Dog dog2 = new Dog();
        dog2.setId(1);
        dog2.setState(StateEnum.OPEN);
        List<Dog> list = Lists.newArrayList(dog,dog2);

        String toJSONString = JSON.toJSONString(list);
        System.out.println(toJSONString);//[{"id":1,"state":"OPEN"},{"id":1,"state":"OPEN"}]

        byte[] bytes = JSON.toJSONString(list).getBytes();
        System.out.println(Arrays.toString(bytes));//[91, 123, 34, 105, 100, 34, 58, 49, 44, 34, 115, 116, 97, 116, 101, 34, 58, 34, 79, 80, 69, 78, 34, 125, 44, 123, 34, 105, 100, 34, 58, 49, 44, 34, 115, 116, 97, 116, 101, 34, 58, 34, 79, 80, 69, 78, 34, 125, 93]

        String s = new String(bytes, StandardCharsets.UTF_8);
        System.out.println(s);//            [{"id":1,"state":"OPEN"},{"id":1,"state":"OPEN"}]

        String jsonString = JSON.toJSONString(s);
        System.out.println(jsonString);//   "[{\"id\":1,\"state\":\"OPEN\"},{\"id\":1,\"state\":\"OPEN\"}]"

        JSONArray jsonArray = JSONArray.parseArray(s);
        System.out.println(jsonArray);//    [{"id":1,"state":"OPEN"},{"id":1,"state":"OPEN"}]
    }


    @Test
    public void parse(){
        String ss = "{\"category\":\"123456789\",\"brand\":\"md5\",\"country\":\"md5,country\"}";
        Object json = JSON.toJSON(ss);
        System.out.println(json);

        Object toJSON = JSONObject.toJSON(ss);
        System.out.println(toJSON);

        JSONObject jsonObject = JSON.parseObject(ss);
        Object category = jsonObject.get("category");
        System.out.println(category);

        //JSONObject 转 map
        Map<String, Object> map = jsonObject.toJavaObject(Map.class);
        System.out.println("map: "+ map);


        Collection<Object> values = jsonObject.values();
        System.out.println(values);


    }

    @Test
    public void parseMap(){
        String ss = "{\"category\":\"123456789\",\"brand\":\"md5\",\"country\":\"md5,country\"}";
        JSONObject jsonObject = JSON.parseObject(ss);
        Map<String, Object> map = JSONObject.parseObject(ss, new TypeReference<Map<String, Object>>() {});
        System.out.println(map);
        map.forEach((k,v)->{
            System.out.println(k+" <----> " + v);
        });
    }






}
