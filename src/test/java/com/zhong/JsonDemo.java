package com.zhong;

import cn.hutool.core.util.ZipUtil;
import com.alibaba.fastjson.*;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhong.cache.AreaModel;
import com.zhong.cache.CurrencyModel;
import com.zhong.cache.UnitModel;
import com.zhong.entity.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.web.bind.annotation.ValueConstants;

import java.io.*;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author zhong.zihan@xyb2b.com
 * @date 2021/11/26 17:50
 */
public class JsonDemo {

    @Test
    public void Test3() {
        List<JSONObject> list = Lists.newArrayList(
                new JSONObject().fluentPut("category_backend_id", 111).fluentPut("category", "[{\"value\": \"母婴ttt\", \"locale\": \"zh_CN\"}]"),
                new JSONObject().fluentPut("category_backend_id", 222).fluentPut("category", "[{\"value\": \"DHA\", \"locale\": \"zh_CN\"}]"),
                new JSONObject().fluentPut("category_backend_id", 333).fluentPut("category", "[{\"value\": \"钙铁锌\", \"locale\": \"zh_CN\"}]")
        );
        Map<String, JSONArray> categoryLanMap = list.stream().collect(Collectors.toMap(jsonObject -> jsonObject.getString("category_backend_id"),
                jsonObject -> jsonObject.getJSONArray("category"), (s, e) -> s));

        //{111=[{"locale":"zh_CN","value":"母婴ttt"}], 222=[{"locale":"zh_CN","value":"DHA"}], 333=[{"locale":"zh_CN","value":"钙铁锌"}]}
        System.out.println(categoryLanMap);
    }

    /**
     * Optional包装了一层，不能直接用
     */
    @Test
    public void one() {
        List<JSONObject> skuJsonObjectList = Lists.newArrayList(
                new JSONObject()
                        .fluentPut("minPrice", Optional.of(7))
                        .fluentPut("hh", "ss"),
                new JSONObject().fluentPut("minPrice", Optional.of(6)),
                new JSONObject()
        );

        BigDecimal minPrice = skuJsonObjectList.stream()
                .map(jsonObject -> {
                    return jsonObject.getBigDecimal("minPrice");
                })
                .peek(System.out::println)
                .filter(Objects::nonNull)
                .min(Comparator.comparing(Function.identity()))
                .orElse(new BigDecimal(0));
        System.out.println(minPrice);
    }

    @Test
    public void jsonObject() {
        SingleLanguageDTO singleLanguageDTO = new SingleLanguageDTO();
        singleLanguageDTO.setValue("zz");
        singleLanguageDTO.setLocale(Locale.US);
        String jsonString = JSONObject.toJSONString(singleLanguageDTO);
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        System.out.println(jsonObject);
    }

    @Test
    public void arr() {
        String ss = "[{\"value\": \"新手\", \"locale\": \"zh_CN\"}, {\"value\": \"新手\", \"locale\": \"en_US\"}]";
        List<SingleLanguageDTO> singleLanguageDTOList = JSON.parseArray(ss, SingleLanguageDTO.class);
        String string = JSON.toJSONString(singleLanguageDTOList);
        JSONArray jsonArray = JSONArray.parseArray(string);
        System.out.println(Arrays.toString(jsonArray.toArray()));

        //[{"locale":"zh_CN","value":"新手"},{"locale":"en_US","value":"新手"}]
        //[{"locale":"zh_CN","value":"新手"},{"locale":"en_US","value":"新手"}]
    }

    @Test
    public void convert2() {
        String ss = "[{\"locale\":\"en_US\",\"propertyExtend\":\"[{\\\"key\\\":{\\\"propertyId\\\":231704},\\\"values\\\":[{\\\"relationId\\\":1231},{\\\"relationId\\\":4566}]}]\"},{\"locale\":\"zh_CN\",\"propertyExtend\":\"[{\\\"key\\\":{\\\"propertyId\\\":231704},\\\"values\\\":[{\\\"relationId\\\":99461},{\\\"relationId\\\":99561}]}]\"},{\"locale\":\"ja_JP\",\"propertyExtend\":\"[{\\\"key\\\":{\\\"propertyId\\\":231704},\\\"values\\\":[{\\\"relationId\\\":99461},{\\\"relationId\\\":99561}]}]\"},{\"locale\":\"in_ID\",\"propertyExtend\":\"[{\\\"key\\\":{\\\"propertyId\\\":231704},\\\"values\\\":[{\\\"relationId\\\":99461},{\\\"relationId\\\":99561}]}]\"}]";
        JSONObject jsonObject = new JSONObject().fluentPut("property", ss);
        List<JSONObject> list = Lists.newArrayList(jsonObject);
        Set<Long> collect = list.stream().map(property -> {
            JSONArray propertyArr = property.getJSONArray("property");
            return propertyArr.stream().map(o -> {
                JSONObject object = (JSONObject) o;
                String propertyExtend = object.getString("propertyExtend");
                List<ItemPropertyBO> itemPropertyBOS = JSON.parseArray(propertyExtend, ItemPropertyBO.class);
                //System.out.println("itemPropertyBOS==" + itemPropertyBOS);
                return itemPropertyBOS;
            }).flatMap(Collection::stream).map(ItemPropertyBO::getKey).map(PropertyKeyBO::getPropertyId).collect(Collectors.toSet());
        }).flatMap(Collection::stream).collect(Collectors.toSet());
        //System.out.println(collect);

        Set<Long> values = list.stream().map(property -> {
            JSONArray propertyArr = property.getJSONArray("property");
            return propertyArr.stream().map(o -> {
                        JSONObject object = (JSONObject) o;
                        String propertyExtend = object.getString("propertyExtend");
                        List<ItemPropertyBO> itemPropertyBOS = JSON.parseArray(propertyExtend, ItemPropertyBO.class);
                        return itemPropertyBOS;
                    }).flatMap(Collection::stream).map(ItemPropertyBO::getValues)
                    .flatMap(Collection::stream).map(PropertyValuesBO::getRelationId).collect(Collectors.toSet());
        }).flatMap(Collection::stream).collect(Collectors.toSet());
        System.out.println("values==" + values);
    }


    @Test
    public void convert() {
        String ss = "[{\"locale\":\"zh_CN\",\"propertyExtend\":null,\"salePropertyExtend\":\"[{\\\"key\\\":{\\\"propertyId\\\":2000000003},\\\"value\\\":{\\\"relationId\\\":99561}}]\"},{\"locale\":\"ja_JP\",\"propertyExtend\":null,\"salePropertyExtend\":\"[{\\\"key\\\":{\\\"propertyId\\\":2000000003},\\\"value\\\":{\\\"custom\\\":\\\"Type 1\\\"}}]\"},{\"locale\":\"in_ID\",\"propertyExtend\":null,\"salePropertyExtend\":\"[{\\\"key\\\":{\\\"propertyId\\\":2000000003},\\\"value\\\":{\\\"custom\\\":\\\"Type 1\\\"}}]\"},{\"locale\":\"en_US\",\"propertyExtend\":null,\"salePropertyExtend\":\"[{\\\"key\\\":{\\\"propertyId\\\":2000000003},\\\"value\\\":{\\\"custom\\\":\\\"Type 1\\\"}}]\"}]";
        JSONObject jsonObject = new JSONObject().fluentPut("property", ss);
        List<JSONObject> list = Lists.newArrayList(jsonObject);

        Set<Long> collect = list.stream().map(property -> {
            JSONArray propertyArr = property.getJSONArray("property");
            return propertyArr.stream().map(o -> {
                        JSONObject object = (JSONObject) o;
                        String salePropertyExtendStr = object.getString("salePropertyExtend");
                        //System.out.println("salePropertyExtendStr==" + salePropertyExtendStr);
                        List<SkuPropertyBO> skuPropertyBOS = JSON.parseArray(salePropertyExtendStr, SkuPropertyBO.class);
                        //System.out.println(skuPropertyBOS);
                        return skuPropertyBOS;
                    }).flatMap(Collection::stream).map(SkuPropertyBO::getKey)
                    //.peek(System.out::println)
                    .filter(Objects::nonNull)
                    .map(PropertyKeyBO::getPropertyId)
                    //.peek(System.out::println)
                    .filter(Objects::nonNull).collect(Collectors.toSet());
        }).flatMap(Collection::stream).collect(Collectors.toSet());
        //System.out.println(collect);

        Set<Long> valueIds = list.stream().map(property -> {
            JSONArray propertyArr = property.getJSONArray("property");
            return propertyArr.stream().map(o -> {
                        JSONObject object = (JSONObject) o;
                        String salePropertyExtendStr = object.getString("salePropertyExtend");
                        List<SkuPropertyBO> skuPropertyBOS = JSON.parseArray(salePropertyExtendStr, SkuPropertyBO.class);
                        return skuPropertyBOS;
                    }).flatMap(Collection::stream).map(SkuPropertyBO::getValue).filter(Objects::nonNull)
                    .map(PropertyValuesBO::getRelationId).filter(Objects::nonNull).collect(Collectors.toSet());
        }).flatMap(Collection::stream).collect(Collectors.toSet());
        //System.out.println("valueIds==" + valueIds);

        Set<Long> values = list.stream().map(property -> {
            JSONArray propertyArr = property.getJSONArray("property");
            return propertyArr.stream().map(o -> {
                        JSONObject object = (JSONObject) o;
                        String propertyExtend = object.getString("propertyExtend");
                        if (StringUtils.isBlank(propertyExtend)) {
                            return new ArrayList<ItemPropertyBO>();
                        }
                        List<ItemPropertyBO> itemPropertyBOS = JSON.parseArray(propertyExtend, ItemPropertyBO.class);
                        return itemPropertyBOS;
                    }).flatMap(Collection::stream)
                    .map(ItemPropertyBO::getValues).peek(System.out::println)
                    .flatMap(Collection::stream).peek(System.out::println)
                    .map(PropertyValuesBO::getRelationId).peek(System.out::println)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
        }).flatMap(Collection::stream).collect(Collectors.toSet());
        System.out.println("values==" + values);
    }

