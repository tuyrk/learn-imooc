package com.tuyrk.lesson04;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 5-3 线程安全问题
 *
 * @author tuyrk
 */
public class App53 {
    public static void main(String[] args) {
        // 整数列表
        List<Integer> list1 = new ArrayList<>();
        // 增加数据
        for (int i = 0; i < 1000; i++) {
            list1.add(i);
        }
        System.out.println(list1.size());

        // 串行Stream
        List<Integer> list2 = new ArrayList<>();
        list1.forEach(list2::add);
        System.out.println(list2.size());

        // 并行Stream，线程不安全
        List<Integer> list3 = new ArrayList<>();
        list1.parallelStream().forEach(list3::add);
        System.out.println(list3.size());

        // 解决并行Stream线程安全问题
        List<Integer> list4 = list1.parallelStream().collect(Collectors.toList());
        System.out.println(list4.size());
    }
}
