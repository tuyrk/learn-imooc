package com.tuyrk.analog_jdkproxy;

import java.lang.reflect.Method;

/**
 * 模拟JDK动态代理
 * 业务处理类
 *
 * @author tuyrk
 */
public interface InvocationHandler {
    /**
     * 模拟JDK动态代理
     *
     * @param obj    被代理对象
     * @param method 被代理对象方法
     * @param args   被代理对象方法的参数
     */
    void invoke(Object obj, Method method, Object[] args);
}
