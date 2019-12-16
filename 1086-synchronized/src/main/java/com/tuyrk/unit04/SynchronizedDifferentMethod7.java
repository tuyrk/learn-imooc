package com.tuyrk.unit04;

/**
 * 同时访问一个类的不同的普通同步方法
 */
public class SynchronizedDifferentMethod7 implements Runnable {
    private static SynchronizedDifferentMethod7 instance = new SynchronizedDifferentMethod7();

    public static void main(String[] args) {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        while (t1.isAlive() || t2.isAlive()) {
        }
        System.out.println("finished");
    }

    @Override
    public void run() {
        try {
            method1();
            method2();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void method1() throws InterruptedException {
        System.out.println("我是加锁的方法-1。我叫" + Thread.currentThread().getName());
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getName() + "运行结束");
    }

    public synchronized void method2() throws InterruptedException {
        System.out.println("我是加锁的方法-2。我叫" + Thread.currentThread().getName());
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getName() + "运行结束");
    }
}
