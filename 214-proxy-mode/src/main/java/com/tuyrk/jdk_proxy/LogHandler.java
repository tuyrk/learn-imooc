package com.tuyrk.jdk_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 日志处理
 * JDK动态代理
 *
 * @author tuyrk
 */
public class LogHandler implements InvocationHandler {
    private Object target;

    public LogHandler(Object target) {
        super();
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("日志开始");
        method.invoke(target, args);
        System.out.println("日志结束");
        return null;
    }
}
