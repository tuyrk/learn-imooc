package com.tuyrk.polymerize;

/**
 * 二相转三相的插座适配器-聚合方式
 * 怎们实现的？
 * 1. 实现目标接口 ThreePlug
 * 2. 聚合GbTwoPlug类到当前适配器类中为成员变量
 * 3. 把"被适配者"作为一个对象聚合到适配器类中，以修改目标接口包装被适配者
 *
 * @author tuyrk
 */
public class TwoPlugAdapter implements ThreePlug {

    private GbTwoPlug gbTwoPlug;

    public TwoPlugAdapter(GbTwoPlug gbTwoPlug) {
        this.gbTwoPlug = gbTwoPlug;
    }

    @Override
    public void powerWithThree() {
        System.out.println("通过转换-聚合方式");
        gbTwoPlug.powerWithTwo();
    }
}
