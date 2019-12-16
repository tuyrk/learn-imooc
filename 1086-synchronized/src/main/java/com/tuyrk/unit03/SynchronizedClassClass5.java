package com.tuyrk.unit03;

/**
 * 类锁示例2，*.class形式
 */
public class SynchronizedClassClass5 implements Runnable {
    private static SynchronizedClassClass5 instance1 = new SynchronizedClassClass5();
    private static SynchronizedClassClass5 instance2 = new SynchronizedClassClass5();

    public static void main(String[] args) {
        Thread t1 = new Thread(instance1);
        Thread t2 = new Thread(instance2);
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

    public void method() throws InterruptedException {
        synchronized (SynchronizedClassClass5.class) {
            System.out.println("我是类锁的 synchronized(*.class) 形式，我叫" + Thread.currentThread().getName());
            Thread.sleep(3000);
            System.out.println(Thread.currentThread().getName() + "，运行结束");
        }
    }
}
