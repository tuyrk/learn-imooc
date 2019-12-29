package com.tuyrk.strategy;

/**
 * 策略模式
 * 绿头鸭
 *
 * @author tuyrk
 */
public class MallardDuck extends Duck {
    public MallardDuck() {
        super.setFlyingStrategy(new FlyWithWin());
    }

    @Override
    public void display() {
        System.out.println("我的头是绿色的");
    }
}
