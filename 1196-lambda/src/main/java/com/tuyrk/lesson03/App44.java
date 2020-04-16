package com.tuyrk.lesson03;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 4-4 Stream操作集合中的数据-上
 * - 类型转换：其他类型（创建/获取） => Stream对象
 * - 类型转换：Stream对象 => 其他类型
 *
 * @author tuyrk
 */
public class App44 {
    public static void main(String[] args) {
        // 1. 批量数据 => Stream对象
        // 多个数据
        Stream<String> stream1 = Stream.of("admin", "tom", "damu");
        // 数组
        String[] strArrays = {"xueqi", "biyao"};
        Stream<String> stream2 = Arrays.stream(strArrays);
        // 列表
        List<String> list = new ArrayList<>();
        list.add("少林");
        list.add("武当");
        list.add("青城");
        list.add("崆峒");
        list.add("峨眉");
        Stream<String> stream3 = list.stream();
        // 集合
        Set<String> set = new HashSet<>();
        set.add("少林罗汉拳");
        set.add("武当长拳");
        set.add("青城剑法");
        Stream<String> stream4 = set.stream();
        // Map
        Map<String, Integer> map = new HashMap<>();
        map.put("tom", 1000);
        map.put("jerry", 1200);
        map.put("shuke", 1000);
        Stream<Map.Entry<String, Integer>> stream5 = map.entrySet().stream();

        // 2. Stream对象对于基本数据类型的功能封装
        // int/long/double
        IntStream.of(new int[]{10, 20, 30}).forEach(System.out::println);
        IntStream.of(10, 20, 30).forEach(System.out::println);
        IntStream.range(1, 5).forEach(System.out::println);
        IntStream.rangeClosed(1, 5).forEachOrdered(System.out::println);

        // 3. Stream对象 => 转换得到指定的数据类型
        // 数组
        /*String[] array1 = stream1.toArray(String[]::new);
        System.out.println("array1 = " + Arrays.toString(array1)); // [admin, tom, damu]*/
        // 字符串
        /*String str1 = stream1.collect(Collectors.joining());
        System.out.println("str1 = " + str1); // admintomdamu*/
        /*String str2 = stream1.collect(Collectors.joining(" "));
        System.out.println("str2 = " + str2); // admin tom damu*/
        /*String str3 = stream1.collect(Collectors.joining(",", "^", "$"));
        System.out.println("str3 = " + str3); // ^admin,tom,damu$*/
        // 列表
        /*List<String> lists = stream1.collect(Collectors.toList());
        System.out.println("lists = " + lists); // [admin, tom, damu]*/
        // 集合
        /*Set<String> sets = stream1.collect(Collectors.toSet());
        System.out.println("sets = " + sets); // [tom, admin, damu]*/
        // Map
        /*Map<String, String> maps = stream1.collect(Collectors.toMap(x -> x, y -> "value:" + y));
        System.out.println("maps = " + maps); // {tom=value:tom, admin=value:admin, damu=value:damu}*/
    }
}
