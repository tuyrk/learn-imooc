package com.tuyrk.hook;

/**
 * 模板方法模式
 * 具体子类，提供了制备茶的具体实现
 *
 * @author tuyrk
 */
public class Tea extends RefreshBeverage {
    /**
     * 子类通过覆盖的形式选择挂载钩子函数
     *
     * @return 是否加入调料
     */
    @Override
    protected boolean isCustomerWantsCondiments() {
        return false;
    }

    @Override
    protected void brew() {
        System.out.println("用80度的热水浸泡茶叶5分钟");
    }

    @Override
    protected void addCondiments() {
        System.out.println("加入柠檬");
    }
}
