package com.tuyrk.unit04;

/**
 * 同时访问同步方法和非同步方法
 */
public class SynchronizedYesAndNo6 implements Runnable {

    private static SynchronizedYesAndNo6 instance = new SynchronizedYesAndNo6();

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
            if ("Thread-0".equals(Thread.currentThread().getName())) {
                method1();
            } else if ("Thread-1".equals(Thread.currentThread().getName())) {
                method2();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void method1() throws InterruptedException {
        System.out.println("我是加锁的方法。我叫" + Thread.currentThread().getName());
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getName() + "运行结束");
    }

    public void method2() throws InterruptedException {
        System.out.println("我是非加锁的方法。我叫" + Thread.currentThread().getName());
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getName() + "运行结束");
    }
}
