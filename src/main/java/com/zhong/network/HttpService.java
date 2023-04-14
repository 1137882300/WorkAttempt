package com.zhong.network;

import com.alibaba.fastjson.JSONObject;
import javafx.util.Pair;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;

/**
 * @date 2022/11/23 13:34
 */
public class HttpService {

    public static JSONObject post(String path, JSONObject body) {
        Request request = Request.Post(path)
                .addHeader("Content-Type", "application/json;  charset=utf-8")
                .bodyString(body.toJSONString(), ContentType.APPLICATION_JSON);
        return doRequest(request);
    }

    public static JSONObject get(String path) {
        Request request = Request.Get(path)
                .addHeader("Content-Type", "application/json;  charset=utf-8");
        return doRequest(request);
    }


    public static JSONObject post(String path, Pair<String,String> header , JSONObject body) {
        Request request = Request.Post(path)
                .addHeader("Content-Type", "application/json;  charset=utf-8")
                .addHeader(header.getKey(), header.getValue())
                .bodyString(body.toJSONString(), ContentType.APPLICATION_JSON);
        return doRequest(request);
    }


    private static JSONObject doRequest(Request request) {
        try {
            HttpResponse response = request.execute().returnResponse();
            int statusCode = response.getStatusLine().getStatusCode();
            String responseString = EntityUtils.toString(response.getEntity());
            if (statusCode >= 200 && statusCode < 300) {
                return JSONObject.parseObject(responseString);
            }
        } catch (Throwable throwable) {
        }
        throw new RuntimeException();
    }

}
