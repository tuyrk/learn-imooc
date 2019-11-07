package com.tuyrk;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
volatile不能保证原子性
理论来讲，最后的值应该是500，但是因为num++;不是原子操作，且volatile关键字又没有原子性，所以偶尔会出现<500的情况。
 */
public class VolatileDemo {

    private Lock lock = new ReentrantLock();
    private /*volatile*/ int number = 0;
//    private AtomicInteger num = new AtomicInteger(0);

    public int getNumber() {
        return this.number;
    }

    public /*synchronized*/ void increase() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.lock();
        try {
//            synchronized (this) {
            this.number++;
//            }
//            num.getAndAdd(1);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final VolatileDemo volDemo = new VolatileDemo();
        for (int i = 0; i < 500; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    volDemo.increase();
                }
            }).start();
        }

        // 如果还有子线程在运行，主线程就让出CPU资源，
        // 直到所有的子线程都运行完了，主线程再继续往下执行
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println("number : " + volDemo.getNumber());
    }
}