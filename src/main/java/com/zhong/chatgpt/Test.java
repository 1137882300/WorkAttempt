package com.zhong.chatgpt;

import com.alibaba.fastjson.JSONObject;
import com.zhong.network.HttpService;
//import javafx.util.Pair;

/**
 * @author: juzi
 * @date: 2023/4/14
 * @desc:
 */
public class Test {


    public static void main(String[] args) {

        // 构造请求体，JSON格式，包含一个字符串参数prompt和一个整数参数max_tokens，如果有其他参数，延续即可。
        String json = "{\"model\": \"text-davinci-003\", \"prompt\": \"Hello, ChatGPT!\", \"max_tokens\": 1000}";
        JSONObject jsonObject = JSONObject.parseObject(json);
//        Pair<String, String> header = new Pair<>("Authorization", "Bearer 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");
//        HttpService.post("https://api.openai.com/v1/chat/completions", header, jsonObject);

    }


}
