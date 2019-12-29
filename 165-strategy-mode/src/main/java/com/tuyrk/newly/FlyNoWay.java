package com.tuyrk.newly;

import com.tuyrk.strategy.FlyingStrategy;

/**
 * 策略模式
 * 实现鸭子的飞行行为，不会飞行的策略类
 *
 * @author tuyrk
 */
public class FlyNoWay implements FlyingStrategy {
    @Override
    public void performFly() {
        System.out.println("我不会飞行！");
    }
}
