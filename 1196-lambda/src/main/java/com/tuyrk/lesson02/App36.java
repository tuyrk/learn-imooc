package com.tuyrk.lesson02;

/**
 * 3-6 变量捕获-变量的访问操作
 *
 * @author tuyrk
 */
public class App36 {
    String s1 = "全局变量";

    /**
     * 1. 匿名内部类型中对于变量的访问
     */
    public void testInnerClass() {
        String s2 = "局部变量";

        new Thread(new Runnable() {
            String s3 = "内部变量";

            @Override
            public void run() {
                // 1. 访问全局变量
                // System.out.println(this.s1); // this关键字~表示的是当前内部类型的对象
                System.out.println(s1);

                // 2. 局部变量的访问
                System.out.println(s2);
                // s2 = "hello"; // 不能对局部变量进行数据的修改[final]

                // 3. 内部变量的访问
                System.out.println(s3);
                System.out.println(this.s3);
            }
        }).start();
    }

    /**
     * 2. Lambda表达式变量捕获
     */
    public void testLambda() {
        String s2 = "局部变量Lambda";
        new Thread(() -> {
            String s3 = "内部变量Lambda";

            // 1. 访问全局变量
            System.out.println(this.s1); // this关键字~表示的是所属方法所在类型的对象

            // 2. 访问局部变量
            System.out.println(s2);
            // s2 = "hello"; // 不能进行数据修改，默认推导变量的修饰符：final

            // 3. 访问内部变量
            System.out.println(s3);
            s3 = "lambda内部变量直接修改";
            System.out.println(s3);
        }).start();
    }

    public static void main(String[] args) {
        App36 app36 = new App36();
        app36.testInnerClass();
        app36.testLambda();
    }
}
