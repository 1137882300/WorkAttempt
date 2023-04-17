package com.zhong.working;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.zhong.network.HttpService;
import javafx.util.StringConverter;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author: juzi
 * @date: 2023/4/14
 * @desc:
 */
public class MappingTest {

    public static void main(String[] args) {
        Class<?> aClass = getClassFromClass("com.zhong.working.entity.ActivityDetailVO");
        printFields(aClass);
    }

    public static void main4(String[] args) {
        //获取类的信息
        String apiId = "";
        String pathOfInBody = "";
        String pathOfOutBody = "";
        String pathOfQuery = "";
        Field[] fieldsOfInBody = getFieldFromClass(pathOfInBody);
        Field[] fieldsOfOutBody = getFieldFromClass(pathOfOutBody);
        Field[] fieldsOfQuery = getFieldFromClass(pathOfQuery);

        JSONObject body = new JSONObject();
        buildBodyOfBase(body, apiId);
        buildBodyOfQueryParams(body, fieldsOfQuery);
        buildBodyOfInBodyParams(body, fieldsOfInBody);
        buildBodyOfOutBodyParams(body, fieldsOfOutBody);
        new MappingTest().doRequest(body);
    }

    @SneakyThrows
    private static Field[] getFieldFromClass(String path) {
        Class<?> aClass = Class.forName(path);
        return aClass.getDeclaredFields();
    }

    @SneakyThrows
    private static Class<?> getClassFromClass(String path) {
        return Class.forName(path);
    }


    private void doRequest(JSONObject body) {
        String url = "https://op-irs.zj.gov.cn/wireless/service/portal/api/info/updateParam";
        Map<String, String> header = Maps.newHashMap();
        header.put("Cookie", "G_irs_platform=pc; G_irs_accountType=gov; G_irs_gsid=a7d429c5ae0d49adb4cbe14959599483; token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2ODE3ODExMzksImlhdCI6MTY4MTY5NDczOSwiY29udGVudCI6IntcImFjY291bnRJZFwiOjc5Mzk2MjQ2LFwiZW1wbG95ZWVDb2RlXCI6XCJHRV9jMTM4YjEyOWNiZjU0NTg2OGVhNTc4NGRlMzI4ZTJmYlwiLFwidGVuYW50SWRcIjoxOTY3Mjl9In0.c0954cdV9op1KDmSJkomEAtj-RmycbvOq-nekkD6bD4");
        JSONObject result = HttpService.post(url, header, body);
        System.out.println(result);
    }


    private static void buildBodyOfBase(JSONObject jsonObject, String apiId) {
        jsonObject.fluentPut("mappingType", 1)
                .fluentPut("contentType", "application/json")
                .fluentPut("apiId", apiId)
        ;
    }


    private static void buildBodyOfQueryParams(JSONObject jsonObject, Field[] fields) {
        JSONArray jsonArray = new JSONArray();
        JSONObject innerJson = new JSONObject()
                //字段名称
                .fluentPut("name", "")
                //参数类型
                .fluentPut("type", "")
                //基本类型
                .fluentPut("subType", "")
//                .fluentPut("required", "")
                ;
        jsonArray.add(innerJson);
        jsonObject.fluentPut("queryParams", jsonArray);
    }

    private static void buildBodyOfInBodyParams(JSONObject jsonObject, Field[] fields) {
        JSONArray jsonArray = new JSONArray();
        JSONObject innerJson = new JSONObject()
                //字段名称
                .fluentPut("name", "")
                //参数类型
                .fluentPut("type", "")
                //基本类型
                .fluentPut("subType", "");

        //下级
        JSONArray subArr = new JSONArray();
        JSONObject subJson = new JSONObject()
                .fluentPut("_level_", 1);
        subArr.add(subJson);
        innerJson.fluentPut("childParams", subArr);

        jsonArray.add(innerJson);
        jsonObject.fluentPut("inBodyParams", jsonArray);
    }

    private static void buildBodyOfOutBodyParams(JSONObject jsonObject, Field[] fields) {
        JSONArray jsonArray = new JSONArray();
        JSONObject innerJson = new JSONObject()
                //字段名称
                .fluentPut("name", "")
                //参数类型
                .fluentPut("type", "")
                //具体类型
                .fluentPut("subType", "");

        //下级
        JSONArray subArr = new JSONArray();
        JSONObject subJson = new JSONObject()
                .fluentPut("_level_", 1);
        subArr.add(subJson);
        innerJson.fluentPut("childParams", subArr);
        jsonArray.add(innerJson);
        jsonObject.fluentPut("outBodyParams", jsonArray);
    }

    @SneakyThrows
    private static void setOfFields(Class<?> clazz, int status, JSONObject jsonObject) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Class<?> type = field.getType();
            String typeName = field.getType().getName();
            String name = field.getName();


