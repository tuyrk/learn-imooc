package com.tuyrk.lesson03;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 4-4 Stream操作集合中的数据-上
 * - Stream常见的API操作
 *
 * @author tuyrk
 */
public class App45 {
    public static void main(String[] args) {
        // 4. Stream常见的API操作
        List<String> accountList = new ArrayList<>();
        accountList.add("songjiang");
        accountList.add("lujunyi");
        accountList.add("wuyong");
        accountList.add("linchong");
        accountList.add("luzhishen");
        accountList.add("likui");
        accountList.add("wusong");
        // map() 中间操作，map()方法接收一个Functional接口
        accountList = accountList.stream().map(x -> "梁山好汉：" + x).collect(Collectors.toList()); // 在每个名字前添加"梁山好汉"
        // filter() 添加过滤条件，过滤符合条件的用户
        accountList = accountList.stream().filter(x -> x.length() > 5).collect(Collectors.toList()); // 过滤名字长度大于5的名字
        // forEach增强型循环
        accountList.forEach(x -> System.out.println("forEach:" + x));
        // accountList.forEach(x -> System.out.println("forEach:" + x));
        // accountList.forEach(x -> System.out.println("forEach:" + x));
        // peek()中间操作，迭代数据完成数据的依次处理过程
        accountList.stream()
                .peek(x -> System.out.println("peek 1:" + x))
                .peek(x -> System.out.println("peek 2:" + x))
                .forEach(x -> System.out.println("forEach end:" + x));
        accountList.forEach(System.out::println);

        // 5. Stream中对于数字运算的支持
        List<Integer> intList = new ArrayList<>();
        intList.add(20);
        intList.add(19);
        intList.add(7);
        intList.add(8);
        intList.add(86);
        intList.add(11);
        intList.add(3);
        intList.add(20);
        // skip()中间操作，有状态，跳过部分数据
        intList.stream().skip(3).forEach(System.out::println);
        // limit()中间操作，有状态，限制输出数据量
        intList.stream().skip(3).limit(2).forEach(System.out::println);
        // distinct()中间操作，有状态，剔除重复的数据
        intList.stream().distinct().forEach(System.out::println);
        // sorted()中间操作，有状态，排序
        // max()获取最大值
        // Optional<Integer> max = intList.stream().max((x, y) -> x - y);
        Optional<Integer> max = intList.stream().max(Comparator.comparingInt(x -> x));
        System.out.println(max.get());
        // min()获取最小值
        // reduce()合并处理数据
        // Optional<Integer> reduce = intList.stream().reduce((sum, x) -> sum + x);
        Optional<Integer> reduce = intList.stream().reduce(Integer::sum);
        System.out.println(reduce.get());

    }
}
