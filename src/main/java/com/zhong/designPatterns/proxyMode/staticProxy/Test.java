package com.zhong.designPatterns.proxyMode.staticProxy;

import com.zhong.designPatterns.proxyMode.IService;
import com.zhong.designPatterns.proxyMode.Service;

/**
 * 测试
 */
public class Test {
    /**
     * 简单说即是在不改变源码的情况下，实现对目标对象的功能扩展。
     * 静态代理需要我们写出代理类和被代理类，而且一个代理类和一个被代理类一一对应。
     * 代理类和被代理类需要实现同一个接口，通过聚合使得代理对象中有被代理对象的引用，以此实现代理对象控制被代理对象的目的。
     */
    public static void main(String[] args) {
        IService service = new Service();
        //传入被代理类的对象
        ProxyService proxyService = new ProxyService(service);
        proxyService.service();
    }

}
