package com.tuyrk.unit07;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 展示Lock的方法
 */
public class LockExample15 {
    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        lock.lock();
        lock.unlock();

        boolean tryLock1 = lock.tryLock();
        boolean tryLock2 = lock.tryLock(10, TimeUnit.SECONDS);

        Condition condition = lock.newCondition();
    }
}
