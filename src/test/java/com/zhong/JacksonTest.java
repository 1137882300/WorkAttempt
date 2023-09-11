package com.zhong;

import cn.hutool.core.util.ZipUtil;
import com.alibaba.nacos.common.utils.MD5Utils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.google.common.collect.Lists;
import com.zhong.utils.JacksonUtils;
import com.zhong.cache.AreaModel;
import com.zhong.cache.PropertyModel;
import com.zhong.cache.UnitModel;
import com.zhong.entity.Cat;
import com.zhong.entity.MultiLanguageString;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @date 2022/3/30 11:29
 */
public class JacksonTest {

    /*
        jackson 不能解析 fastjson （没指定DisableCircularReferenceDetect）的数据
        fastjson 解析 jackson
     */

    @Test
    public void jacksonWrite() throws IOException {
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
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String string = mapper.writeValueAsString(Lists.newArrayList(unitModel));
        FileWriter writer = new FileWriter("jacksonTest.json");
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        bufferedWriter.write(string);
        bufferedWriter.close();
    }

    @Test
    public void jacksonRead() throws IOException {
        long time = System.currentTimeMillis();
        File file = new File("C:\\Users\\EDZ\\Documents\\meta\\fixJsonString.json");
        String content = FileUtils.readFileToString(file, "UTF-8");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<AreaModel> areaModels = mapper.readValue(content, new TypeReference<List<AreaModel>>() {
        });
        System.out.println(areaModels.get(0).getAreaName().getAllLanguageString());
        System.out.println("consume time: " + (System.currentTimeMillis() - time) + "::::" + areaModels.size());
//        consume time: 1098::::147177
    }


    @Test
    public void read2Jackson() throws IOException {
//        String path = "C:\\Users\\EDZ\\Documents\\meta\\6eaea88095ce04ae9369b4d458abf166.json";
        String path = "C:\\Users\\EDZ\\Downloads\\6eaea88095ce04ae9369b4d458abf166.json";
        File file = new File(path);
        String content = FileUtils.readFileToString(file, "UTF-8");
        ObjectMapper mapper = new ObjectMapper();

        long time = System.currentTimeMillis();
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<AreaModel> areaModels = mapper.readValue(content, new TypeReference<List<AreaModel>>() {
        });
        System.out.println("consume time: " + (System.currentTimeMillis() - time) + "::::" + areaModels.size());//consume time: 159::::15403

        System.out.println(mapper.writeValueAsString(areaModels.get(2)));
        Map<Locale, String> map = areaModels.get(2).getAreaName().getAllLanguageString();
        System.out.println(map.get(Locale.US));
        map.forEach((k, v) -> System.out.println(k + ",,," + v));

    }

    @Test
    public void read2Jackson2() throws IOException {
        String path = "C:\\Users\\EDZ\\Documents\\meta\\property.json";
        File file = new File(path);
        String content = FileUtils.readFileToString(file, "UTF-8");
        ObjectMapper mapper = new ObjectMapper();
        long time = System.currentTimeMillis();
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        List<PropertyModel> areaModels = mapper.readValue(content, new TypeReference<List<PropertyModel>>() {});
        JavaType type = mapper.getTypeFactory().constructParametricType(List.class, PropertyModel.class, Map.class);
        List<PropertyModel> areaModels = mapper.readValue(content, type);
        System.out.println("consume time: " + (System.currentTimeMillis() - time) + "::::" + areaModels.size());
        System.out.println(areaModels.get(0).getPropertyName().getAllLanguageString());

    }

