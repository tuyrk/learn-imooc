package com.tuyrk.strategy;

/**
 * 策略模式
 * 测试类
 *
 * @author tuyrk
 */
public class DuckTest {
    public static void main(String[] args) {
        Duck duck = null;
        // duck = new MallardDuck();
        // duck = new RedHeadDuck();
        duck = new SpaceDuck();
        duck.display();
        duck.quack();
        duck.fly();
    }
}

class SpaceDuck extends Duck {
    public SpaceDuck() {
        // 可传入不同的策略
        super.setFlyingStrategy(new FlyWithWin());
    }

    @Override
    public void display() {
        System.out.println("Space Duck");
    }
}