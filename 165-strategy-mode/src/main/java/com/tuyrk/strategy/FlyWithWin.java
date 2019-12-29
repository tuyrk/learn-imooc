package com.tuyrk.strategy;

/**
 * 策略模式
 * 实现鸭子的飞行行为，用翅膀飞行的类
 *
 * @author tuyrk
 */
public class FlyWithWin implements FlyingStrategy {
    @Override
    public void performFly() {
        System.out.println("振翅高飞");
    }
}
