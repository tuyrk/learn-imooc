package com.tuyrk.static_proxy;

/**
 * 使用聚合方式实现静态代理
 *
 * @author tuyrk
 */
public class Car3 implements Moveable {
    private Car car;

    public Car3(Car car) {
        super();
        this.car = car;
    }

    @Override
    public void move() {
        // 记录汽车行驶的时间
        long startTime = System.currentTimeMillis();
        System.out.println("汽车开始行驶...");

        car.move();

        long endTime = System.currentTimeMillis();
        System.out.println("汽车结束行驶...汽车行驶时间：" + (endTime - startTime) + "毫秒");
    }
}
