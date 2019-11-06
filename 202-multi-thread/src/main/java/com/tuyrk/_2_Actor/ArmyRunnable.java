package com.tuyrk._2_Actor;

//军队线程
//模拟作战双方的行为
public class ArmyRunnable implements Runnable {

    //volatile保证了线程可以正确的读取其他线程写入的值
    //可见性 ref JMM， happens-before原则
    volatile boolean keepRunning = true;

    @Override
    public void run() {
        while (keepRunning) {
            // 发动5连击
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "进攻对方[" + i + "]");
                // 让出了处理器时间，下次该谁进攻还不一定呢！
                Thread.yield();//使当前的线程可以让出处理器，使所有的线程同时去竞争CPU资源，从而获取运行的机会
            }
        }
        System.out.println(Thread.currentThread().getName() + "结束了战斗！");
    }
}