            if (List.class.isAssignableFrom(field.getType())) {
                Type genericType = field.getGenericType();
                if (genericType instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) genericType;
                    Type listType = parameterizedType.getActualTypeArguments()[0];
                    if (listType instanceof Class<?>) {
                        if (((Class<?>) listType).isPrimitive() || listType.getTypeName().startsWith("java.lang.")) {
                            continue;
                        }
                        setOfFields((Class<?>) listType, 1, jsonObject);
                    } else if (listType instanceof ParameterizedType) {
                        setOfFields((Class<?>) ((ParameterizedType) listType).getRawType(), 3, jsonObject);
                    }
                }
            } else if (!field.getType().isPrimitive() && !field.getType().getName().startsWith("java.lang")) {
                setOfFields(field.getType(), 2, jsonObject);
            }

            //set 数据
            buildBodyOfOutBodyParams(jsonObject, null);


        }
    }

    private static void parseFields(JSONObject jsonObject, Field[] fields) {
        for (Field field : fields) {
            Class<?> type = field.getType();
            String typeName = field.getType().getName();
            String name = field.getName();

            String subType = String.valueOf(Config.ConcreteType.String.value);
            if (Integer.class.isAssignableFrom(type)) {
                subType = String.valueOf(Config.ConcreteType.Int.value);
            } else if (Boolean.class.isAssignableFrom(type)) {
                subType = String.valueOf(Config.ConcreteType.Boolean.value);
            }

            String typeStr = String.valueOf(Config.ParameterType.baseType.value);
            if (type.isPrimitive() || String.class.isAssignableFrom(type)) {
                //是基本类型+string
                typeStr = String.valueOf(Config.ParameterType.baseType.value);
            } else if (!typeName.startsWith("java.lang")) {
                typeStr = String.valueOf(Config.ParameterType.complexType.value);
            } else if (List.class.isAssignableFrom(type)) {
                typeStr = String.valueOf(Config.ParameterType.baseList.value);
            }
        }
    }

    @SneakyThrows
    private static void printFields(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            System.out.println("Field type: " + field.getType().getName());
            System.out.println("Field name: " + field.getName());
            if (List.class.isAssignableFrom(field.getType())) {
                Type genericType = field.getGenericType();
                if (genericType instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) genericType;
                    Type listType = parameterizedType.getActualTypeArguments()[0];
                    if (listType instanceof Class<?>) {
                        if (((Class<?>) listType).isPrimitive() || listType.getTypeName().startsWith("java.lang.")) {
                            continue;
                        }
                        printFields((Class<?>) listType);
                    } else if (listType instanceof ParameterizedType) {
                        printFields((Class<?>) ((ParameterizedType) listType).getRawType());
                    }
                }
            } else if (!field.getType().isPrimitive() && !field.getType().getName().startsWith("java.lang")) {
                printFields(field.getType());
            }
        }
    }

    /**
     * 判断一个对象是否是基本类型或基本类型的封装类型:isPrimitive
     */
    private boolean isPrimitive(Object obj) {
        try {
            return ((Class<?>) obj.getClass().getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }


    public static void main2(String[] args) {

        String url = "https://op-irs.zj.gov.cn/wireless/service/portal/api/info/updateParam";

        JSONObject body = new JSONObject();

        String ss = "{\n" +
                "    \"mappingType\": 1,\n" +
                "    \"contentType\": \"application/x-www-form-urlencoded\",\n" +
                "    \"queryParams\": [],\n" +
                "    \"inBodyParams\": [],\n" +
                "    \"outBodyParams\": [\n" +
                "        {\n" +
                "            \"name\": \"title\",\n" +
                "            \"type\": 1,\n" +
                "            \"subType\": 4\n" +
                "        }\n" +
                "    ],\n" +
                "    \"apiId\": \"1002270448\"\n" +
                "}";
        JSONObject jsonObject = JSONObject.parseObject(ss);
        Map<String, String> header = Maps.newHashMap();
        header.put("Cookie", "G_irs_platform=pc; G_irs_accountType=gov; G_irs_gsid=c5e018520c8a434c967d4d6d271994d0; token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2ODE1NDY4MTIsImlhdCI6MTY4MTQ2MDQxMiwiY29udGVudCI6IntcImFjY291bnRJZFwiOjc5Mzk2MjQ2LFwiZW1wbG95ZWVDb2RlXCI6XCJHRV9jMTM4YjEyOWNiZjU0NTg2OGVhNTc4NGRlMzI4ZTJmYlwiLFwidGVuYW50SWRcIjoxOTY3Mjl9In0.uf5-AeCN4O7S4PQH1u2ELIZweD89DcwV-wKla4FiTk4");
        HttpService.post(url, header, jsonObject);
    }
}