    @Test
    public void jsonArr() {
        String ss = "[{\"salePropertyExtend\":\"[{\\\"key\\\":{\\\"custom\\\":\\\"Spesifikasi\\\",\\\"propertyId\\\":48201},\\\"value\\\":{\\\"custom\\\":\\\"40 pon\\\\t\\\",\\\"relationId\\\":5862}},{\\\"key\\\":{\\\"custom\\\":\\\"model\\\",\\\"propertyId\\\":49301},\\\"value\\\":{\\\"custom\\\":\\\"For iPhone 13mini \\\",\\\"relationId\\\":6964}}]\"}]";
        JSONArray array = JSONObject.parseArray(ss);
        JSONArray jsonArray = array.getJSONArray(Integer.parseInt("salePropertyExtend"));
        Map<Long, Object> objectMap = jsonArray.stream().collect(Collectors.toMap(obj -> {
            JSONObject jsonObject = (JSONObject) obj;
            Long propertyId = jsonObject.getLong("propertyId");
            if (Objects.nonNull(propertyId)) {
                return propertyId;
            }
            return null;
        }, Function.identity(), (s, e) -> s));

        System.out.println(objectMap);
    }

    /**
     * 带斜杠的str 可以转成 json
     */
    @Test
    public void strToJson() {
        String ss = "[{\"locale\": \"zh_CN\", \"propertyExtend\": null, \"salePropertyExtend\": \"[{\\\"key\\\":{\\\"propertyId\\\":2000000003},\\\"value\\\":{\\\"custom\\\":\\\"plug\\\"}}]\"}, {\"locale\": \"ja_JP\", \"propertyExtend\": null, \"salePropertyExtend\": \"[{\\\"key\\\":{\\\"propertyId\\\":2000000003},\\\"value\\\":{\\\"custom\\\":\\\"plug\\\"}}]\"}, {\"locale\": \"in_ID\", \"propertyExtend\": null, \"salePropertyExtend\": \"[{\\\"key\\\":{\\\"propertyId\\\":2000000003},\\\"value\\\":{\\\"custom\\\":\\\"plug\\\"}}]\"}, {\"locale\": \"en_US\", \"propertyExtend\": null, \"salePropertyExtend\": \"[{\\\"key\\\":{\\\"propertyId\\\":2000000003},\\\"value\\\":{\\\"custom\\\":\\\"plug\\\"}}]\"}]";
        JSONArray jsonArray = JSON.parseArray(ss);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        System.out.println(jsonObject);
        String jsonObjectString = jsonObject.getString("salePropertyExtend");
        System.out.println(jsonObjectString);
        JSONArray objects = JSONObject.parseArray(jsonObjectString);
        System.out.println(objects);
    }

    @Test
    public void toStringV2() {
        String ss = "{\"1\":[{\"age\":11,\"id\":11}]}";
        Map<String, List<User>> map = new HashMap<String, List<User>>() {{
            put("1", Collections.singletonList(new User(11, 11)));
        }};

        String string = JSON.toJSONString(map);//{"1":[{"age":11,"id":11}]}
        System.out.println(string);


//        output_tax_rate -> [{"countryId":1,"taxRate":0.22}]

        Map<String, String> mapV2 = new HashMap<String, String>() {{
            put("output_tax_rate", "[{\"countryId\":1,\"taxRate\":0.22}]");
        }};
        String jsonString = JSON.toJSONString(mapV2);
        System.out.println(jsonString);

    }

    /**
     * SimplePropertyPreFilter
     * 序列化排除/包含某些字段
     */
    @Test
    public void jsonString() {
        HashMap<String, String> hashMap = new HashMap<String, String>() {{
            put("aa", "11");
            put("bb", "55");
            put("vv", "22");
            put("cc", "33");
        }};

        SimplePropertyPreFilter simplePropertyPreFilter = new SimplePropertyPreFilter();
        simplePropertyPreFilter.getIncludes().add("tt");
//        simplePropertyPreFilter.getExcludes().add("rr");
        String string = JSON.toJSONString(hashMap, simplePropertyPreFilter);
        System.out.println(string);
    }

    /**
     * json arr ，直接list
     */
    @Test
    public void jsonList() {
        ArrayList<User> integers = Lists.newArrayList(new User(1, 2));
        String string = JSON.toJSONString(integers);
        System.out.println(string);
    }


    @Test
    public void jsonString2() {
        HashMap<String, String> hashMap = new HashMap<String, String>() {{
            put("aa", "11");
            put("bb", "55");
            put("vv", "22");
            put("cc", "33");
        }};

        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        filter.getIncludes().add("aa");
        String s = toJsonStr(hashMap, null);
        System.out.println(s);
    }


    public static String toJsonStr(Map<String, String> featureMap, PropertyPreFilter filter) {
        if (MapUtils.isNotEmpty(featureMap)) {
            return JSON.toJSONString(featureMap, filter);
        }
        return null;
    }

