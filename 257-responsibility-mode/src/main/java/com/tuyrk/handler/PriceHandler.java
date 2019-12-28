package com.tuyrk.handler;

import lombok.Data;

/**
 * 责任链模式
 * 价格处理人，负责处理客户折扣申请
 *
 * @author tuyrk
 */
@Data
public abstract class PriceHandler {
    /**
     * 责任链的直接后继，用于传递请求
     */
    protected PriceHandler successor;

    /**
     * 处理折扣申请
     *
     * @param discount 折扣
     */
    public abstract void processDiscount(float discount);

    /**
     * 创建PriceHandler的工厂方法
     *
     * @return PriceHandler对象
     */
    public static PriceHandler createPriceHandler() {
        // 创建对象
        PriceHandler sales = new Sales();
        PriceHandler manager = new Manager();
        PriceHandler director = new Director();
        PriceHandler vp = new VicePresident();
        PriceHandler ceo = new CEO();

        // 指定直接后继
        sales.setSuccessor(manager);
        manager.setSuccessor(director);
        director.setSuccessor(vp);
        vp.setSuccessor(ceo);

        // 返回销售人员
        return sales;
    }
}
