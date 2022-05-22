package com.zhong.designPatterns.proxyMode.dynamicProxy.cglibProxy;

/**
 * @date 2022/4/24 12:03
 */
public class Test {

    public static void main(String[] args) {

        AliSmsService aliSmsService = (AliSmsService) CglibProxyFactory.getProxy(AliSmsService.class);

        aliSmsService.send("ni");

    }
}
