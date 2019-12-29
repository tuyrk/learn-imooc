package com.tuyrk.newly2;

import com.tuyrk.newly.BigYellowDuck;
import com.tuyrk.strategy.Duck;

/**
 * 策略模式
 * 测试类
 *
 * @author tuyrk
 */
public class DuckTest {
    public static void main(String[] args) {
        Duck duck = null;
        duck = new SpaceDuck();
        duck.display();
        duck.quack();
        duck.fly();
    }
}