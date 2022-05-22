package com.zhong.designPatterns.proxyMode.dynamicProxy.cglibProxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @date 2022/4/24 11:56
 */
public class MyMethodInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        System.out.println("cjlib 动态代理 调用方法之前，我们可以添加自己的操作");
        System.out.println("before method " + method.getName());

        Object invoke = methodProxy.invokeSuper(o, objects);

        System.out.println("cjlib 动态代理 调用方法之后，我们可以添加自己的操作");
        System.out.println("after method " + method.getName());

        return invoke;
    }
}
