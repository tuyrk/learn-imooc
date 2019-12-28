package com.tuyrk.handler2;

import com.tuyrk.handler.*;

/**
 * 责任链模式
 * 创建PriceHandler的工厂类
 *
 * @author tuyrk
 */
public class PriceHandlerFactory {
    public static PriceHandler createPriceHandler() {
        // 创建对象
        PriceHandler sales = new Sales();
        PriceHandler lead = new Lead();
        PriceHandler manager = new Manager();
        PriceHandler director = new Director();
        PriceHandler vp = new VicePresident();
        PriceHandler ceo = new CEO();

        // 指定直接后继
        sales.setSuccessor(lead);
        lead.setSuccessor(manager);
        manager.setSuccessor(director);
        director.setSuccessor(vp);
        vp.setSuccessor(ceo);

        // 返回销售人员
        return sales;
    }
}
