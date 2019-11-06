package com.tuyrk.project3;

import java.io.*;
import java.util.Scanner;

/**
 * User: 涂元坤
 * Mail: 766564616@qq.com
 * Create: 2018/12/29 15:01 星期六
 * Description:
 * 一共有两个线程，一个主线程，一个守护线程。
 * 守护线程会在很长的时间内不停的往文件中写数据，主线程会阻塞等待来自键盘的输入。
 * 一旦主线程获取到了用户的输入，这时候，阻塞就会解除掉，主线程继续运行，直到结束。
 * 而一旦主线程结束，用户线程就没有了。这时候即使数据还没有写完，守护线程也会随虚拟机一起结束运行。
 */
class DaemonThread implements Runnable {
    @Override
    public void run() {
        System.out.println("进入守护线程" + Thread.currentThread().getName());
        try {
            write2File();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("退出守护线程" + Thread.currentThread().getName());
    }

    private void write2File() throws Exception {
        File filename = new File("312-thread-runnable\\src\\main\\resources\\daemon.txt");
        OutputStream outputStream = new FileOutputStream(filename, true);
        int count = 0;
        while (count < 10) {
            outputStream.write(("word" + count + "\r\n").getBytes());
            System.out.println("守护线程" + Thread.currentThread().getName() + "向文件中写入了word" + count++);
            Thread.sleep(100);
        }
    }
}

public class DaemonThreadDemo {
    public static void main(String[] args) {
        System.out.println("进入主线程" + Thread.currentThread().getName());
        DaemonThread daemonThread = new DaemonThread();
        Thread thread = new Thread(daemonThread);
        thread.setDaemon(true);//设置为守护线程
        thread.start();
        Scanner scanner = new Scanner(System.in);
        scanner.next();
        // 主线程为唯一用户线程
        // thread为守护线程
        // 主线程退出导致数据的不完整性
        System.out.println("退出主线程" + Thread.currentThread().getName());
    }
}
