package com.tuyrk;

import com.tuyrk.handler.PriceHandler;
import com.tuyrk.handler2.PriceHandlerFactory;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * 责任链模式
 * 测试类，客户发起折扣请求
 *
 * @author tuyrk
 */
public class Test {
    public static void main(String[] args) {
        Customer customer = new Customer();
        // customer.setPriceHandler(PriceHandler.createPriceHandler());
        customer.setPriceHandler(PriceHandlerFactory.createPriceHandler());
        Random random = new Random();
        IntStream.rangeClosed(1, 100).forEach(i -> {
            System.out.print(i + ":");
            customer.requestDiscount(random.nextFloat());
        });
    }
}
