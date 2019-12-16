package com.tuyrk.jdk_proxy;

import com.tuyrk.static_proxy.Car;
import com.tuyrk.static_proxy.Moveable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * JDK动态代理测试类
 *
 * @author tuyrk
 */
public class Test {
    public static void main(String[] args) {
        // 1. JDK动态代理
        /*Car car = new Car();
        InvocationHandler h = new TimeHandler(car);
        Class<? extends Car> cls = car.getClass();
        // 使用Proxy类newProxyInstance方法动态创建代理类
        *//*
          loader 类加载器
          interfaces 实现接口
          h InvocationHandler
         *//*
        Moveable m = (Moveable) Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), h);
        m.move();*/


        // 2. 功能叠加的JDK动态代理
        Car car = new Car();
        InvocationHandler h = new TimeHandler(car);
        Class<? extends Car> cls = car.getClass();
        Moveable m = (Moveable) Proxy.newProxyInstance(cls.getClassLoader(), cls.getInterfaces(), h);

        LogHandler h2 = new LogHandler(m);
        Class<? extends Moveable> cls2 = m.getClass();
        Moveable m2 = (Moveable) Proxy.newProxyInstance(cls2.getClassLoader(), cls2.getInterfaces(), h2);
        m2.move();
    }
}