    @Test
    public void ss() throws JsonProcessingException {
        String ss = "{\n" +
                "    \"propertyId\": 172801,\n" +
                "    \"platform\": \"product_GLO\",\n" +
                "    \"outerPropertyId\": null,\n" +
                "    \"extendable\": true,\n" +
                "    \"searchable\": true,\n" +
                "    \"propertyType\": 1,\n" +
                "    \"propertyName\": {\n" +
                "        \"languageStringMap\": {\n" +
                "            \"zh_CN\": \"是否包邮\",\n" +
                "            \"ja_JP\": \"1\",\n" +
                "            \"in_ID\": \"1\",\n" +
                "            \"en_US\": \"1\"\n" +
                "        },\n" +
                "        \"defaultLocale\": \"in_ID\",\n" +
                "        \"allLanguageString\": {\n" +
                "            \"zh_CN\": \"是否包邮\",\n" +
                "            \"ja_JP\": \"1\",\n" +
                "            \"in_ID\": \"1\",\n" +
                "            \"en_US\": \"1\"\n" +
                "        },\n" +
                "        \"defaultLanguageString\": \"1\"\n" +
                "    },\n" +
                "    \"valueType\": 1,\n" +
                "    \"propertyValues\": [\n" +
                "        {\n" +
                "            \"propertyValueId\": 2096,\n" +
                "            \"propertyValueName\": {\n" +
                "                \"languageStringMap\": {\n" +
                "                    \"zh_CN\": \"我是新导\"\n" +
                "                },\n" +
                "                \"defaultLocale\": \"in_ID\",\n" +
                "                \"allLanguageString\": {\n" +
                "                    \"zh_CN\": \"我是新导\"\n" +
                "                },\n" +
                "                \"defaultLanguageString\": \"我是新导\"\n" +
                "            },\n" +
                "            \"platform\": \"1\",\n" +
                "            \"status\": 0,\n" +
                "            \"createTime\": 1647523281020,\n" +
                "            \"updateTime\": 1647523281020,\n" +
                "            \"creatorId\": null,\n" +
                "            \"modifiedId\": null,\n" +
                "            \"version\": 0\n" +
                "        },\n" +
                "        {\n" +
                "            \"propertyValueId\": 2098,\n" +
                "            \"propertyValueName\": {\n" +
                "                \"languageStringMap\": {\n" +
                "                    \"en_US\": \"wo shi nes\"\n" +
                "                },\n" +
                "                \"defaultLocale\": \"in_ID\",\n" +
                "                \"allLanguageString\": {\n" +
                "                    \"en_US\": \"wo shi nes\"\n" +
                "                },\n" +
                "                \"defaultLanguageString\": \"wo shi nes\"\n" +
                "            },\n" +
                "            \"platform\": \"bbnmall\",\n" +
                "            \"status\": 0,\n" +
                "            \"createTime\": 1647523451445,\n" +
                "            \"updateTime\": 1647523451445,\n" +
                "            \"creatorId\": null,\n" +
                "            \"modifiedId\": null,\n" +
                "            \"version\": 1\n" +
                "        }\n" +
                "    ],\n" +
                "    \"status\": 1,\n" +
                "    \"createTime\": 1644461120441,\n" +
                "    \"updateTime\": 1647517617164,\n" +
                "    \"creatorId\": 1,\n" +
                "    \"modifiedId\": null,\n" +
                "    \"version\": 2\n" +
                "}";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        PropertyModel unitModel = mapper.readValue(ss, PropertyModel.class);
        System.out.println(unitModel);

    }

    @Test
    public void jacksonTest() throws JsonProcessingException, NoSuchAlgorithmException {
        ObjectMapper mapper = new ObjectMapper();
        Map<Locale, String> map = new HashMap<>();
        map.put(Locale.US, "niao1");
        Cat build2 = Cat.builder().id(222).state(3333).unitName(MultiLanguageString.of(map)).build();
        Cat build = Cat.builder().id(111).state(2222).unitName(MultiLanguageString.of(map)).build();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String string = mapper.writeValueAsString(Lists.newArrayList(build, build2));

        byte[] bytes = string.getBytes(StandardCharsets.UTF_8);
        String newMd5 = MD5Utils.md5Hex(bytes);
        System.out.println("md5: " + newMd5);
//        b5bb534d806312883a1a27f9f9800380
//        9d876d5398c54f345ac35ae3fb46121d
//        6106636f55d076c1d527e9062abb4478
//        6106636f55d076c1d527e9062abb4478
//        6106636f55d076c1d527e9062abb4478
//        2a2df114de0ad3c389026116a4a98435
//        2a2df114de0ad3c389026116a4a98435


//        System.out.println(string);
//        List<Cat> list = mapper.readValue(string, new TypeReference<List<Cat>>() {});
//        System.out.println(list);
    }


