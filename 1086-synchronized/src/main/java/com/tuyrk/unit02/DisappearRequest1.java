package com.tuyrk.unit02;

import java.util.concurrent.locks.Lock;

/**
 * 消失的请求数
 */
public class DisappearRequest1 implements Runnable {
    private static DisappearRequest1 instance = new DisappearRequest1();

    private static int a = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("a = " + a);
    }

    @Override
    public void run() {
        method();
    }

    public void method() {
        for (int i = 0; i < 100000; i++) {
            a++;
        }
    }
}
