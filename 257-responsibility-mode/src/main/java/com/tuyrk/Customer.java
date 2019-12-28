package com.tuyrk;

import com.tuyrk.handler.PriceHandler;
import lombok.Data;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * 责任链模式
 * 客户，请求折扣
 *
 * @author tuyrk
 */
@Data
public class Customer {
    private PriceHandler priceHandler;

    public void requestDiscount(float discount) {
        priceHandler.processDiscount(discount);
    }
}