    @Test
    public void jacksonReadZip() throws IOException {
        long time = System.currentTimeMillis();
        File file = new File("C:\\Users\\EDZ\\Documents\\meta\\fixJsonString.json");
        String content = FileUtils.readFileToString(file, "UTF-8");
        System.out.println("content.length():: " + content.length());//60941397
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        System.out.println("bytes.length:: " + bytes.length);//61018311

        //压缩
        byte[] gzip = ZipUtil.gzip(bytes);
        System.out.println("gzip.length:: " + gzip.length);//2869748
        String gzipSting = new String(gzip);
        System.out.println("gzipSting.length():: " + gzipSting.length());//2742264
    }


    @Test
    public void jacksonRead2Zip() throws IOException {
        File file = new File("C:\\Users\\EDZ\\Documents\\meta\\fixJsonString.json");
        String content = FileUtils.readFileToString(file, "UTF-8");
        System.out.println("content.length():: " + content.length());//60941397
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        System.out.println("bytes.length:: " + bytes.length);//61018311

        //压缩
        byte[] gzip = ZipUtil.gzip(bytes);
        System.out.println("gzip.length:: " + gzip.length);//2869748
        String gzipSting = new String(gzip);
        System.out.println("gzipSting.length():: " + gzipSting.length());//2742264

        //转json
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(gzipSting);
        //写入文件
        FileWriter writer = new FileWriter("C:\\Users\\EDZ\\Documents\\meta\\jacksonZip.json");
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        bufferedWriter.write(jsonString);
        bufferedWriter.close();

    }

    @Test
    public void jacksonRead2Zip2() throws IOException {
        File file = new File("C:\\Users\\EDZ\\Documents\\meta\\fixJsonString.json");
        String content = FileUtils.readFileToString(file, "UTF-8");
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        //压缩
        byte[] gzip = ZipUtil.gzip(bytes);
        //转json
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(gzip);
        //写入文件
        FileWriter writer = new FileWriter("C:\\Users\\EDZ\\Documents\\meta\\jacksonZip.json");
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        bufferedWriter.write(jsonString);
        bufferedWriter.close();
    }

    @Test
    public void jacksonRead2Zip3() throws IOException {
        File file = new File("C:\\Users\\EDZ\\Documents\\meta\\fixJsonString.json");
        String content = FileUtils.readFileToString(file, "UTF-8");
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        System.out.println(bytes.length);
        //压缩
        byte[] gzip = ZipUtil.gzip(content, StandardCharsets.UTF_8.name());
        System.out.println(gzip.length);
        //转json
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(bytes);
        //写入文件
        FileWriter writer = new FileWriter("C:\\Users\\EDZ\\Documents\\meta\\jacksonZip.json");
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        bufferedWriter.write(jsonString);
        bufferedWriter.close();
    }

