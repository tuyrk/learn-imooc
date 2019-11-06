package com.tuyrk.project2;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2018/12/29 13:52 星期六
 * Description:
 */
class MyThread extends Thread {
    // 一共有5张火车票
    private static int ticketCount = 5;
    // 窗口，即类的名称
    private String name;

    private final Object object = new Object();

    public MyThread(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        while (ticketCount > 0) {
//            // 加锁可以解决资源共享的问题。
//            synchronized (object) {
            ticketCount--; // 如果还有票就卖掉一张
            System.out.println(name + "卖了一张票,剩余票数为：" + ticketCount);
//            }
        }
    }
}


public class TicketsThread {
    public static void main(String[] args) {
        // 创建三个线程，模拟三个窗口卖票
        MyThread mt1 = new MyThread("窗口1");
        MyThread mt2 = new MyThread("窗口2");
        MyThread mt3 = new MyThread("窗口3");
        // 注意：此时mt1, mt2, mt3是堆内存中实例出的三个对象，对于它们所有属性也独立的

        // 启动三个线程，也即是窗口开始卖票
        mt1.start();
        mt2.start();
        mt3.start();
    }
}
