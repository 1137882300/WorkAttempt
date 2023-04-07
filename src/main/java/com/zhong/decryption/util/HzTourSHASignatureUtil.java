package com.zhong.decryption.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @date: 2022-09-24
 * 签名工具
 **/
public class HzTourSHASignatureUtil {

    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";

    /**
     * 比较签名
     */
    public static boolean compareSign(String sign, String key, Map<String, String> params) {
        if (sign == null) {
            return false;
        }
        return sign.equals(genSignature(key, params));
    }

    /**
     * 生成签名
     */
    public static String genSignature(String key, Map<String, String> params) {
        String sign = "";
        try {
            sign = hmacSHA1(key, genParamsInfo(params));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("签名生成错误" + e.getMessage());
        }
        return sign;
    }

    private static String genParamsInfo(Map<String, String> params) {
        if (null == params || params.size() == 0) {
            return "";
        }
        Set<String> keySet = params.keySet();
        keySet.remove("sign");
        keySet.remove("appId");
        List<String> keyList = keySet.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        StringBuilder paramsInfo = new StringBuilder();
        for (String key : keyList) {
            String value = params.get(key);
            if (value == null) {
                continue;
            }
            paramsInfo.append(key).append("=");
            paramsInfo.append(value);
            paramsInfo.append(";");
        }
        System.out.println("paramsInfo=====>" + paramsInfo.toString());
        return paramsInfo.toString();
    }

    private static String hmacSHA1(String key, String data) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(ENCODING), MAC_NAME);
            Mac mac = Mac.getInstance(MAC_NAME);
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes(ENCODING));
            return org.apache.commons.codec.binary.Base64.encodeBase64String(rawHmac);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("b", "b");
        map.put("a", "b");
        map.put("d", "b");
        map.put("c", "b");
        System.out.println(genParamsInfo(map));
        System.out.println(genSignature("asdgasdg", map));
    }
}
