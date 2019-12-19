package com.tuyrk.analog_jdkproxy;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 模拟JDK动态代理
 * 时间业务逻辑处理
 *
 * @author tuyrk
 */
public class TimeHandler implements InvocationHandler {
    private Object target;

    public TimeHandler(Object target) {
        super();
        this.target = target;
    }

    @Override
    public void invoke(Object obj, Method method, Object[] args) {
        long startTime = System.currentTimeMillis();
        System.out.println("汽车开始行驶...");

        try {
            method.invoke(target, args);
        } catch (Exception e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("汽车结束行驶...汽车行驶时间：" + (endTime - startTime) + "毫秒");
    }
}
