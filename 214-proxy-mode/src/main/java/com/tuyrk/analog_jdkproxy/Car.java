package com.tuyrk.analog_jdkproxy;

import java.util.Random;

/**
 * 一辆车实现可行驶的接口
 *
 * @author tuyrk
 */
public class Car implements Moveable {
    @Override
    public void move() {
        // 实现开车
        try {
            System.out.println("汽车行驶中...");
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void move(String name, String sex) {
        // 实现开车
        try {
            System.out.println(name + sex + "汽车行驶中...");
            Thread.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
