package com.tuyrk.jdk11;

import java.util.concurrent.TimeUnit;

/**
 * 3-3 改进 Aarch64 函数
 *
 * @author tuyrk
 */
public class Aarch64Example {
    public static void main(String[] args) {
        mathOnJdk11();
    }

    private static void mathOnJdk11() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 10_000_000; i++) {
            Math.sin(i);
            Math.cos(i);
            Math.log(i);
        }
        long endTime = System.currentTimeMillis();
        System.out.println(TimeUnit.NANOSECONDS.toNanos(endTime - startTime) + "ms");
    }
}
