package com.tuyrk.static_proxy;

/**
 * 使用继承方式实现静态代理
 *
 * @author tuyrk
 */
public class Car2 extends Car {
    @Override
    public void move() {
        // 记录汽车行驶的时间
        long startTime = System.currentTimeMillis();
        System.out.println("汽车开始行驶...");

        super.move();

        long endTime = System.currentTimeMillis();
        System.out.println("汽车结束行驶...汽车行驶时间：" + (endTime - startTime) + "毫秒");
    }
}
