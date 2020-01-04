package com.tuyrk.decorator;

/**
 * 被装饰者-果汁
 *
 * @author tuyrk
 */
public class Fruit implements Drink {
    @Override
    public double money() {
        return 6D;
    }

    @Override
    public String desc() {
        return "果汁";
    }
}