    @Test
    public void ReadJacksonZip() throws IOException {
        File file = new File("C:\\Users\\EDZ\\Documents\\meta\\jacksonZip.json");
        String content = FileUtils.readFileToString(file, "UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<AreaModel> areaModels = mapper.readValue(content, new TypeReference<List<AreaModel>>() {
        });
        System.out.println(areaModels.get(0).getAreaName().getAllLanguageString());
    }


    @Test
    public void jacksonRead2Zip4() throws IOException {
        File file = new File("C:\\Users\\EDZ\\Documents\\meta\\fixJsonString.json");

        //压缩
        File zip = ZipUtil.zip(file);

        String content2 = FileUtils.readFileToString(zip, "UTF-8");

        //写入文件
        FileWriter writer = new FileWriter("C:\\Users\\EDZ\\Documents\\meta\\jacksonZip.json");
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        bufferedWriter.write(content2);
        bufferedWriter.close();
    }

    @Test
    public void ssss() throws JsonProcessingException {
        String ss = "{\"area\":\"c8220deb2c510e92dd5e08f7cb031c06\",\"country\":\"5a7dcc641e35a92f71e1ede89e738ae6\",\"unit\":\"19e00958b88e95e6fbf63032b51e144f\",\"property\":\"22222\",\"currency\":\"47e71fda82778b4f3223ee8dfe895fbb\",\"category\":\"7d08db3415372339b82935b09261b1cf\"}\n";

        ObjectMapper mapper = new ObjectMapper();

//        Map<String, String> map = mapper.readValue(ss, new TypeReference<Map<String, String>>() {
//        });
//        System.out.println(map);

//        String string = mapper.writeValueAsString(ss);
//        System.out.println(string);

//        Map map = mapper.readValue(ss, Map.class);

        JsonNode jsonNode = mapper.readTree(ss);
        JsonNode area = jsonNode.get("area");
        System.out.println(area);
        System.out.println(jsonNode);

        String string = mapper.writeValueAsString(ss);
    }

    @Test
    public void parse2() throws IOException {
        long time = System.currentTimeMillis();
        File file = new File("fastjsonTest.json");
        String content = FileUtils.readFileToString(file, "UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<UnitModel> areaModels = mapper.readValue(content, new TypeReference<List<UnitModel>>() {
        });
        System.out.println(areaModels);
    }


    @Test
    public void jacksonUtil() throws IOException {
        File file = new File("fastjsonTest.json");
        String content = FileUtils.readFileToString(file, "UTF-8");
        content = null;
        try {
            List<UnitModel> unitModels = JacksonUtils.toObj(content, new TypeReference<List<UnitModel>>() {
            });
            System.out.println(unitModels);
        } catch (Exception e) {
            System.out.println("error");
        }
        System.out.println("sssss");

//[UnitModel(unitId=null, unitName=MultiLanguageString(super=com.zhong.entity.MultiLanguageString@419573e5, languageStringMap={$ref=$[0].unitName.allLanguageString}, defaultLocale=in_ID), unitCode=111, status=null, platform=null, createTime=null, updateTime=null, creatorId=null, modifiedId=null, version=null, extendMap={sss=aaaa})]
//[UnitModel(unitId=null, unitName=MultiLanguageString(super=com.zhong.entity.MultiLanguageString@419573e5, languageStringMap={$ref=$[0].unitName.allLanguageString}, defaultLocale=in_ID), unitCode=111, status=null, platform=null, createTime=null, updateTime=null, creatorId=null, modifiedId=null, version=null, extendMap={sss=aaaa})]
//[UnitModel(unitId=null, unitName=MultiLanguageString(super=com.zhong.entity.MultiLanguageString@419573e5, languageStringMap={$ref=$[0].unitName.allLanguageString}, defaultLocale=in_ID), unitCode=null, status=null, platform=null, createTime=null, updateTime=null, creatorId=null, modifiedId=null, version=null, extendMap={sss=aaaa})]
//[UnitModel(unitId=null, unitName=MultiLanguageString(super=com.zhong.entity.MultiLanguageString@419573e5, languageStringMap={$ref=$[0].unitName.allLanguageString}, defaultLocale=in_ID), unitCode=null, status=null, platform=null, createTime=null, updateTime=null, creatorId=null, modifiedId=null, version=null, extendMap={sss=aaaa})]
    }


}