    @Test
    public void te() {


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
    public void categoryJSON() {
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
    public void categoryjson() {
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
    public void tree() {
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
    public void analysis() {
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
    public void da() {
        Dog dog = new Dog();
        dog.setId(1);
        dog.setState(StateEnum.OPEN);
        Dog dog2 = new Dog();
        dog2.setId(1);
        dog2.setState(StateEnum.OPEN);
        List<Dog> list = Lists.newArrayList(dog, dog2);

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
    public void parse() {
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
        System.out.println("map: " + map);


        Collection<Object> values = jsonObject.values();
        System.out.println(values);


    }

    @Test
    public void parseMap() {
        String ss = "{\"category\":\"123456789\",\"brand\":\"md5\",\"country\":\"md5,country\"}";
        JSONObject jsonObject = JSON.parseObject(ss);
        Map<String, Object> map = JSONObject.parseObject(ss, new TypeReference<Map<String, Object>>() {
        });
        System.out.println(map);
        map.forEach((k, v) -> {
            System.out.println(k + " <----> " + v);
        });
    }


    @Test
    public void parseEntity() {
        String ss = "[{\"countryCode\":\"101\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"阿富汗\",\"en_US\":\"Afghanistan\"},\"defaultLanguageString\":\"Afghanistan\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[0].countryName.allLanguageString\"}}},{\"countryCode\":\"102\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"巴林\",\"en_US\":\"Bahrian\"},\"defaultLanguageString\":\"Bahrian\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[1].countryName.allLanguageString\"}}},{\"countryCode\":\"103\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"孟加拉国\",\"en_US\":\"Bangladesh\"},\"defaultLanguageString\":\"Bangladesh\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[2].countryName.allLanguageString\"}}},{\"countryCode\":\"104\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"不丹\",\"en_US\":\"Bhutan\"},\"defaultLanguageString\":\"Bhutan\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[3].countryName.allLanguageString\"}}},{\"countryCode\":\"105\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"文莱\",\"en_US\":\"Brunei\"},\"defaultLanguageString\":\"Brunei\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[4].countryName.allLanguageString\"}}},{\"countryCode\":\"106\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"缅甸\",\"en_US\":\"Myanmar\"},\"defaultLanguageString\":\"Myanmar\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[5].countryName.allLanguageString\"}}},{\"countryCode\":\"107\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"柬埔寨\",\"en_US\":\"Cambodia\"},\"defaultLanguageString\":\"Cambodia\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[6].countryName.allLanguageString\"}}},{\"countryCode\":\"108\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"塞浦路斯\",\"en_US\":\"Cyprus\"},\"defaultLanguageString\":\"Cyprus\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[7].countryName.allLanguageString\"}}},{\"countryCode\":\"109\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"朝鲜\",\"en_US\":\"Korea,DPR\"},\"defaultLanguageString\":\"Korea,DPR\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[8].countryName.allLanguageString\"}}},{\"countryCode\":\"110\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"香港\",\"en_US\":\"Hong Kong\"},\"defaultLanguageString\":\"Hong Kong\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[9].countryName.allLanguageString\"}}},{\"countryCode\":\"111\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"印度\",\"en_US\":\"India\"},\"defaultLanguageString\":\"India\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[10].countryName.allLanguageString\"}}},{\"countryCode\":\"112\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"印度尼西亚\",\"en_US\":\"Indonesia\"},\"defaultLanguageString\":\"Indonesia\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[11].countryName.allLanguageString\"}}},{\"countryCode\":\"113\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"伊朗\",\"en_US\":\"Iran\"},\"defaultLanguageString\":\"Iran\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[12].countryName.allLanguageString\"}}},{\"countryCode\":\"114\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"伊拉克\",\"en_US\":\"Iraq\"},\"defaultLanguageString\":\"Iraq\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[13].countryName.allLanguageString\"}}},{\"countryCode\":\"115\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"以色列\",\"en_US\":\"Israel\"},\"defaultLanguageString\":\"Israel\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[14].countryName.allLanguageString\"}}},{\"countryCode\":\"116\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"日本\",\"en_US\":\"Japan\"},\"defaultLanguageString\":\"Japan\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[15].countryName.allLanguageString\"}}},{\"countryCode\":\"117\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"约旦\",\"en_US\":\"Jordan\"},\"defaultLanguageString\":\"Jordan\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[16].countryName.allLanguageString\"}}},{\"countryCode\":\"118\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"科威特\",\"en_US\":\"Kuwait\"},\"defaultLanguageString\":\"Kuwait\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[17].countryName.allLanguageString\"}}},{\"countryCode\":\"119\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"老挝\",\"en_US\":\"Lao PDR\"},\"defaultLanguageString\":\"Lao PDR\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[18].countryName.allLanguageString\"}}},{\"countryCode\":\"120\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"黎巴嫩\",\"en_US\":\"Lebanon\"},\"defaultLanguageString\":\"Lebanon\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[19].countryName.allLanguageString\"}}},{\"countryCode\":\"121\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"澳门\",\"en_US\":\"Macau\"},\"defaultLanguageString\":\"Macau\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[20].countryName.allLanguageString\"}}},{\"countryCode\":\"122\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"马来西亚\",\"en_US\":\"Malaysia\"},\"defaultLanguageString\":\"Malaysia\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[21].countryName.allLanguageString\"}}},{\"countryCode\":\"123\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"马尔代夫\",\"en_US\":\"Maldives\"},\"defaultLanguageString\":\"Maldives\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[22].countryName.allLanguageString\"}}},{\"countryCode\":\"124\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"蒙古\",\"en_US\":\"Mongolia\"},\"defaultLanguageString\":\"Mongolia\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[23].countryName.allLanguageString\"}}},{\"countryCode\":\"125\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"尼泊尔联邦民主共和国\",\"en_US\":\"Nepal, FDR\"},\"defaultLanguageString\":\"Nepal, FDR\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[24].countryName.allLanguageString\"}}},{\"countryCode\":\"126\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"阿曼\",\"en_US\":\"Oman\"},\"defaultLanguageString\":\"Oman\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[25].countryName.allLanguageString\"}}},{\"countryCode\":\"127\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"巴基斯坦\",\"en_US\":\"Pakistan\"},\"defaultLanguageString\":\"Pakistan\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[26].countryName.allLanguageString\"}}},{\"countryCode\":\"128\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"巴勒斯坦\",\"en_US\":\"Palestine\"},\"defaultLanguageString\":\"Palestine\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[27].countryName.allLanguageString\"}}},{\"countryCode\":\"129\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"菲律宾\",\"en_US\":\"Philippines\"},\"defaultLanguageString\":\"Philippines\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[28].countryName.allLanguageString\"}}},{\"countryCode\":\"130\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"卡塔尔\",\"en_US\":\"Qatar\"},\"defaultLanguageString\":\"Qatar\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[29].countryName.allLanguageString\"}}},{\"countryCode\":\"131\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"沙特阿拉伯\",\"en_US\":\"Saudi Arabia\"},\"defaultLanguageString\":\"Saudi Arabia\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[30].countryName.allLanguageString\"}}},{\"countryCode\":\"132\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"新加坡\",\"en_US\":\"Singapore\"},\"defaultLanguageString\":\"Singapore\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[31].countryName.allLanguageString\"}}},{\"countryCode\":\"133\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"韩国\",\"en_US\":\"Korea, Rep.\"},\"defaultLanguageString\":\"Korea, Rep.\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[32].countryName.allLanguageString\"}}},{\"countryCode\":\"134\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"斯里兰卡\",\"en_US\":\"Sri Lanka\"},\"defaultLanguageString\":\"Sri Lanka\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[33].countryName.allLanguageString\"}}},{\"countryCode\":\"135\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"叙利亚\",\"en_US\":\"Syrian Arab Republic\"},\"defaultLanguageString\":\"Syrian Arab Republic\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[34].countryName.allLanguageString\"}}},{\"countryCode\":\"136\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"泰国\",\"en_US\":\"Thailand\"},\"defaultLanguageString\":\"Thailand\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[35].countryName.allLanguageString\"}}},{\"countryCode\":\"137\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"土耳其\",\"en_US\":\"Turkey\"},\"defaultLanguageString\":\"Turkey\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[36].countryName.allLanguageString\"}}},{\"countryCode\":\"138\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"阿联酋\",\"en_US\":\"United Arab Emirates\"},\"defaultLanguageString\":\"United Arab Emirates\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[37].countryName.allLanguageString\"}}},{\"countryCode\":\"139\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"也门\",\"en_US\":\"Yemen\"},\"defaultLanguageString\":\"Yemen\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[38].countryName.allLanguageString\"}}},{\"countryCode\":\"141\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"越南\",\"en_US\":\"Viet Nam\"},\"defaultLanguageString\":\"Viet Nam\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[39].countryName.allLanguageString\"}}},{\"countryCode\":\"142\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"中国\",\"en_US\":\"China\"},\"defaultLanguageString\":\"China\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[40].countryName.allLanguageString\"}}},{\"countryCode\":\"143\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"台澎金马关税区\",\"en_US\":\"Taiwan, Prov.of China\"},\"defaultLanguageString\":\"Taiwan, Prov.of China\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[41].countryName.allLanguageString\"}}},{\"countryCode\":\"144\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"东帝汶\",\"en_US\":\"Timor-Leste\"},\"defaultLanguageString\":\"Timor-Leste\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[42].countryName.allLanguageString\"}}},{\"countryCode\":\"145\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"哈萨克斯坦\",\"en_US\":\"Kazakhstan\"},\"defaultLanguageString\":\"Kazakhstan\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[43].countryName.allLanguageString\"}}},{\"countryCode\":\"146\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"吉尔吉斯斯坦\",\"en_US\":\"Kyrgyzstan\"},\"defaultLanguageString\":\"Kyrgyzstan\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[44].countryName.allLanguageString\"}}},{\"countryCode\":\"147\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"塔吉克斯坦\",\"en_US\":\"Tajikistan\"},\"defaultLanguageString\":\"Tajikistan\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[45].countryName.allLanguageString\"}}},{\"countryCode\":\"148\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"土库曼斯坦\",\"en_US\":\"Turkmenistan\"},\"defaultLanguageString\":\"Turkmenistan\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[46].countryName.allLanguageString\"}}},{\"countryCode\":\"149\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"乌兹别克斯坦\",\"en_US\":\"Uzbekistan\"},\"defaultLanguageString\":\"Uzbekistan\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[47].countryName.allLanguageString\"}}},{\"countryCode\":\"199\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"亚洲其他国家(地区)\",\"en_US\":\"Oth. Asia nes\"},\"defaultLanguageString\":\"Oth. Asia nes\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[48].countryName.allLanguageString\"}}},{\"countryCode\":\"201\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"阿尔及利亚\",\"en_US\":\"Algeria\"},\"defaultLanguageString\":\"Algeria\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[49].countryName.allLanguageString\"}}},{\"countryCode\":\"202\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"安哥拉\",\"en_US\":\"Angola\"},\"defaultLanguageString\":\"Angola\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[50].countryName.allLanguageString\"}}},{\"countryCode\":\"203\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"贝宁\",\"en_US\":\"Benin\"},\"defaultLanguageString\":\"Benin\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[51].countryName.allLanguageString\"}}},{\"countryCode\":\"204\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"博茨瓦那\",\"en_US\":\"Botswana\"},\"defaultLanguageString\":\"Botswana\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[52].countryName.allLanguageString\"}}},{\"countryCode\":\"205\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"布隆迪\",\"en_US\":\"Burundi\"},\"defaultLanguageString\":\"Burundi\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[53].countryName.allLanguageString\"}}},{\"countryCode\":\"206\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"喀麦隆\",\"en_US\":\"Cameroon\"},\"defaultLanguageString\":\"Cameroon\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[54].countryName.allLanguageString\"}}},{\"countryCode\":\"207\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"加那利群岛\",\"en_US\":\"Canary Islands\"},\"defaultLanguageString\":\"Canary Islands\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[55].countryName.allLanguageString\"}}},{\"countryCode\":\"208\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"佛得角\",\"en_US\":\"Cape Verde\"},\"defaultLanguageString\":\"Cape Verde\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[56].countryName.allLanguageString\"}}},{\"countryCode\":\"209\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"中非\",\"en_US\":\"Central African Republic.\"},\"defaultLanguageString\":\"Central African Republic.\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[57].countryName.allLanguageString\"}}},{\"countryCode\":\"210\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"塞卜泰(休达)\",\"en_US\":\"Ceuta\"},\"defaultLanguageString\":\"Ceuta\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[58].countryName.allLanguageString\"}}},{\"countryCode\":\"211\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"乍得\",\"en_US\":\"Chad\"},\"defaultLanguageString\":\"Chad\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[59].countryName.allLanguageString\"}}},{\"countryCode\":\"212\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"科摩罗\",\"en_US\":\"Comoros\"},\"defaultLanguageString\":\"Comoros\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[60].countryName.allLanguageString\"}}},{\"countryCode\":\"213\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"刚果(布)\",\"en_US\":\"Congo\"},\"defaultLanguageString\":\"Congo\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[61].countryName.allLanguageString\"}}},{\"countryCode\":\"214\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"吉布提\",\"en_US\":\"Djibouti\"},\"defaultLanguageString\":\"Djibouti\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[62].countryName.allLanguageString\"}}},{\"countryCode\":\"215\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"埃及\",\"en_US\":\"Egypt\"},\"defaultLanguageString\":\"Egypt\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[63].countryName.allLanguageString\"}}},{\"countryCode\":\"216\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"赤道几内亚\",\"en_US\":\"Equatorial Guinea\"},\"defaultLanguageString\":\"Equatorial Guinea\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[64].countryName.allLanguageString\"}}},{\"countryCode\":\"217\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"埃塞俄比亚\",\"en_US\":\"Ethiopia\"},\"defaultLanguageString\":\"Ethiopia\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[65].countryName.allLanguageString\"}}},{\"countryCode\":\"218\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"加蓬\",\"en_US\":\"Gabon\"},\"defaultLanguageString\":\"Gabon\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[66].countryName.allLanguageString\"}}},{\"countryCode\":\"219\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"冈比亚\",\"en_US\":\"Gambia\"},\"defaultLanguageString\":\"Gambia\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[67].countryName.allLanguageString\"}}},{\"countryCode\":\"220\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"加纳\",\"en_US\":\"Ghana\"},\"defaultLanguageString\":\"Ghana\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[68].countryName.allLanguageString\"}}},{\"countryCode\":\"221\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"几内亚\",\"en_US\":\"Guinea\"},\"defaultLanguageString\":\"Guinea\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[69].countryName.allLanguageString\"}}},{\"countryCode\":\"222\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"几内亚比绍\",\"en_US\":\"Guinea-Bissau\"},\"defaultLanguageString\":\"Guinea-Bissau\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[70].countryName.allLanguageString\"}}},{\"countryCode\":\"223\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"科特迪瓦\",\"en_US\":\"Cote d'lvoire\"},\"defaultLanguageString\":\"Cote d'lvoire\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[71].countryName.allLanguageString\"}}},{\"countryCode\":\"224\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"肯尼亚\",\"en_US\":\"Kenya\"},\"defaultLanguageString\":\"Kenya\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[72].countryName.allLanguageString\"}}},{\"countryCode\":\"225\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"利比里亚\",\"en_US\":\"Liberia\"},\"defaultLanguageString\":\"Liberia\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[73].countryName.allLanguageString\"}}},{\"countryCode\":\"226\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"利比亚\",\"en_US\":\"Libyan Arab Jamahiriya\"},\"defaultLanguageString\":\"Libyan Arab Jamahiriya\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[74].countryName.allLanguageString\"}}},{\"countryCode\":\"227\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"马达加斯加\",\"en_US\":\"Madagascar\"},\"defaultLanguageString\":\"Madagascar\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[75].countryName.allLanguageString\"}}},{\"countryCode\":\"228\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"马拉维\",\"en_US\":\"Malawi\"},\"defaultLanguageString\":\"Malawi\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[76].countryName.allLanguageString\"}}},{\"countryCode\":\"229\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"马里\",\"en_US\":\"Mali\"},\"defaultLanguageString\":\"Mali\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[77].countryName.allLanguageString\"}}},{\"countryCode\":\"230\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"毛里塔尼亚\",\"en_US\":\"Mauritania\"},\"defaultLanguageString\":\"Mauritania\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[78].countryName.allLanguageString\"}}},{\"countryCode\":\"231\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"毛里求斯\",\"en_US\":\"Mauritius\"},\"defaultLanguageString\":\"Mauritius\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[79].countryName.allLanguageString\"}}},{\"countryCode\":\"232\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"摩洛哥\",\"en_US\":\"Morocco\"},\"defaultLanguageString\":\"Morocco\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[80].countryName.allLanguageString\"}}},{\"countryCode\":\"233\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"莫桑比克\",\"en_US\":\"Mozambique\"},\"defaultLanguageString\":\"Mozambique\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[81].countryName.allLanguageString\"}}},{\"countryCode\":\"234\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"纳米比亚\",\"en_US\":\"Namibia\"},\"defaultLanguageString\":\"Namibia\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[82].countryName.allLanguageString\"}}},{\"countryCode\":\"235\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"尼日尔\",\"en_US\":\"Niger\"},\"defaultLanguageString\":\"Niger\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[83].countryName.allLanguageString\"}}},{\"countryCode\":\"236\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"尼日利亚\",\"en_US\":\"Nigeria\"},\"defaultLanguageString\":\"Nigeria\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[84].countryName.allLanguageString\"}}},{\"countryCode\":\"237\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"留尼汪\",\"en_US\":\"Reunion\"},\"defaultLanguageString\":\"Reunion\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[85].countryName.allLanguageString\"}}},{\"countryCode\":\"238\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"卢旺达\",\"en_US\":\"Rwanda\"},\"defaultLanguageString\":\"Rwanda\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[86].countryName.allLanguageString\"}}},{\"countryCode\":\"239\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"圣多美和普林西比\",\"en_US\":\"Sao Tome and Principe\"},\"defaultLanguageString\":\"Sao Tome and Principe\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[87].countryName.allLanguageString\"}}},{\"countryCode\":\"240\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"塞内加尔\",\"en_US\":\"Senegal\"},\"defaultLanguageString\":\"Senegal\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[88].countryName.allLanguageString\"}}},{\"countryCode\":\"241\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"塞舌尔\",\"en_US\":\"Seychelles\"},\"defaultLanguageString\":\"Seychelles\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[89].countryName.allLanguageString\"}}},{\"countryCode\":\"242\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"塞拉利昂\",\"en_US\":\"Sierra Leone\"},\"defaultLanguageString\":\"Sierra Leone\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[90].countryName.allLanguageString\"}}},{\"countryCode\":\"243\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"索马里\",\"en_US\":\"Somalia\"},\"defaultLanguageString\":\"Somalia\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[91].countryName.allLanguageString\"}}},{\"countryCode\":\"244\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"南非\",\"en_US\":\"South Africa\"},\"defaultLanguageString\":\"South Africa\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[92].countryName.allLanguageString\"}}},{\"countryCode\":\"245\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"西撒哈拉\",\"en_US\":\"Western Sahara\"},\"defaultLanguageString\":\"Western Sahara\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[93].countryName.allLanguageString\"}}},{\"countryCode\":\"246\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"苏丹\",\"en_US\":\"Sudan\"},\"defaultLanguageString\":\"Sudan\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[94].countryName.allLanguageString\"}}},{\"countryCode\":\"247\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"坦桑尼亚\",\"en_US\":\"Tanzania\"},\"defaultLanguageString\":\"Tanzania\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[95].countryName.allLanguageString\"}}},{\"countryCode\":\"248\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"多哥\",\"en_US\":\"Togo\"},\"defaultLanguageString\":\"Togo\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[96].countryName.allLanguageString\"}}},{\"countryCode\":\"249\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"突尼斯\",\"en_US\":\"Tunisia\"},\"defaultLanguageString\":\"Tunisia\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[97].countryName.allLanguageString\"}}},{\"countryCode\":\"250\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"乌干达\",\"en_US\":\"Uganda\"},\"defaultLanguageString\":\"Uganda\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[98].countryName.allLanguageString\"}}},{\"countryCode\":\"251\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"布基纳法索\",\"en_US\":\"Burkina Faso\"},\"defaultLanguageString\":\"Burkina Faso\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[99].countryName.allLanguageString\"}}},{\"countryCode\":\"252\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"刚果(金)\",\"en_US\":\"Congo,DR\"},\"defaultLanguageString\":\"Congo,DR\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[100].countryName.allLanguageString\"}}},{\"countryCode\":\"253\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"赞比亚\",\"en_US\":\"Zambia\"},\"defaultLanguageString\":\"Zambia\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[101].countryName.allLanguageString\"}}},{\"countryCode\":\"254\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"津巴布韦\",\"en_US\":\"Zimbabwe\"},\"defaultLanguageString\":\"Zimbabwe\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[102].countryName.allLanguageString\"}}},{\"countryCode\":\"255\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"莱索托\",\"en_US\":\"Lesotho\"},\"defaultLanguageString\":\"Lesotho\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[103].countryName.allLanguageString\"}}},{\"countryCode\":\"256\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"梅利利亚\",\"en_US\":\"Melilla\"},\"defaultLanguageString\":\"Melilla\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[104].countryName.allLanguageString\"}}},{\"countryCode\":\"257\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"斯威士兰\",\"en_US\":\"Swaziland\"},\"defaultLanguageString\":\"Swaziland\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[105].countryName.allLanguageString\"}}},{\"countryCode\":\"258\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"厄立特里亚\",\"en_US\":\"Eritrea\"},\"defaultLanguageString\":\"Eritrea\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[106].countryName.allLanguageString\"}}},{\"countryCode\":\"259\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"马约特\",\"en_US\":\"Mayotte\"},\"defaultLanguageString\":\"Mayotte\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[107].countryName.allLanguageString\"}}},{\"countryCode\":\"299\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"非洲其他国家(地区)\",\"en_US\":\"Oth. Afr. nes\"},\"defaultLanguageString\":\"Oth. Afr. nes\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[108].countryName.allLanguageString\"}}},{\"countryCode\":\"301\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"比利时\",\"en_US\":\"Belgium\"},\"defaultLanguageString\":\"Belgium\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[109].countryName.allLanguageString\"}}},{\"countryCode\":\"302\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"丹麦\",\"en_US\":\"Denmark\"},\"defaultLanguageString\":\"Denmark\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[110].countryName.allLanguageString\"}}},{\"countryCode\":\"303\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"英国\",\"en_US\":\"United Kingdom\"},\"defaultLanguageString\":\"United Kingdom\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[111].countryName.allLanguageString\"}}},{\"countryCode\":\"304\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"德国\",\"en_US\":\"Germany\"},\"defaultLanguageString\":\"Germany\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[112].countryName.allLanguageString\"}}},{\"countryCode\":\"305\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"法国\",\"en_US\":\"France\"},\"defaultLanguageString\":\"France\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[113].countryName.allLanguageString\"}}},{\"countryCode\":\"306\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"爱尔兰\",\"en_US\":\"Ireland\"},\"defaultLanguageString\":\"Ireland\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[114].countryName.allLanguageString\"}}},{\"countryCode\":\"307\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"意大利\",\"en_US\":\"Italy\"},\"defaultLanguageString\":\"Italy\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[115].countryName.allLanguageString\"}}},{\"countryCode\":\"308\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"卢森堡\",\"en_US\":\"Luxembourg\"},\"defaultLanguageString\":\"Luxembourg\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[116].countryName.allLanguageString\"}}},{\"countryCode\":\"309\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"荷兰\",\"en_US\":\"Netherlands\"},\"defaultLanguageString\":\"Netherlands\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[117].countryName.allLanguageString\"}}},{\"countryCode\":\"310\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"希腊\",\"en_US\":\"Greece\"},\"defaultLanguageString\":\"Greece\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[118].countryName.allLanguageString\"}}},{\"countryCode\":\"311\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"葡萄牙\",\"en_US\":\"Portugal\"},\"defaultLanguageString\":\"Portugal\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[119].countryName.allLanguageString\"}}},{\"countryCode\":\"312\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"西班牙\",\"en_US\":\"Spain\"},\"defaultLanguageString\":\"Spain\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[120].countryName.allLanguageString\"}}},{\"countryCode\":\"313\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"阿尔巴尼亚\",\"en_US\":\"Albania\"},\"defaultLanguageString\":\"Albania\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[121].countryName.allLanguageString\"}}},{\"countryCode\":\"314\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"安道尔\",\"en_US\":\"Andorra\"},\"defaultLanguageString\":\"Andorra\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[122].countryName.allLanguageString\"}}},{\"countryCode\":\"315\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"奥地利\",\"en_US\":\"Austria\"},\"defaultLanguageString\":\"Austria\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[123].countryName.allLanguageString\"}}},{\"countryCode\":\"316\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"保加利亚\",\"en_US\":\"Bulgaria\"},\"defaultLanguageString\":\"Bulgaria\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[124].countryName.allLanguageString\"}}},{\"countryCode\":\"318\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"芬兰\",\"en_US\":\"Finland\"},\"defaultLanguageString\":\"Finland\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[125].countryName.allLanguageString\"}}},{\"countryCode\":\"320\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"直布罗陀\",\"en_US\":\"Gibraltar\"},\"defaultLanguageString\":\"Gibraltar\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[126].countryName.allLanguageString\"}}},{\"countryCode\":\"321\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"匈牙利\",\"en_US\":\"Hungary\"},\"defaultLanguageString\":\"Hungary\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[127].countryName.allLanguageString\"}}},{\"countryCode\":\"322\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"冰岛\",\"en_US\":\"Iceland\"},\"defaultLanguageString\":\"Iceland\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[128].countryName.allLanguageString\"}}},{\"countryCode\":\"323\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"列支敦士登\",\"en_US\":\"Liechtenstein\"},\"defaultLanguageString\":\"Liechtenstein\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[129].countryName.allLanguageString\"}}},{\"countryCode\":\"324\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"马耳他\",\"en_US\":\"Malta\"},\"defaultLanguageString\":\"Malta\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[130].countryName.allLanguageString\"}}},{\"countryCode\":\"325\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"摩纳哥\",\"en_US\":\"Monaco\"},\"defaultLanguageString\":\"Monaco\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[131].countryName.allLanguageString\"}}},{\"countryCode\":\"326\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"挪威\",\"en_US\":\"Norway\"},\"defaultLanguageString\":\"Norway\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[132].countryName.allLanguageString\"}}},{\"countryCode\":\"327\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"波兰\",\"en_US\":\"Poland\"},\"defaultLanguageString\":\"Poland\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[133].countryName.allLanguageString\"}}},{\"countryCode\":\"328\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"罗马尼亚\",\"en_US\":\"Romania\"},\"defaultLanguageString\":\"Romania\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[134].countryName.allLanguageString\"}}},{\"countryCode\":\"329\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"圣马力诺\",\"en_US\":\"San Marino\"},\"defaultLanguageString\":\"San Marino\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[135].countryName.allLanguageString\"}}},{\"countryCode\":\"330\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"瑞典\",\"en_US\":\"Sweden\"},\"defaultLanguageString\":\"Sweden\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[136].countryName.allLanguageString\"}}},{\"countryCode\":\"331\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"瑞士\",\"en_US\":\"Switzerland\"},\"defaultLanguageString\":\"Switzerland\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[137].countryName.allLanguageString\"}}},{\"countryCode\":\"334\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"爱沙尼亚\",\"en_US\":\"Estonia\"},\"defaultLanguageString\":\"Estonia\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[138].countryName.allLanguageString\"}}},{\"countryCode\":\"335\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"拉脱维亚\",\"en_US\":\"Latvia\"},\"defaultLanguageString\":\"Latvia\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[139].countryName.allLanguageString\"}}},{\"countryCode\":\"336\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"立陶宛\",\"en_US\":\"Lithuania\"},\"defaultLanguageString\":\"Lithuania\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[140].countryName.allLanguageString\"}}},{\"countryCode\":\"337\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"格鲁吉亚\",\"en_US\":\"Georgia\"},\"defaultLanguageString\":\"Georgia\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[141].countryName.allLanguageString\"}}},{\"countryCode\":\"338\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"亚美尼亚\",\"en_US\":\"Armenia\"},\"defaultLanguageString\":\"Armenia\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[142].countryName.allLanguageString\"}}},{\"countryCode\":\"339\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"阿塞拜疆\",\"en_US\":\"Azerbai jan\"},\"defaultLanguageString\":\"Azerbai jan\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[143].countryName.allLanguageString\"}}},{\"countryCode\":\"340\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"白俄罗斯\",\"en_US\":\"Belarus\"},\"defaultLanguageString\":\"Belarus\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[144].countryName.allLanguageString\"}}},{\"countryCode\":\"343\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"摩尔多瓦\",\"en_US\":\"Moldova\"},\"defaultLanguageString\":\"Moldova\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[145].countryName.allLanguageString\"}}},{\"countryCode\":\"344\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"俄罗斯联邦\",\"en_US\":\"Russian Federation\"},\"defaultLanguageString\":\"Russian Federation\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[146].countryName.allLanguageString\"}}},{\"countryCode\":\"347\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"乌克兰\",\"en_US\":\"Ukraine\"},\"defaultLanguageString\":\"Ukraine\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[147].countryName.allLanguageString\"}}},{\"countryCode\":\"349\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"塞尔维亚和黑山\",\"en_US\":\"Serbia and Montenegro\"},\"defaultLanguageString\":\"Serbia and Montenegro\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[148].countryName.allLanguageString\"}}},{\"countryCode\":\"350\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"斯洛文尼亚\",\"en_US\":\"Slovenia\"},\"defaultLanguageString\":\"Slovenia\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[149].countryName.allLanguageString\"}}},{\"countryCode\":\"351\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"克罗地亚\",\"en_US\":\"Croatia\"},\"defaultLanguageString\":\"Croatia\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[150].countryName.allLanguageString\"}}},{\"countryCode\":\"352\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"捷克\",\"en_US\":\"Czech Republic\"},\"defaultLanguageString\":\"Czech Republic\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[151].countryName.allLanguageString\"}}},{\"countryCode\":\"353\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"斯洛伐克\",\"en_US\":\"Slovakia\"},\"defaultLanguageString\":\"Slovakia\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[152].countryName.allLanguageString\"}}},{\"countryCode\":\"354\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"前南马其顿\",\"en_US\":\"Macedonia,FYR\"},\"defaultLanguageString\":\"Macedonia,FYR\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[153].countryName.allLanguageString\"}}},{\"countryCode\":\"355\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"波黑\",\"en_US\":\"Bosnia and Hercegovina\"},\"defaultLanguageString\":\"Bosnia and Hercegovina\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[154].countryName.allLanguageString\"}}},{\"countryCode\":\"356\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"梵蒂冈城国\",\"en_US\":\"Vatican City State\"},\"defaultLanguageString\":\"Vatican City State\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[155].countryName.allLanguageString\"}}},{\"countryCode\":\"357\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"法罗群岛\",\"en_US\":\"Faroe Islands\"},\"defaultLanguageString\":\"Faroe Islands\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[156].countryName.allLanguageString\"}}},{\"countryCode\":\"358\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"塞尔维亚\",\"en_US\":\"Serbia\"},\"defaultLanguageString\":\"Serbia\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[157].countryName.allLanguageString\"}}},{\"countryCode\":\"359\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"黑山\",\"en_US\":\"Montenegro\"},\"defaultLanguageString\":\"Montenegro\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[158].countryName.allLanguageString\"}}},{\"countryCode\":\"399\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"欧洲其他国家(地区)\",\"en_US\":\"Oth. Eur. nes\"},\"defaultLanguageString\":\"Oth. Eur. nes\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[159].countryName.allLanguageString\"}}},{\"countryCode\":\"401\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"安提瓜和巴布达\",\"en_US\":\"Antigua & Barbuda\"},\"defaultLanguageString\":\"Antigua & Barbuda\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[160].countryName.allLanguageString\"}}},{\"countryCode\":\"402\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"阿根廷\",\"en_US\":\"Argentina\"},\"defaultLanguageString\":\"Argentina\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[161].countryName.allLanguageString\"}}},{\"countryCode\":\"403\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"阿鲁巴\",\"en_US\":\"Aruba\"},\"defaultLanguageString\":\"Aruba\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[162].countryName.allLanguageString\"}}},{\"countryCode\":\"404\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"巴哈马\",\"en_US\":\"Bahamas\"},\"defaultLanguageString\":\"Bahamas\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[163].countryName.allLanguageString\"}}},{\"countryCode\":\"405\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"巴巴多斯\",\"en_US\":\"Barbados\"},\"defaultLanguageString\":\"Barbados\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[164].countryName.allLanguageString\"}}},{\"countryCode\":\"406\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"伯利兹\",\"en_US\":\"Belize\"},\"defaultLanguageString\":\"Belize\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[165].countryName.allLanguageString\"}}},{\"countryCode\":\"408\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"玻利维亚\",\"en_US\":\"Bolivia\"},\"defaultLanguageString\":\"Bolivia\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[166].countryName.allLanguageString\"}}},{\"countryCode\":\"409\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"博内尔\",\"en_US\":\"Bonaire\"},\"defaultLanguageString\":\"Bonaire\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[167].countryName.allLanguageString\"}}},{\"countryCode\":\"410\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"巴西\",\"en_US\":\"Brazil\"},\"defaultLanguageString\":\"Brazil\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[168].countryName.allLanguageString\"}}},{\"countryCode\":\"411\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"开曼群岛\",\"en_US\":\"Cayman Islands\"},\"defaultLanguageString\":\"Cayman Islands\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[169].countryName.allLanguageString\"}}},{\"countryCode\":\"412\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"智利\",\"en_US\":\"Chile\"},\"defaultLanguageString\":\"Chile\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[170].countryName.allLanguageString\"}}},{\"countryCode\":\"413\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"哥伦比亚\",\"en_US\":\"Colombia\"},\"defaultLanguageString\":\"Colombia\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[171].countryName.allLanguageString\"}}},{\"countryCode\":\"414\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"多米尼克\",\"en_US\":\"Dominica\"},\"defaultLanguageString\":\"Dominica\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[172].countryName.allLanguageString\"}}},{\"countryCode\":\"415\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"哥斯达黎加\",\"en_US\":\"Costa Rica\"},\"defaultLanguageString\":\"Costa Rica\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[173].countryName.allLanguageString\"}}},{\"countryCode\":\"416\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"古巴\",\"en_US\":\"Cuba\"},\"defaultLanguageString\":\"Cuba\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[174].countryName.allLanguageString\"}}},{\"countryCode\":\"417\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"库腊索岛\",\"en_US\":\"Curacao\"},\"defaultLanguageString\":\"Curacao\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[175].countryName.allLanguageString\"}}},{\"countryCode\":\"418\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"多米尼加共和国\",\"en_US\":\"Dominican Republic\"},\"defaultLanguageString\":\"Dominican Republic\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[176].countryName.allLanguageString\"}}},{\"countryCode\":\"419\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"厄瓜多尔\",\"en_US\":\"Ecuador\"},\"defaultLanguageString\":\"Ecuador\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[177].countryName.allLanguageString\"}}},{\"countryCode\":\"420\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"法属圭亚那\",\"en_US\":\"French Guiana\"},\"defaultLanguageString\":\"French Guiana\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[178].countryName.allLanguageString\"}}},{\"countryCode\":\"421\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"格林纳达\",\"en_US\":\"Grenada\"},\"defaultLanguageString\":\"Grenada\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[179].countryName.allLanguageString\"}}},{\"countryCode\":\"422\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"瓜德罗普\",\"en_US\":\"Guadeloupe\"},\"defaultLanguageString\":\"Guadeloupe\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[180].countryName.allLanguageString\"}}},{\"countryCode\":\"423\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"危地马拉\",\"en_US\":\"Guatemala\"},\"defaultLanguageString\":\"Guatemala\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[181].countryName.allLanguageString\"}}},{\"countryCode\":\"424\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"圭亚那\",\"en_US\":\"Guyana\"},\"defaultLanguageString\":\"Guyana\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[182].countryName.allLanguageString\"}}},{\"countryCode\":\"425\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"海地\",\"en_US\":\"Haiti\"},\"defaultLanguageString\":\"Haiti\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[183].countryName.allLanguageString\"}}},{\"countryCode\":\"426\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"洪都拉斯\",\"en_US\":\"Honduras\"},\"defaultLanguageString\":\"Honduras\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[184].countryName.allLanguageString\"}}},{\"countryCode\":\"427\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"牙买加\",\"en_US\":\"Jamaica\"},\"defaultLanguageString\":\"Jamaica\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[185].countryName.allLanguageString\"}}},{\"countryCode\":\"428\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"马提尼克\",\"en_US\":\"Martinique\"},\"defaultLanguageString\":\"Martinique\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[186].countryName.allLanguageString\"}}},{\"countryCode\":\"429\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"墨西哥\",\"en_US\":\"Mexico\"},\"defaultLanguageString\":\"Mexico\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[187].countryName.allLanguageString\"}}},{\"countryCode\":\"430\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"蒙特塞拉特\",\"en_US\":\"Montserrat\"},\"defaultLanguageString\":\"Montserrat\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[188].countryName.allLanguageString\"}}},{\"countryCode\":\"431\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"尼加拉瓜\",\"en_US\":\"Nicaragua\"},\"defaultLanguageString\":\"Nicaragua\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[189].countryName.allLanguageString\"}}},{\"countryCode\":\"432\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"巴拿马\",\"en_US\":\"Panama\"},\"defaultLanguageString\":\"Panama\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[190].countryName.allLanguageString\"}}},{\"countryCode\":\"433\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"巴拉圭\",\"en_US\":\"Paraguay\"},\"defaultLanguageString\":\"Paraguay\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[191].countryName.allLanguageString\"}}},{\"countryCode\":\"434\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"秘鲁\",\"en_US\":\"Peru\"},\"defaultLanguageString\":\"Peru\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[192].countryName.allLanguageString\"}}},{\"countryCode\":\"435\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"波多黎各\",\"en_US\":\"Puerto Rico\"},\"defaultLanguageString\":\"Puerto Rico\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[193].countryName.allLanguageString\"}}},{\"countryCode\":\"436\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"萨巴\",\"en_US\":\"Saba\"},\"defaultLanguageString\":\"Saba\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[194].countryName.allLanguageString\"}}},{\"countryCode\":\"437\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"圣卢西亚\",\"en_US\":\"Saint Lucia\"},\"defaultLanguageString\":\"Saint Lucia\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[195].countryName.allLanguageString\"}}},{\"countryCode\":\"438\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"圣马丁岛\",\"en_US\":\"Saint Martin Islands\"},\"defaultLanguageString\":\"Saint Martin Islands\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[196].countryName.allLanguageString\"}}},{\"countryCode\":\"439\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"圣文森特和格林纳丁斯\",\"en_US\":\"Saint Vincent and Grenadines\"},\"defaultLanguageString\":\"Saint Vincent and Grenadines\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[197].countryName.allLanguageString\"}}},{\"countryCode\":\"440\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"萨尔瓦多\",\"en_US\":\"El Salvador\"},\"defaultLanguageString\":\"El Salvador\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[198].countryName.allLanguageString\"}}},{\"countryCode\":\"441\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"苏里南\",\"en_US\":\"Suriname\"},\"defaultLanguageString\":\"Suriname\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[199].countryName.allLanguageString\"}}},{\"countryCode\":\"442\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"特立尼达和多巴哥\",\"en_US\":\"Trinidad and Tobago\"},\"defaultLanguageString\":\"Trinidad and Tobago\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[200].countryName.allLanguageString\"}}},{\"countryCode\":\"443\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"特克斯和凯科斯群岛\",\"en_US\":\"Turks and Caicos Islands\"},\"defaultLanguageString\":\"Turks and Caicos Islands\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[201].countryName.allLanguageString\"}}},{\"countryCode\":\"444\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"乌拉圭\",\"en_US\":\"Uruguay\"},\"defaultLanguageString\":\"Uruguay\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[202].countryName.allLanguageString\"}}},{\"countryCode\":\"445\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"委内瑞拉\",\"en_US\":\"Venezuela\"},\"defaultLanguageString\":\"Venezuela\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[203].countryName.allLanguageString\"}}},{\"countryCode\":\"446\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"英属维尔京群岛\",\"en_US\":\"Virgin Islands, British\"},\"defaultLanguageString\":\"Virgin Islands, British\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[204].countryName.allLanguageString\"}}},{\"countryCode\":\"447\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"圣其茨和尼维斯\",\"en_US\":\"Saint Kitts and Nevis\"},\"defaultLanguageString\":\"Saint Kitts and Nevis\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[205].countryName.allLanguageString\"}}},{\"countryCode\":\"448\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"圣皮埃尔和密克隆\",\"en_US\":\"Saint.Pierre and Miquelon\"},\"defaultLanguageString\":\"Saint.Pierre and Miquelon\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[206].countryName.allLanguageString\"}}},{\"countryCode\":\"449\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"荷属安地列斯\",\"en_US\":\"Netherlands Antilles\"},\"defaultLanguageString\":\"Netherlands Antilles\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[207].countryName.allLanguageString\"}}},{\"countryCode\":\"499\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"拉丁美洲其他国家(地区)\",\"en_US\":\"Oth. L.Amer. nes\"},\"defaultLanguageString\":\"Oth. L.Amer. nes\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[208].countryName.allLanguageString\"}}},{\"countryCode\":\"501\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"加拿大\",\"en_US\":\"Canada\"},\"defaultLanguageString\":\"Canada\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[209].countryName.allLanguageString\"}}},{\"countryCode\":\"502\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"美国\",\"en_US\":\"United States\"},\"defaultLanguageString\":\"United States\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[210].countryName.allLanguageString\"}}},{\"countryCode\":\"503\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"格陵兰\",\"en_US\":\"Greenland\"},\"defaultLanguageString\":\"Greenland\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[211].countryName.allLanguageString\"}}},{\"countryCode\":\"504\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"百慕大\",\"en_US\":\"Bermuda\"},\"defaultLanguageString\":\"Bermuda\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[212].countryName.allLanguageString\"}}},{\"countryCode\":\"599\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"北美洲其他国家(地区)\",\"en_US\":\"Oth. N.Amer. nes\"},\"defaultLanguageString\":\"Oth. N.Amer. nes\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[213].countryName.allLanguageString\"}}},{\"countryCode\":\"601\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"澳大利亚\",\"en_US\":\"Australia\"},\"defaultLanguageString\":\"Australia\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[214].countryName.allLanguageString\"}}},{\"countryCode\":\"602\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"库克群岛\",\"en_US\":\"Cook Islands\"},\"defaultLanguageString\":\"Cook Islands\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[215].countryName.allLanguageString\"}}},{\"countryCode\":\"603\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"斐济\",\"en_US\":\"Fiji\"},\"defaultLanguageString\":\"Fiji\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[216].countryName.allLanguageString\"}}},{\"countryCode\":\"604\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"盖比群岛\",\"en_US\":\"Gambier Islands\"},\"defaultLanguageString\":\"Gambier Islands\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[217].countryName.allLanguageString\"}}},{\"countryCode\":\"605\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"马克萨斯群岛\",\"en_US\":\"Marquesas Islands\"},\"defaultLanguageString\":\"Marquesas Islands\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[218].countryName.allLanguageString\"}}},{\"countryCode\":\"606\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"瑙鲁\",\"en_US\":\"Nauru\"},\"defaultLanguageString\":\"Nauru\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[219].countryName.allLanguageString\"}}},{\"countryCode\":\"607\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"新喀里多尼亚\",\"en_US\":\"New Caledonia\"},\"defaultLanguageString\":\"New Caledonia\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[220].countryName.allLanguageString\"}}},{\"countryCode\":\"608\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"瓦努阿图\",\"en_US\":\"Vanuatu\"},\"defaultLanguageString\":\"Vanuatu\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[221].countryName.allLanguageString\"}}},{\"countryCode\":\"609\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"新西兰\",\"en_US\":\"New Zealand\"},\"defaultLanguageString\":\"New Zealand\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[222].countryName.allLanguageString\"}}},{\"countryCode\":\"610\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"诺福克岛\",\"en_US\":\"Norfolk Island\"},\"defaultLanguageString\":\"Norfolk Island\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[223].countryName.allLanguageString\"}}},{\"countryCode\":\"611\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"巴布亚新几内亚\",\"en_US\":\"Papua New Guinea\"},\"defaultLanguageString\":\"Papua New Guinea\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[224].countryName.allLanguageString\"}}},{\"countryCode\":\"612\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"社会群岛\",\"en_US\":\"Society Islands\"},\"defaultLanguageString\":\"Society Islands\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[225].countryName.allLanguageString\"}}},{\"countryCode\":\"613\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"所罗门群岛\",\"en_US\":\"Solomon Islands\"},\"defaultLanguageString\":\"Solomon Islands\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[226].countryName.allLanguageString\"}}},{\"countryCode\":\"614\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"汤加\",\"en_US\":\"Tonga\"},\"defaultLanguageString\":\"Tonga\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[227].countryName.allLanguageString\"}}},{\"countryCode\":\"615\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"土阿莫土群岛\",\"en_US\":\"Tuamotu Islands\"},\"defaultLanguageString\":\"Tuamotu Islands\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[228].countryName.allLanguageString\"}}},{\"countryCode\":\"616\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"土布艾群岛\",\"en_US\":\"Tubai Islands\"},\"defaultLanguageString\":\"Tubai Islands\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[229].countryName.allLanguageString\"}}},{\"countryCode\":\"617\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"萨摩亚\",\"en_US\":\"Samoa\"},\"defaultLanguageString\":\"Samoa\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[230].countryName.allLanguageString\"}}},{\"countryCode\":\"618\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"基里巴斯\",\"en_US\":\"Kiribati\"},\"defaultLanguageString\":\"Kiribati\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[231].countryName.allLanguageString\"}}},{\"countryCode\":\"619\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"图瓦卢\",\"en_US\":\"Tuvalu\"},\"defaultLanguageString\":\"Tuvalu\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[232].countryName.allLanguageString\"}}},{\"countryCode\":\"620\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"密克罗尼西亚联邦\",\"en_US\":\"Micronesia, Fs\"},\"defaultLanguageString\":\"Micronesia, Fs\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[233].countryName.allLanguageString\"}}},{\"countryCode\":\"621\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"马绍尔群岛\",\"en_US\":\"Marshall Islands\"},\"defaultLanguageString\":\"Marshall Islands\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[234].countryName.allLanguageString\"}}},{\"countryCode\":\"622\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"帕劳\",\"en_US\":\"Palau\"},\"defaultLanguageString\":\"Palau\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[235].countryName.allLanguageString\"}}},{\"countryCode\":\"623\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"法属波利尼西亚\",\"en_US\":\"French Polynesia\"},\"defaultLanguageString\":\"French Polynesia\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[236].countryName.allLanguageString\"}}},{\"countryCode\":\"625\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"瓦利斯和浮图纳\",\"en_US\":\"Wallis and Futuna\"},\"defaultLanguageString\":\"Wallis and Futuna\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[237].countryName.allLanguageString\"}}},{\"countryCode\":\"699\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"大洋洲其他国家(地区)\",\"en_US\":\"Oth. Ocean. nes\"},\"defaultLanguageString\":\"Oth. Ocean. nes\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[238].countryName.allLanguageString\"}}},{\"countryCode\":\"701\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"国(地)别不详\",\"en_US\":\"Countries(reg.) unknown\"},\"defaultLanguageString\":\"Countries(reg.) unknown\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[239].countryName.allLanguageString\"}}},{\"countryCode\":\"702\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"联合国及机构和国际组织\",\"en_US\":\"UN and oth. int'l org.\"},\"defaultLanguageString\":\"UN and oth. int'l org.\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[240].countryName.allLanguageString\"}}},{\"countryCode\":\"999\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"中性包装原产国别\",\"en_US\":\"Conutries of Neutral Package\"},\"defaultLanguageString\":\"Conutries of Neutral Package\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[241].countryName.allLanguageString\"}}},{\"countryCode\":\"260\",\"countryName\":{\"allLanguageString\":{\"zh_CN\":\"南苏丹共和国\",\"en_US\":\"Republic of South Sudan\"},\"defaultLanguageString\":\"Republic of South Sudan\",\"defaultLocale\":\"in_ID\",\"languageStringMap\":{\"$ref\":\"$[242].countryName.allLanguageString\"}}}]\n";
        List<QueryCountryLocaleDTO> countryLocaleDTOS = JSONObject.parseObject(ss, new TypeReference<List<QueryCountryLocaleDTO>>() {
        });
        System.out.println("countryLocaleDTOS.size() = " + countryLocaleDTOS.size());
        for (QueryCountryLocaleDTO dto : countryLocaleDTOS) {
            String countryCode = dto.getCountryCode();
            MultiLanguageString countryName = dto.getCountryName();

            System.out.println("countryCode = " + countryCode);
            System.out.println("countryName = " + countryName);
        }
    }


    @Test
    public void parseArray2Obj() {
        String ss = "[{\"creatorId\":23,\"currencyAbbreviation\":\"CNY\",\"currencyCode\":\"142\",\"currencyId\":1,\"extendMap\":{},\"platform\":\"product_GLO\",\"status\":0,\"version\":0},{\"creatorId\":23,\"currencyAbbreviation\":\"HKD\",\"currencyCode\":\"110\",\"currencyId\":2,\"extendMap\":{},\"platform\":\"product_GLO\",\"status\":0,\"version\":0},{\"createTime\":1638173137238,\"creatorId\":23,\"currencyAbbreviation\":\"JPY\",\"currencyCode\":\"116\",\"currencyId\":17,\"extendMap\":{},\"modifiedId\":1,\"platform\":\"product_GLO\",\"status\":0,\"updateTime\":1638188844542,\"version\":8},{\"createTime\":1638187940210,\"creatorId\":1,\"currencyAbbreviation\":\"13364856\",\"currencyCode\":\"468496\",\"currencyId\":29,\"extendMap\":{},\"modifiedId\":1,\"platform\":\"product_GLO\",\"status\":0,\"updateTime\":1638240070907,\"version\":2},{\"createTime\":1638191274966,\"creatorId\":1,\"currencyAbbreviation\":\"THB\",\"currencyCode\":\"136\",\"currencyId\":38,\"extendMap\":{},\"modifiedId\":1,\"platform\":\"product_GLO\",\"status\":0,\"updateTime\":1638191307059,\"version\":8},{\"createTime\":1638861980600,\"creatorId\":1,\"currencyAbbreviation\":\"9\",\"currencyCode\":\"99\",\"currencyId\":132,\"extendMap\":{},\"platform\":\"product_GLO\",\"status\":0,\"updateTime\":1638861980600,\"version\":1},{\"createTime\":1638870892025,\"creatorId\":1,\"currencyAbbreviation\":\"454\",\"currencyCode\":\"454\",\"currencyId\":137,\"extendMap\":{},\"platform\":\"product_GLO\",\"status\":0,\"updateTime\":1638870892025,\"version\":1},{\"createTime\":1638871817609,\"creatorId\":1,\"currencyAbbreviation\":\"767\",\"currencyCode\":\"676\",\"currencyId\":138,\"extendMap\":{},\"platform\":\"product_GLO\",\"status\":0,\"updateTime\":1638871817609,\"version\":1},{\"createTime\":1638875219161,\"creatorId\":1,\"currencyAbbreviation\":\"8888\",\"currencyCode\":\"888\",\"currencyId\":139,\"extendMap\":{},\"platform\":\"product_GLO\",\"status\":0,\"updateTime\":1638875219161,\"version\":1},{\"createTime\":1638875413310,\"creatorId\":1,\"currencyAbbreviation\":\"0909\",\"currencyCode\":\"999\",\"currencyId\":143,\"extendMap\":{},\"platform\":\"product_GLO\",\"status\":0,\"updateTime\":1638875413310,\"version\":1},{\"createTime\":1638875467186,\"creatorId\":1,\"currencyAbbreviation\":\"777\",\"currencyCode\":\"777\",\"currencyId\":144,\"extendMap\":{},\"platform\":\"product_GLO\",\"status\":0,\"updateTime\":1638875467186,\"version\":1},{\"createTime\":1638875551785,\"creatorId\":1,\"currencyAbbreviation\":\"66666\",\"currencyCode\":\"666\",\"currencyId\":145,\"extendMap\":{},\"modifiedId\":1,\"platform\":\"product_GLO\",\"status\":0,\"updateTime\":1638875916092,\"version\":3},{\"createTime\":1638875899219,\"creatorId\":1,\"currencyAbbreviation\":\"34323232323\",\"currencyCode\":\"434323232323\",\"currencyId\":147,\"extendMap\":{},\"modifiedId\":1,\"platform\":\"product_GLO\",\"status\":0,\"updateTime\":1638876018021,\"version\":2},{\"createTime\":1638957513750,\"creatorId\":1,\"currencyAbbreviation\":\"阿富汗尼2\",\"currencyCode\":\"99090\",\"currencyId\":186,\"extendMap\":{},\"modifiedId\":1,\"platform\":\"product_GLO\",\"status\":0,\"updateTime\":1638960273275,\"version\":4},{\"createTime\":1639021850599,\"creatorId\":1,\"currencyAbbreviation\":\"USD\",\"currencyCode\":\"502\",\"currencyId\":219,\"extendMap\":{},\"platform\":\"product_GLO\",\"status\":0,\"updateTime\":1639021850599,\"version\":1},{\"createTime\":1639037786234,\"creatorId\":1,\"currencyAbbreviation\":\"EUR\",\"currencyCode\":\"300\",\"currencyId\":240,\"extendMap\":{},\"platform\":\"product_GLO\",\"status\":0,\"updateTime\":1639037786234,\"version\":1},{\"createTime\":1639041479815,\"creatorId\":1,\"currencyAbbreviation\":\"测试一下\",\"currencyCode\":\"23456\",\"currencyId\":242,\"extendMap\":{},\"platform\":\"product_GLO\",\"status\":0,\"updateTime\":1639041479815,\"version\":1},{\"createTime\":1639048791114,\"creatorId\":1,\"currencyAbbreviation\":\"23232323343\",\"currencyCode\":\"23233434\",\"currencyId\":246,\"extendMap\":{},\"platform\":\"product_GLO\",\"status\":0,\"updateTime\":1639048791114,\"version\":1},{\"createTime\":1639101529643,\"creatorId\":1,\"currencyAbbreviation\":\"23\",\"currencyCode\":\"2323\",\"currencyId\":254,\"extendMap\":{},\"platform\":\"product_GLO\",\"status\":0,\"updateTime\":1639101529643,\"version\":1},{\"createTime\":1639103163645,\"creatorId\":1,\"currencyAbbreviation\":\"5454545\",\"currencyCode\":\"46565656\",\"currencyId\":256,\"extendMap\":{},\"platform\":\"product_GLO\",\"status\":0,\"updateTime\":1639103163645,\"version\":1},{\"createTime\":1639105888253,\"creatorId\":1,\"currencyAbbreviation\":\"币种增加后刷新\",\"currencyCode\":\"2344\",\"currencyId\":257,\"extendMap\":{},\"platform\":\"product_GLO\",\"status\":0,\"updateTime\":1639105888253,\"version\":1},{\"createTime\":1639400394990,\"creatorId\":1,\"currencyAbbreviation\":\"DC\",\"currencyCode\":\"8907\",\"currencyId\":354,\"extendMap\":{},\"platform\":\"product_GLO\",\"status\":0,\"updateTime\":1639400394990,\"version\":1},{\"createTime\":1639986246600,\"creatorId\":23,\"currencyAbbreviation\":\"PO\",\"currencyCode\":\"1126\",\"currencyId\":497,\"extendMap\":{},\"modifiedId\":233,\"platform\":\"product_GLO\",\"status\":0,\"updateTime\":1639986550640,\"version\":6},{\"createTime\":1639988533566,\"creatorId\":1,\"currencyAbbreviation\":\"OOO\",\"currencyCode\":\"14665\",\"currencyId\":501,\"extendMap\":{},\"modifiedId\":1,\"platform\":\"product_GLO\",\"status\":0,\"updateTime\":1639988533566,\"version\":3},{\"createTime\":1640848792300,\"creatorId\":103338,\"currencyAbbreviation\":\"创建人\",\"currencyCode\":\"908000\",\"currencyId\":772,\"extendMap\":{},\"modifiedId\":999999999,\"platform\":\"product_GLO\",\"status\":0,\"updateTime\":1643253030928,\"version\":5}]";
        long time = System.currentTimeMillis();
        List<CurrencyModel> c = JSONArray.parseArray(ss, CurrencyModel.class);
        System.out.println("差值1： " + (System.currentTimeMillis() - time));

        long time2 = System.currentTimeMillis();
        List<CurrencyModel> v = JSONArray.parseObject(ss, new TypeReference<List<CurrencyModel>>() {
        });
        System.out.println("差值2： " + (System.currentTimeMillis() - time2));
    }


    @Test
    public void entity2Array() {
        CurrencyModel a = new CurrencyModel();
        CurrencyModel b = new CurrencyModel();
        CurrencyModel c = new CurrencyModel();
        List<CurrencyModel> list = Lists.newArrayList(a, b, c);
        Object[] array = list.toArray();
        System.out.println(Arrays.toString(array));


    }

    @Test
    public void fastjsonWrite() throws IOException {
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("sss", "aaaa");
        Map<Locale, String> map = new HashMap<>();
        map.put(Locale.US, "niao");
        map.put(Locale.UK, "niao222");
        map.put(Locale.ENGLISH, "niao3333");
        UnitModel unitModel = UnitModel.builder()
                .unitCode(111L)
                .extendMap(hashMap)
                .unitName(MultiLanguageString.of(map))
                .build();
//        String string = JSON.toJSONString(Lists.newArrayList(unitModel), SerializerFeature.DisableCircularReferenceDetect);
        String string = JSON.toJSONString(Lists.newArrayList(unitModel));
        FileWriter writer = new FileWriter("C:\\Users\\EDZ\\Documents\\meta\\fastjsonTest.json");
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        bufferedWriter.write(string);
        bufferedWriter.close();

    }

    @Test
    public void fastJsonRead() throws IOException {
        long time = System.currentTimeMillis();
        File file = new File("C:\\Users\\EDZ\\Documents\\meta\\fixJsonString.json");
        String content = FileUtils.readFileToString(file, "UTF-8");
        List<AreaModel> unitModels = JSONArray.parseObject(content, new TypeReference<List<AreaModel>>() {
        });
        System.out.println(unitModels.get(0).getAreaName().getAllLanguageString());
        System.out.println("consume time: " + (System.currentTimeMillis() - time) + "::::" + unitModels.size());
//        consume time: 1406738::::147177   23分钟

    }


    @Test
    public void fastjsonWriteZip() throws IOException {
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("sss", "aaaa");
        Map<Locale, String> map = new HashMap<>();
        map.put(Locale.US, "niao");
        map.put(Locale.UK, "niao222");
        map.put(Locale.ENGLISH, "niao3333");
        UnitModel unitModel = UnitModel.builder()
                .unitCode(111L)
                .extendMap(hashMap)
                .unitName(MultiLanguageString.of(map))
                .build();
//        String string = JSON.toJSONString(Lists.newArrayList(unitModel), SerializerFeature.DisableCircularReferenceDetect);
        String jsonString = JSON.toJSONString(Lists.newArrayList(unitModel));

        byte[] bytes = jsonString.getBytes(StandardCharsets.UTF_8);
        System.out.println(bytes.length);
        //压缩
        byte[] gzip = ZipUtil.gzip(bytes);
        System.out.println(gzip.length);
//
//        File zip = ZipUtil.zip(jsonString);
//        String zipString = FileUtils.readFileToString(zip, "UTF-8");
//
//        FileWriter writer = new FileWriter("C:\\Users\\EDZ\\Documents\\meta\\fastjsonZip.json");
//        BufferedWriter bufferedWriter = new BufferedWriter(writer);
//        bufferedWriter.write(zipString);
//        bufferedWriter.close();

    }


    @Test
    public void parse2() throws IOException {
        long time = System.currentTimeMillis();
        File file = new File("jacksonTest.json");
        String content = FileUtils.readFileToString(file, "UTF-8");

        List<UnitModel> unitModels = JSONArray.parseObject(content, new TypeReference<List<UnitModel>>() {
        });
        System.out.println(unitModels);
        System.out.println(unitModels.get(0).getUnitName().getAllLanguageString());
        System.out.println("consume time: " + (System.currentTimeMillis() - time) + "::::" + unitModels.size());
    }


}
