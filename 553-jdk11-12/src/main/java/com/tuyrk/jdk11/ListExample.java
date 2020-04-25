package com.tuyrk.jdk11;

import java.util.List;

/**
 * 3-10 JDK11 中新增加的常用 API
 * java.util.List
 *
 * @author tuyrk
 */
public class ListExample {
    public static void main(String[] args) {
        List<String> list = List.of("java8", "java11", "java12");
        System.out.println(list);
        // list.clear(); // List不能被更改

        // 创建String数组
        // 旧的方法
        String[] oldWay = list.toArray(new String[0]);
        // 新的方法
        String[] newWay = list.toArray(String[]::new);
    }
}
