package com.tuyrk.unit03;

/**
 * 对象锁示例1，代码块形式
 */
public class SynchronizedObjectCodeBlock2 implements Runnable {
    private static SynchronizedObjectCodeBlock2 instance = new SynchronizedObjectCodeBlock2();

    private Object lock1 = new Object();
    private Object lock2 = new Object();

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
        synchronized (lock1) {
            System.out.println("我是对象锁的代码块形式-lock1。我叫" + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "，lock1-运行结束。");
        }

        synchronized (lock2) {
            System.out.println("我是对象锁的代码块形式-lock2。我叫" + Thread.currentThread().getName());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "，lock2-运行结束。");
        }
    }
}
