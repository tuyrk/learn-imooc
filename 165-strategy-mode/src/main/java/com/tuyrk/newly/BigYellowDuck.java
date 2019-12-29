package com.tuyrk.newly;

import com.tuyrk.strategy.Duck;

/**
 * 大黄鸭
 *
 * @author tuyrk
 */
public class BigYellowDuck extends Duck {
    public BigYellowDuck() {
        super.setFlyingStrategy(new FlyNoWay());
    }

    @Override
    public void display() {
        System.out.println("我身体很大，全身黄黄");
    }
}
