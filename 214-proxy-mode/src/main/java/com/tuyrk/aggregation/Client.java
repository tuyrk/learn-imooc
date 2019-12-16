package com.tuyrk.aggregation;

import com.tuyrk.static_proxy.Car;

/**
 * 静态代理测试类
 * 功能叠加的聚合方式
 *
 * @author tuyrk
 */
public class Client {
    public static void main(String[] args) {
        // 1. 先记录行驶时间，再记录日志
        /*Car car = new Car();
        CarLogProxy clp = new CarLogProxy(car);
        CarTimeProxy ctp = new CarTimeProxy(clp);
        ctp.move();*/

        // 2. 先记录日志，再记录行驶时间
        Car car = new Car();
        CarTimeProxy ctp = new CarTimeProxy(car);
        CarLogProxy clp = new CarLogProxy(ctp);
        clp.move();
    }
}
