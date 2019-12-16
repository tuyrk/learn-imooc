package com.tuyrk.unit05;

/**
 * 可重入粒度测试：调用类内另外的方法
 */
public class SynchronizedOtherMethod11 {
    public static void main(String[] args) {
        SynchronizedOtherMethod11 synchronizedOtherMethod11 = new SynchronizedOtherMethod11();
        synchronizedOtherMethod11.method1();
    }

    public synchronized void method1() {
        System.out.println("I'm method1");
        method2();
    }

    private synchronized void method2() {
        System.out.println("I'm method2");
    }
}
