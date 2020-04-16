package com.tuyrk.lesson01;

/**
 * 2-1 为什么引入Lambda表达式
 *
 * @author tuyrk
 */
public class Demo01 {
    public static void main(String[] args) {
        // 1. 传统模式下，新线程的创建
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("threading..." + Thread.currentThread().getId());
            }
        }).start();
        // 2. JDK8新特性，Lambda表达式优化线程模式
        new Thread(() -> {
            System.out.println("lambda threading..." + Thread.currentThread().getId());
        }).start();
    }
}
