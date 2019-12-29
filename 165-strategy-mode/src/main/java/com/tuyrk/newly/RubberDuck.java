package com.tuyrk.newly;

import com.tuyrk.strategy.Duck;

/**
 * 策略模式
 * 橡胶鸭
 *
 * @author tuyrk
 */
public class RubberDuck extends Duck {
    public RubberDuck() {
        super.setFlyingStrategy(new FlyNoWay());
    }

    @Override
    public void display() {
        System.out.println("我全身发黄，嘴巴很红");
    }

    @Override
    public void quack() {
        System.out.println("嘎~嘎~嘎~");
    }
}
