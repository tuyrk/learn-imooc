package com.tuyrk.newly2;

import com.tuyrk.strategy.FlyingStrategy;

/**
 * 策略模式
 * 实现鸭子的飞行行为，使用火箭飞行策略类
 *
 * @author tuyrk
 */
public class FlyWithRocket implements FlyingStrategy {
    @Override
    public void performFly() {
        System.out.println("用火箭在太空遨游");
    }
}
