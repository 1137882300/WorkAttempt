package com.zhong.designPatterns.proxyMode.dynamicProxy.cglibProxy;

/**
 * @date 2022/4/24 12:04
 */
public class AliSmsService {

    public String send(String message) {
        System.out.println("send message:" + message);
        return message;
    }
}
