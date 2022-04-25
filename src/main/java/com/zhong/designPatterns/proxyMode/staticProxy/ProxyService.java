package com.zhong.designPatterns.proxyMode.staticProxy;

import com.zhong.designPatterns.proxyMode.IService;
import com.zhong.designPatterns.proxyMode.Service;

/**
 * 代理类
 */
public class ProxyService implements IService {

    /**
     * 持有被代理对象的引用
     */
    private IService service;

    /**
     * 默认代理Service类
     */
    public ProxyService() {
        this.service = new Service();
    }

    /**
     * 也可以代理实现相同接口的其他类
     */
    public ProxyService(IService service) {
        this.service = service;
    }

    @Override
    public void service() {
        System.out.println("静态代理类执行的方法 start");
        service.service();
        System.out.println("静态代理类执行的方法 end");
    }
}