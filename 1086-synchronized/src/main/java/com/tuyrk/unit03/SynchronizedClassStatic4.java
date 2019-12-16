package com.tuyrk.unit03;

/**
 * 类锁示例1，静态方法形式
 */
public class SynchronizedClassStatic4 implements Runnable {
    private static SynchronizedClassStatic4 instance1 = new SynchronizedClassStatic4();
    private static SynchronizedClassStatic4 instance2 = new SynchronizedClassStatic4();

    public static void main(String[] args) {
        Thread t1 = new Thread(instance1);
        Thread t2 = new Thread(instance1);
        t1.start();
        t2.start();
        while (t1.isAlive() || t2.isAlive()) {
        }
        System.out.println("finished");
    }

    @Override
    public void run() {
        try {
            method();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void method() throws InterruptedException {
        System.out.println("我是类锁的静态方法形式，我叫" + Thread.currentThread().getName());
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getName() + "，运行结束");
    }
}
