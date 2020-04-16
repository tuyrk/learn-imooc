package com.imooc.test;

import java.util.*;
import java.util.stream.Stream;

/**
 * Copyright (C), 2018-2019, copyright info. DAMU., Ltd.
 * FileName: com.imooc.text Test1
 * <p>集合元素的常见操作</p>
 *
 * @author <a href="http://blog.csdn.net/muwenbin_flex">大牧莫邪</a>
 * @version 1.00
 */
public class Test1 {

    public static void main(String[] args) {
        // 1. 批量数据 -> Stream对象
        // 多个数据
        Stream stream = Stream.of("admin", "tom", "damu");

        // 数组
        String [] strArrays = new String[] {"xueqi", "biyao"};
        Stream stream2 = Arrays.stream(strArrays);

        // 列表
        List<String> list = new ArrayList<>();
        list.add("少林");
        list.add("武当");
        list.add("青城");
        list.add("崆峒");
        list.add("峨眉");
        Stream stream3 = list.stream();

        // 集合
        Set<String> set = new HashSet<>();
        set.add("少林罗汉拳");
        set.add("武当长拳");
        set.add("青城剑法");
        Stream stream4 = set.stream();

        // Map
        Map<String, Integer> map = new HashMap<>();
        map.put("tom", 1000);
        map.put("jerry", 1200);
        map.put("shuke", 1000);
        Stream stream5 = map.entrySet().stream();

        // 2. Stream对象对于基本数据类型的功能封装
        // int / long / double
//        IntStream.of(new int[] {10, 20, 30}).forEach(System.out::println);
//        IntStream.range(1, 5).forEach(System.out::println);
//        IntStream.rangeClosed(1, 5).forEach(System.out::println);

        // 3. Stream对象 --> 转换得到指定的数据类型
        // 数组
//        Object [] objx = stream.toArray(String[]::new);

        // 字符串
//        String str = stream.collect(Collectors.joining()).toString();
//        System.out.println(str);

        // 列表
//        List<String> listx = (List<String>) stream.collect(Collectors.toList());
//        System.out.println(listx);

        // 集合
//        Set<String> setx = (Set<String>) stream.collect(Collectors.toSet());
//        System.out.println(setx);

        // Map
//        Map<String, String> mapx = (Map<String, String>) stream.collect(Collectors.toMap(x->x, y->"value:"+y));
//        System.out.println(mapx);

        // 4. Stream中常见的API操作
        List<String> accountList = new ArrayList<>();
        accountList.add("xongjiang");
        accountList.add("lujunyi");
        accountList.add("wuyong");
        accountList.add("linchong");
        accountList.add("luzhishen");
        accountList.add("likui");
        accountList.add("wusong");

        // map() 中间操作，map()方法接收一个Functional接口
//        accountList = accountList.stream().map(x->"梁山好汉:" + x).collect(Collectors.toList());

        // filter() 添加过滤条件，过滤符合条件的用户
//        accountList = accountList.stream().filter(x-> x.length() > 5).collect(Collectors.toList());

        // forEach 增强型循环
//        accountList.forEach(x-> System.out.println("forEach->" + x));
//        accountList.forEach(x-> System.out.println("forEach->" + x));
//        accountList.forEach(x-> System.out.println("forEach->" + x));

        // peek() 中间操作，迭代数据完成数据的依次处理过程
//        accountList.stream()
//                .peek(x -> System.out.println("peek 1: " + x))
//                .peek(x -> System.out.println("peek 2:" + x))
//                .forEach(System.out::println);

//        accountList.forEach(System.out::println);

        // Stream中对于数字运算的支持
        List<Integer> intList = new ArrayList<>();
        intList.add(20);
        intList.add(19);
        intList.add(7);
        intList.add(8);
        intList.add(86);
        intList.add(11);
        intList.add(3);
        intList.add(20);

        // skip() 中间操作，有状态，跳过部分数据
//        intList.stream().skip(3).forEach(System.out::println);

        // limit() 中间操作，有状态，限制输出数据量
//        intList.stream().skip(3).limit(2).forEach(System.out::println);

        // distinct() 中间操作，有状态，剔除重复的数据
//        intList.stream().distinct().forEach(System.out::println);

        // sorted() 中间操作，有状态，排序

        // max() 获取最大值
        Optional optional = intList.stream().max((x, y)-> x-y);
        System.out.println(optional.get());
        // min() 获取最小值

        // reduce() 合并处理数据
        Optional optional2 = intList.stream().reduce((sum, x)-> sum + x);
        System.out.println(optional2.get());
    }
}

