package com.tuyrk.original;

/**
 * 策略模式
 * 绿头鸭
 *
 * @author tuyrk
 */
public class MallardDuck extends Duck {
    @Override
    public void display() {
        System.out.println("我的头是绿色的");
    }
}
