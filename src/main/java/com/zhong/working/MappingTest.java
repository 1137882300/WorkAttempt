package com.zhong.working;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPatch;
import com.google.common.collect.Maps;
import com.zhong.network.HttpService;

import java.util.Map;

/**
 * @author: juzi
 * @date: 2023/4/14
 * @desc:
 */
public class MappingTest {

    public static void main(String[] args) {


        JSONObject body = new JSONObject();


        new MappingTest().doRequest(body);
    }


    private void doRequest(JSONObject body) {
        String url = "https://op-irs.zj.gov.cn/wireless/service/portal/api/info/updateParam";
        Map<String, String> header = Maps.newHashMap();
        header.put("Cookie", "G_irs_platform=pc; G_irs_accountType=gov; G_irs_gsid=c5e018520c8a434c967d4d6d271994d0; token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2ODE1NDY4MTIsImlhdCI6MTY4MTQ2MDQxMiwiY29udGVudCI6IntcImFjY291bnRJZFwiOjc5Mzk2MjQ2LFwiZW1wbG95ZWVDb2RlXCI6XCJHRV9jMTM4YjEyOWNiZjU0NTg2OGVhNTc4NGRlMzI4ZTJmYlwiLFwidGVuYW50SWRcIjoxOTY3Mjl9In0.uf5-AeCN4O7S4PQH1u2ELIZweD89DcwV-wKla4FiTk4");
        HttpService.post(url, header, body);
    }


    private void buildBody(JSONObject jsonObject, String apiId) {
        jsonObject.fluentPut("mappingType", 1)
                .fluentPut("contentType", "application/x-www-form-urlencoded")
                .fluentPut("apiId", apiId)
        ;


    }


    private void buildBodyOfQueryParams(JSONObject jsonObject) {

    }

    private void buildBodyOfInBodyParams(JSONObject jsonObject) {

    }

    private void buildBodyOfOutBodyParams(JSONObject jsonObject) {

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
