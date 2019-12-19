package com.tuyrk.analog_jdkproxy;

/**
 * 模拟JDK动态代理
 * 测试类
 * <p>
 * 实现功能：通过Proxy的newProxyInstance返回代理对象
 * 1. 声明一段源码（动态产生代理）
 * 2. 编译编码（JDK Compiler API），产生新的类（代理类）
 * 3. 将这个类load到内存当中，产生一个新的对象（代理对象）
 * 4. return代理对象
 *
 * @author tuyrk
 */
public class Client {
    public static void main(String[] args) throws Exception {
        Car car = new Car();
        InvocationHandler h = new TimeHandler(car);

        Moveable m = (Moveable) Proxy.newProxyInstance(Moveable.class, h);
        m.move();
        System.out.println("==========");
        m.move("tuyrk", "男");
    }
}
