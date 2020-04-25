package com.tuyrk.jdk11;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * 3-2 动态类文件常量
 *
 * @author tuyrk
 */
public class DynamicTest {
    private static void test() {
        System.out.println("hello");
    }

    public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodHandle mh = lookup.findStatic(DynamicTest.class, "test", MethodType.methodType(void.class));
        mh.invokeExact();
    }
}
