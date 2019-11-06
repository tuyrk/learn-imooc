package com.tuyrk.project2;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2018/12/29 14:02 星期六
 * Description:
 */
class MyRunnable implements Runnable {
    private int ticketCount = 5;

    @Override
    public void run() {
        while (ticketCount > 0) {
            ticketCount--; // 如果还有票就卖掉一张
            System.out.println(Thread.currentThread().getName() + "卖了一张票,剩余票数为：" + ticketCount);
        }
    }
}

public class TicketsRunnable {
    public static void main(String[] args) {
        MyRunnable mr = new MyRunnable();

//        MyRunnable mr1 = new MyRunnable();
//        MyRunnable mr2 = new MyRunnable();
//        MyRunnable mr3 = new MyRunnable();

        // 创建三个线程，模拟三个售票窗口
        Thread th1 = new Thread(mr, "窗口1");
        Thread th2 = new Thread(mr, "窗口2");
        Thread th3 = new Thread(mr, "窗口3");
        // 注意：th1,th2,th3虽然是堆中的三个不同对象，但是此时的mt是一个，所以三个线程引用mt中的资源也是共有的

        // 启动这三个线程，也即是三个窗口开始卖票
        th1.start();
        th2.start();
        th3.start();
    }
}

/*
Runnable可以实现资源共享，而Thread资源独立
*/