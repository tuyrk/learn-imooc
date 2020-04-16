package com.imooc.performance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Test2 {

    public static void main(String[] args) {
        // 整数列表
        List<Integer> lists = new ArrayList<Integer>();
        // 增加数据
        for (int i = 0; i < 1000; i++){
            lists.add(i);
        }

        // 串行Stream
        List<Integer> list2 = new ArrayList<>();
        lists.stream().forEach(x->list2.add(x));
        System.out.println(lists.size());
        System.out.println(list2.size());
        // 并行Stream
        List<Integer> list3 = new ArrayList<>();
        lists.parallelStream().forEach(x-> list3.add(x));
        System.out.println(list3.size());
        //
        List<Integer> list4 = lists.parallelStream().collect(Collectors.toList());
        System.out.println(list4.size());
    }
}
