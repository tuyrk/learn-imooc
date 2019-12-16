package com.tuyrk.background;

/**
 * 创建100个线程，用活动监视器的CPU栏目查看Java进程的线程数量的变化，10秒后线程消失了
 */
public class Create100Threads {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
