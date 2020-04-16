package com.tuyrk.lesson02;

import java.util.ArrayList;
import java.util.List;

/**
 * 3-7 Lambda表达式类型检查
 *
 * @author tuyrk
 */
public class App37 {
    public static void test(MyInterface<String, List> inter) {
        List list = inter.strategy("hello", new ArrayList());
        System.out.println(list);
    }

    public static void main(String[] args) {
        // 1. 匿名内部类
        test(new MyInterface<String, List>() {
            @Override
            public List strategy(String s, List list) {
                list.add(s);
                return list;
            }
        });

        // 2. Lambda表达式
        test((x, y) -> {
            y.add(x);
            return y;
        });
    }
}

@FunctionalInterface
interface MyInterface<T, R> {
    /**
     * 策略
     */
    R strategy(T t, R r);
}
