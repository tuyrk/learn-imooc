package com.tuyrk.newly;

import com.tuyrk.strategy.Duck;

/**
 * 策略模式
 * 测试类
 *
 * @author tuyrk
 */
public class DuckTest {
    public static void main(String[] args) {
        // Duck duck = new RubberDuck();
        Duck duck = new BigYellowDuck();
        duck.display();
        duck.quack();
        duck.fly();
    }
}