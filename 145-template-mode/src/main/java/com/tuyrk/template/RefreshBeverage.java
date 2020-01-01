package com.tuyrk.template;

/**
 * 模板方法模式
 * 抽象基类，为所有子类提供一个算法框架。提神饮料
 *
 * @author tuyrk
 */
public abstract class RefreshBeverage {
    /**
     * 制备饮料的模板方法
     * 封装了所有子类共同遵循的算法框架
     */
    public final void prepareBeverageTemplate() {
        // 步骤一：将水煮沸
        boilWater();
        // 步骤二：泡制饮料
        brew();
        // 步骤三：将饮料倒入杯中
        pourInCup();
        // 步骤四：加入调味料
        addCondiments();
    }

    /**
     * 基本方法，将水煮沸
     */
    private void boilWater() {
        System.out.println("将水煮沸");
    }

    /**
     * 抽象的基本方法，泡制饮料
     */
    protected abstract void brew();

    /**
     * 基本方法，将饮料倒入杯中
     */
    private void pourInCup() {
        System.out.println("将饮料倒入杯中");
    }

    /**
     * 抽象的基本方法，加入调味料
     */
    protected abstract void addCondiments();
}
