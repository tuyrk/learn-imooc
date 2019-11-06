package com.tuyrk._2_Actor;

/**
 * 隋唐演义大戏舞台
 */
public class Stage extends Thread {

    @Override
    public void run() {
        System.out.println("欢迎观看隋唐演义");
        //让观众们安静片刻，等待大戏上演
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        System.out.println("大幕徐徐拉开");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        System.out.println("话说隋朝末年，隋军与农民起义军杀得昏天黑地...");

        ArmyRunnable armyTaskOfSuiDynasty = new ArmyRunnable();//隋朝军队
        ArmyRunnable armyTaskOfRevolt = new ArmyRunnable();//农民起义军队
        //使用Runnable接口创建线程
        Thread armyOfSuiDynasty = new Thread(armyTaskOfSuiDynasty, "隋军");
        Thread armyOfRevolt = new Thread(armyTaskOfRevolt, "农民起义军");

        //启动线程，让军队开始作战
        armyOfSuiDynasty.start();
        armyOfRevolt.start();

        //舞台线程休眠，大家专心观看军队厮杀
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {//中断异常
            e.printStackTrace();
        }

        System.out.println("正当双方激战正酣，半路杀出了个程咬金");
        Thread mrCheng = new KeyPersonThread("程咬金");
        System.out.println("程咬金的理想就是结束战争，使百姓安居乐业！");

        //停止军队作战
        //停止线程的方法
        armyTaskOfSuiDynasty.keepRunning = false;
        armyTaskOfRevolt.keepRunning = false;
//        // 不正确的停止线程的方法
//        armyOfSuiDynasty.stop();
//        armyOfRevolt.stop();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //历史大戏留给关键人物
        mrCheng.start();

        //万众瞩目，所有线程等待程先生完成历史使命
        try {
            mrCheng.join();//使其他线程等待，直到当前线程终止。指明其他其他线程必须等待当前结束后才能运行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("战争结束，人民安居乐业，程先生实现了积极的人生梦想，为人民作出了贡献！");
        System.out.println("谢谢观看隋唐演义，再见！");
    }

    public static void main(String[] args) {
        new Stage().start();
    }
}