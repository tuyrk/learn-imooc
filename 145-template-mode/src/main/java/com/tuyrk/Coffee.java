package com.tuyrk;

/**
 * 模板方法模式
 * 具体子类，提供了咖啡的具体实现
 *
 * @author tuyrk
 */
public class Coffee extends RefreshBeverage {
    @Override
    protected void brew() {
        System.out.println("用沸水冲泡咖啡");
    }

    @Override
    protected void addCondiments() {
        System.out.println("加入糖和牛奶");
    }
}
