package com.tuyrk.lesson03;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author tuyrk
 */
public class AppTest {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        // 1. map(Function(T, R)-> R) 接受一个参数，通过运算得到转换后的数据
        List<Double> collect = list.stream().map(x -> Math.pow(x, 2)).collect(Collectors.toList());
        System.out.println("collect = " + collect);

        Integer[] nums = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        // 2. filter(Predicate(T t)->Boolean) 接受一个参数，验证参数是否符合设置的条件
        Integer[] filter = Arrays.stream(nums).filter(x -> x % 2 == 0).toArray(Integer[]::new);
        System.out.println("filter = " + Arrays.toString(filter));

        // 3. forEach: 接受一个lambda表达式，在Stream每个元素上执行指定的操作
        list.stream().filter(x -> x % 2 == 0).forEach(System.out::print);

        List<Integer> numList = new ArrayList<>();
        numList.add(1);
        numList.add(3);
        numList.add(2);
        numList.add(5);
        numList.add(4);
        numList.add(6);
        // reduce
        Optional<Integer> sum1 = numList.stream().reduce((x, y) -> x + y);
        System.out.println("sum1 = " + sum1.get());
        Optional<Integer> sum2 = numList.stream().reduce(Integer::sum);
        System.out.println("sum2 = " + sum2.get());
        // limit
        List<Integer> limit1 = numList.stream().limit(2).collect(Collectors.toList());
        System.out.println("limit1 = " + limit1);
        // skip
        List<Integer> skip1 = numList.stream().skip(2).collect(Collectors.toList());
        System.out.println("skip1 = " + skip1);
        // sorted().一般在skip/limit或者filter之后进行
        List<Integer> sorted1 = numList.stream().skip(2).limit(5).sorted().collect(Collectors.toList());
        System.out.println("sorted1 = " + sorted1);
        // min/max/distinct
        Integer min1 = numList.stream().min((x, y) -> {
            return x - y;
        }).get();
        Integer min2 = numList.stream().min(Comparator.comparingInt(x -> x)).get();
        System.out.println("min1 = " + min1);
        System.out.println("min2 = " + min2);
        Integer max = numList.stream().max((x, y) -> x - y).get();
        System.out.println("max = " + max);
    }
}
