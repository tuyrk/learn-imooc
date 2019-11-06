package com.tuyrk._3_project;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2018/12/28 17:11 星期五
 * Description:
 */
public class WrongWayStopThread extends Thread {
    public static void main(String[] args) throws InterruptedException {
        WrongWayStopThread thread = new WrongWayStopThread();
        System.out.println("Starting Thread...");
        thread.start();

        Thread.sleep(3000);

        System.out.println("Interrupting Thread...");
        thread.interrupt();

        Thread.sleep(3000);
        System.out.println("Stopping Application...");
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {//while(true)将不会结束，处于无休止循环之中。
            //while(!this.isInterrupted()) 实质还是使用了状态标识的方法
            System.out.println("Thread is Running");

//            long time = System.currentTimeMillis();
//            while (System.currentTimeMillis() - time < 1000) {
//                //减少屏幕输出的空循环
//            }

            //线程不会结束，并且会抛出一个InterruptedException异常
            try {
                Thread.sleep(1000);//调用.sleep()进入了阻塞方法，
                // 然后再调用.interrupt()会产生两个结果：
                // 1）中断状态被清除，.isInterrupted()方法不会返回线程状态
                // 2）抛出InterruptedException异常
            } catch (InterruptedException e) {
                e.printStackTrace();
                this.interrupt();//在catch中再次调用interrupt()就OK了，
                // 此时线程会线程会结束，但还是会抛出异常。
            }
        }
    }
}
