package com.tuyrk.decorator;

/**
 * 具体装饰-西瓜
 *
 * @author tuyrk
 */
public class Watermelon extends Decorator {
    public Watermelon(Drink drink) {
        super(drink);
    }

    @Override
    public double money() {
        return super.money() + 4.0;
    }

    @Override
    public String desc() {
        return super.desc() + "+西瓜";
    }
}
