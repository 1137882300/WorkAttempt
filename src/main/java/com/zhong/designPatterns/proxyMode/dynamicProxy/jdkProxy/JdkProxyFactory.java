package com.zhong.designPatterns.proxyMode.dynamicProxy.jdkProxy;

import java.lang.reflect.Proxy;

/**
 * @date 2022/4/24 11:49
 * 代理对象的工厂类
 * JDK 动态代理有一个最致命的问题是其只能代理实现了接口的类。
 */
public class JdkProxyFactory {

    public static Object getProxy(Object target) {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(), // 目标类的类加载
                target.getClass().getInterfaces(),  // 代理需要实现的接口，可指定多个
                new ServiceInvocationHandler(target)   // 代理对象对应的自定义 InvocationHandler
        );
    }

}
