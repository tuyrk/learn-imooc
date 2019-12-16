package com.tuyrk.aggregation;

import com.tuyrk.static_proxy.Moveable;

/**
 * 汽车行驶时间的代理
 *
 * @author tuyrk
 */
public class CarTimeProxy implements Moveable {
    private Moveable m;

    // 因为代理类和被代理类都是实现了相同的接口，所以构造方法传递的对象也是可以是Moveable对象
    public CarTimeProxy(Moveable m) {
        super();
        this.m = m;
    }

    @Override
    public void move() {
        // 记录汽车行驶的时间
        long startTime = System.currentTimeMillis();
        System.out.println("汽车开始行驶...");

        m.move();

        long endTime = System.currentTimeMillis();
        System.out.println("汽车结束行驶...汽车行驶时间：" + (endTime - startTime) + "毫秒");
    }
}
