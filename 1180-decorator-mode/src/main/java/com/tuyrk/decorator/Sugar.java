package com.tuyrk.decorator;

/**
 * 具体装饰-糖
 *
 * @author tuyrk
 */
public class Sugar extends Decorator {
    public Sugar(Drink drink) {
        super(drink);
    }

    @Override
    public double money() {
        return super.money() + 2.1;
    }

    @Override
    public String desc() {
        return super.desc() + "+糖";
    }
}
