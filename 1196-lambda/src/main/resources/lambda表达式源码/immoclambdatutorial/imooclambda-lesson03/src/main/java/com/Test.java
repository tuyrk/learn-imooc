package com;

import java.util.*;

/**
 * Copyright (C), 2018-2019, copyright info. DAMU., Ltd.
 * FileName: com Test
 * <p>TODO</p>
 *
 * @author <a href="http://blog.csdn.net/muwenbin_flex">大牧莫邪</a>
 * @version 1.00
 */
public class Test {
    public static void main(String[] args) {



    }

    public static Integer commenDeal(List<Integer> list) {
        Integer result = Integer.MIN_VALUE;

//        for(int i = 0; i < list.size(); i++) {
//            result = Integer.max(result, list.get(i));
//        } // 44

        for (Integer temp : list) {
            result = Integer.max(result, temp);
        } // 46

        return result;
    }

    public static Integer lambdaDeal(List<Integer> list) {

//        Optional<Integer> optional = list.parallelStream().reduce(Integer::max);
//        return optional.get(); // 152

//        Optional<Integer> optional = list.parallelStream().max((x, y) -> x - y);
//        return optional.get(); // 120

        return list.stream().reduce(Integer.MIN_VALUE, (x, y) -> Integer.max(x, y)); // 109

//        Optional<Integer> optional = list.stream().max((x, y)->(x - y));
//        return optional.get(); // 117
    }
}
