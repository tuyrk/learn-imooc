package com.tuyrk.decorator;

/**
 * 被装饰者-豆浆
 *
 * @author tuyrk
 */
public class Soya implements Drink {
    @Override
    public double money() {
        return 5D;
    }

    @Override
    public String desc() {
        return "纯豆浆";
    }
}
