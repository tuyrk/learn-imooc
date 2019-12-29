package com.tuyrk.original;

/**
 * 策略模式
 * 红头鸭
 *
 * @author tuyrk
 */
public class RedHeadDuck extends Duck {
    @Override
    public void display() {
        System.out.println("我的头是红色的");
    }
}
