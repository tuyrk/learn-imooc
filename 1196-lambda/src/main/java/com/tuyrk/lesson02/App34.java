package com.tuyrk.lesson02;

import java.util.UUID;
import java.util.function.*;

/**
 * 3-4 jdk中常见的函数式接口
 * java.util.function提供了大量的函数式接口
 * Predicate 接收参数T对象，返回一个boolean类型结果
 * Consumer 接收参数T对象，没有返回值
 * Function 接收参数T对象，返回R对象
 * Supplier 不接受任何参数，直接通过get()获取指定类型的对象
 * UnaryOperator 接口参数T对象，执行业务处理后，返回更新后的T对象
 * BinaryOperator 接口接收两个T对象，执行业务处理后，返回一个T对象
 *
 * @author tuyrk
 */
public class App34 {
    public static void main(String[] args) {
        // 1. 接收参数对象T，返回一个boolean类型结果
        Predicate<String> pre = (String username) -> {
            return "admin".equals(username);
        };
        System.out.println(pre.test("manager"));
        System.out.println(pre.test("admin"));

        // 2. 接收参数对象T，不返回结果
        Consumer<String> con = (String message) -> {
            System.out.println("要发送的消息：" + message);
            System.out.println("消息发送完成");
        };
        con.accept("hello world");
        con.accept("lambda expression");

        // 3. 接收参数对象T，返回结果对象R
        Function<String, Integer> fun = (String gender) -> {
            return "male".equals(gender) ? 1 : 0;
        };
        System.out.println(fun.apply("male"));
        System.out.println(fun.apply("female"));

        // 4. 不需要接收参数，提供T对象的创建工厂
        Supplier<String> sup = () -> {
            return UUID.randomUUID().toString();
        };
        System.out.println(sup.get());
        System.out.println(sup.get());
        System.out.println(sup.get());

        // 5. 接收参数对象T，返回结果对象T
        UnaryOperator<String> uo = (String img) -> {
            img += "[100x200]";
            return img;
        };
        System.out.println(uo.apply("原图--"));

        // 6. 接收两个T对象，返回一个T对象结果
        BinaryOperator<Integer> bo = (Integer i1, Integer i2) -> {
            return i1 > i2 ? i1 : i2;
        };
        System.out.println(bo.apply(12, 13));
    }
}
