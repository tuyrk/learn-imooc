package com.tuyrk.unit04;

/**
 * 同时访问静态synchronized和非静态synchronized方法
 */
public class SynchronizedStaticAndNormal8 implements Runnable {
    private static SynchronizedStaticAndNormal8 instance = new SynchronizedStaticAndNormal8();

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

    public synchronized static void method1() throws InterruptedException {
        System.out.println("我是静态synchronized方法。我叫" + Thread.currentThread().getName());
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getName() + "运行结束");
    }

    public synchronized void method2() throws InterruptedException {
        System.out.println("我是非静态synchronized方法。我叫" + Thread.currentThread().getName());
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getName() + "运行结束");
    }
}
