package com.tuyrk.jdk11;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 3-5 Lambda 参数的本地变量语法
 *
 * @author tuyrk
 */
public class LocalVarExample {
    /**
     * Java8特性：lambda表达式语法
     */
    private static void lambdaInJava8() {
        // 参数列表，->，函数体
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("before java8");
            }
        }).start();

        new Thread(() -> System.out.println("in java8")).start();

        List<String> list = Arrays.asList("java8", "jdk8", "1.8");
        list.forEach(w -> {
            System.out.println("lambda in java8: " + w);
        });
    }

    /**
     * Java10新特性，局部变量类型推断
     */
    private static void varInJava10() {
        int var = 10;
        var i = 10; // int
        var str = "java10"; // String
        var list = new ArrayList<String>(); // ArrayList<String>
        var map = Map.of(1, "a", 2, "b"); // Map<Integer, String>

        for (var entry : map.entrySet()) {
            System.out.println(entry);
        }

        // i = "abc"; // 和JavaScript中的var不同，不能赋值其他类型
        // var a; // 必须初始化赋值

    }

    class ErrorUseVar {
        // JDK10只能允许在局部变量中使用var
        // var i = 10;
        /*var f1(var str) {
            return 10;
        }*/
    }

    /**
     * Java11新特性：Lambda表达式可以使用var来标识变量
     */
    private static void lambdaWithVarInJava11() {
        List<Integer> nums = Arrays.asList(8, 7, 9);
        nums.sort((@NotNull Integer s1, @NotNull Integer s2) -> {
            if (s1.equals(s2)) {
                return 0;
            }
            return s1 > s2 ? 1 : -1;
        });
        // 可以给lambda表达式添加注解
        nums.sort((@NotNull var s1, @NotNull var s2) -> {
            if (s1.equals(s2)) {
                return 0;
            }
            return s1 > s2 ? 1 : -1;
        });
        nums.sort((s1, s2) -> {
            if (s1.equals(s2)) {
                return 0;
            }
            return s1 > s2 ? 1 : -1;
        });
        System.out.println(nums);
        // (var x, y) -> x.process(y)         // Cannot mix 'var' and 'no var' in implicitly typed lambda expression
        // (var x, int y) -> x.process(y)     // Cannot mix 'var' and manifest types in explicitly typed lambda expression
        // var x -> x.foo();
    }

    public static void main(String[] args) {
        lambdaInJava8();
        varInJava10();
        lambdaWithVarInJava11();
    }
}
