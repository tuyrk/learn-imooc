package com.tuyrk.jdk11;

import java.lang.reflect.Field;

/**
 * 3-1 基于嵌套的访问控制
 *
 * @author tuyrk
 */
public class NestAccessExample {
    private static class X {
        void test() throws Exception {
            Y y = new Y();
            y.y = 1;

            // Exception in thread "main" java.lang.IllegalAccessException: Class com.tuyrk.jdk11.NestAccessExample$X can not access a member of class com.tuyrk.jdk11.NestAccessExample$Y with modifiers "private"
            Field field = Y.class.getDeclaredField("y");
            // field.setAccessible(true);
            field.setInt(y, 2);
        }
    }

    private static class Y {
        private int y;
    }

    public static void main(String[] args) throws Exception {
        new X().test();
    }
}
