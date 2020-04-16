package com.tuyrk.lesson02;

/**
 * 3-5 Lambda表达式基本语法
 *
 * @author tuyrk
 */
public class App35 {
    public static void main(String[] args) {
        // 1. 没有参数，没有返回值的Lambda表达式
        ILambda1 i1 = () -> {
            System.out.println("hello lambda");
            System.out.println("welcome to lambda");
        };
        i1.test();
        ILambda1 i12 = () -> System.out.println("hello");
        i12.test();

        // 2. 带有参数，没有返回值的Lambda表达式
        ILambda2 i2 = (String name, int age) -> {
            System.out.println(name + " age: my year's old is " + age);
        };
        i2.test("jerry", 18);
        ILambda2 i22 = (name, age) -> {
            System.out.println(name + " age: my year's old is " + age);
        };
        i22.test("tom", 22);

        // 3. 带有参数，带有返回值的Lambda表达式
        ILambda3 i3 = (x, y) -> {
            int z = x + y;
            return z;
        };
        System.out.println(i3.test(11, 22));
        ILambda3 i32 = (x, y) -> x + y;
        System.out.println(i32.test(100, 200));
        ILambda3 i33 = Integer::sum;
        System.out.println(i33.test(23, 32));
    }

    interface ILambda1 {
        void test();
    }

    interface ILambda2 {
        void test(String name, int age);
    }

    interface ILambda3 {
        int test(int x, int y);
    }
}
