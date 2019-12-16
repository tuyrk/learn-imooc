package com.tuyrk.unit04;

/**
 * 方法抛出异常后，会释放锁。
 * 展示不抛出异常和抛出异常后的对比：一旦抛出了异常，第二个线程会立刻进入同步方法，意味着锁已经释放。
 */
public class SynchronizedException9 implements Runnable {
    private static SynchronizedException9 instance = new SynchronizedException9();

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
        System.out.println("我是抛出异常的同步方法。我叫" + Thread.currentThread().getName());
        Thread.sleep(3000);
        // throw new Exception();
        // throw new RuntimeException();
        int a = 1 / 0;// 抛出异常
        System.out.println(Thread.currentThread().getName() + "运行结束");
    }

    public synchronized void method2() throws InterruptedException {
        System.out.println("我是普通的同步方法。我叫" + Thread.currentThread().getName());
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getName() + "运行结束");
    }
}
