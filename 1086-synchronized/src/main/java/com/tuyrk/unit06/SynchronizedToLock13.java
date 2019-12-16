package com.tuyrk.unit06;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * synchronized锁与lock锁
 */
public class SynchronizedToLock13 {
    public static void main(String[] args) {
        SynchronizedToLock13 s = new SynchronizedToLock13();
        s.method1();
        s.method2();
    }

    Lock lock = new ReentrantLock();

    public synchronized void method1() {
        System.out.println("我是Synchronized形式的锁。");
    }

    public void method2() {
        lock.lock();
        try {
            System.out.println("我是lock形式的锁。");
        } finally {
            lock.unlock();
        }
    }
}
