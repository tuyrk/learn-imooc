package com.tuyrk.lesson02;

/**
 * 3-9 Lmabda表达式底层构建原理
 *
 * @author tuyrk
 */
public class App39 {
    public static void main(String[] args) {
        IMarkUp mu = (message) -> System.out.println(message);
        mu.markUp("Lambda!");
    }
}

interface IMarkUp {
    void markUp(String msg);
}

