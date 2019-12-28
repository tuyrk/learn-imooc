package com.tuyrk.handler;

/**
 * 责任链模式
 * 销售总监，可以批准40%以内的折扣
 *
 * @author tuyrk
 */
public class Director extends PriceHandler {
    @Override
    public void processDiscount(float discount) {
        if (discount <= 0.4) {
            System.out.format("%s批准了折扣：%.2f%n", this.getClass().getName(), discount);
        } else {
            successor.processDiscount(discount);
        }
    }
}
