package com.tuyrk.handler;

/**
 * 责任链模式
 * 销售，可以批准5%以内的折扣
 *
 * @author tuyrk
 */
public class Sales extends PriceHandler {
    @Override
    public void processDiscount(float discount) {
        if (discount <= 0.05) {
            // 此处有打印转换精度的问题
            System.out.format("%s批准了折扣：%.2f%n", this.getClass().getName(), discount);
        } else {
            successor.processDiscount(discount);
        }
    }
}
