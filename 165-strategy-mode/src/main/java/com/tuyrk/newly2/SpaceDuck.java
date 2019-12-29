package com.tuyrk.newly2;

import com.tuyrk.strategy.Duck;

/**
 * 策略模式
 * 太空鸭
 *
 * @author tuyrk
 */
public class SpaceDuck extends Duck {
    public SpaceDuck() {
        super();
        super.setFlyingStrategy(new FlyWithRocket());
    }

    @Override
    public void display() {
        System.out.println("我头戴宇航盔");
    }

    @Override
    public void quack() {
        System.out.println("我通过无线电与你通信");
    }
}
