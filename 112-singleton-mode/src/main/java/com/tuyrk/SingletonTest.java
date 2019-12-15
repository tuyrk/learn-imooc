package com.tuyrk;

/**
 * 测试Singleton.java
 *
 * @author tuyrk
 */
public class SingletonTest {
    public static void main(String[] args) {
        // 饿汉模式
        Singleton s1 = Singleton.getInstance();
        Singleton s2 = Singleton.getInstance();
        System.out.println(s1 == s2);// true
        if (s1 == s2) {
            System.out.println("s1和s2是同一个实例");
        } else {
            System.out.println("s1和s2不是同一个实例");
        }

        // 懒汉模式
        Singleton2 s3 = Singleton2.getInstance();
        Singleton2 s4 = Singleton2.getInstance();
        System.out.println(s3 == s4);// true
        if (s3 == s4) {
            System.out.println("s3和s4是同一个实例");
        } else {
            System.out.println("s3和s4不是同一个实例");
        }
    }
}
