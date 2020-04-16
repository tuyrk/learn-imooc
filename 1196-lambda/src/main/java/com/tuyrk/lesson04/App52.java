package com.tuyrk.lesson04;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

/**
 * 5-2 Lambda和Stream性能问题
 *
 * @author tuyrk
 */
public class App52 {
    public static void main(String[] args) {
        Random random = new Random(Integer.MAX_VALUE);
        // 1. 基本数据类型：整型
        List<Integer> integerList = new ArrayList<>();
        for (int i = 0; i < 1_000_000; i++) {
            integerList.add(random.nextInt());
        }

        // 1) Stream
        testStream(integerList); // 64ms
        // 2) parallelStream
        testParallelStream(integerList);// 15ms
        // 3) 普通for循环
        testForLoop(integerList); // 14ms
        // 4) 普通foreach循环
        testForEach(integerList); // 12ms
        // 5) 增强型for循环
        testStrongLoop(integerList); // 11ms
        // 6) 迭代器
        testIterator(integerList); // 11ms

        // 2. 复杂数据类型：对象
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < 1_000_000; i++) {
            productList.add(new Product("pro_" + i, i, random.nextInt()));
        }

        // 1) Stream
        testProductStream(productList); // 92ms
        // 2) parallelStream
        testProductParallelStream(productList);// 30ms
        // 3) 普通for循环
        testProductForLoop(productList); // 16ms
        // 4) 普通foreach循环
        testProductForEach(productList); // 17ms
        // 5) 增强型for循环
        testProductStrongLoop(productList); // 12ms
        // 6) 迭代器
        testProductIterator(productList); // 16ms


        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1_000_000; i++) {
            list.add(random.nextInt());
        }
        // 开始计时
        Date begin = new Date();
        // 传统处理模式
        System.out.println(commenDeal(list));
        // lambda处理模式
        /*System.out.println(lambdaDeal(list));*/

        Date end = new Date();
        System.out.println("最终用时：" + (end.getTime() - begin.getTime()));
    }

    public static void testStream(List<Integer> list) {
        long start = System.currentTimeMillis();
        Optional<Integer> max = list.stream().max(Integer::compare);
        System.out.println(max.get());
        long end = System.currentTimeMillis();
        System.out.println("testStream：" + (end - start) + "ms");
    }

    public static void testParallelStream(List<Integer> list) {
        long start = System.currentTimeMillis();
        Optional<Integer> max = list.parallelStream().max(Integer::compare);
        System.out.println(max.get());
        long end = System.currentTimeMillis();
        System.out.println("testParallelStream：" + (end - start) + "ms");
    }

    public static void testForLoop(List<Integer> list) {
        long start = System.currentTimeMillis();
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < list.size(); i++) {
            Integer current = list.get(i);
            if (current > max) {
                max = current;
            }
        }
        System.out.println(max);
        long end = System.currentTimeMillis();
        System.out.println("testForLoop：" + (end - start) + "ms");
    }

    public static void testForEach(List<Integer> list) {
        long start = System.currentTimeMillis();
        int max = Integer.MIN_VALUE;
        for (Integer current : list) {
            if (current > max) {
                max = current;
            }
        }
        System.out.println(max);
        long end = System.currentTimeMillis();
        System.out.println("testForEach：" + (end - start) + "ms");
    }

    public static void testStrongLoop(List<Integer> list) {
        long start = System.currentTimeMillis();
        final int[] max = {Integer.MIN_VALUE};
        list.forEach(current -> {
            if (current > max[0]) {
                max[0] = current;
            }
        });
        System.out.println(max[0]);
        long end = System.currentTimeMillis();
        System.out.println("testStrongLoop：" + (end - start) + "ms");
    }

    public static void testIterator(List<Integer> list) {
        long start = System.currentTimeMillis();
        int max = Integer.MIN_VALUE;
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            Integer current = it.next();
            if (current > max) {
                max = current;
            }
        }
        System.out.println(max);
        long end = System.currentTimeMillis();
        System.out.println("testIterator：" + (end - start) + "ms");
    }


    public static void testProductStream(List<Product> list) {
        long start = System.currentTimeMillis();
        Optional<Product> max = list.stream().max(Comparator.comparingInt(Product::getHot));
        System.out.println(max.get());
        long end = System.currentTimeMillis();
        System.out.println("testProductStream：" + (end - start) + "ms");
    }

    public static void testProductParallelStream(List<Product> list) {
        long start = System.currentTimeMillis();
        Optional<Product> max = list.parallelStream().max(Comparator.comparingInt(Product::getHot));
        System.out.println(max.get());
        long end = System.currentTimeMillis();
        System.out.println("testProductParallelStream：" + (end - start) + "ms");
    }

    public static void testProductForLoop(List<Product> list) {
        long start = System.currentTimeMillis();
        Product maxHot = list.get(0);
        for (int i = 0; i < list.size(); i++) {
            Product current = list.get(i);
            if (current.getHot() > maxHot.getHot()) {
                maxHot = current;
            }
        }
        System.out.println(maxHot);
        long end = System.currentTimeMillis();
        System.out.println("testProductForLoop：" + (end - start) + "ms");
    }

    public static void testProductForEach(List<Product> list) {
        long start = System.currentTimeMillis();
        Product maxHot = list.get(0);
        for (Product current : list) {
            if (current.getHot() > maxHot.getHot()) {
                maxHot = current;
            }
        }
        System.out.println(maxHot);
        long end = System.currentTimeMillis();
        System.out.println("testProductForEach：" + (end - start) + "ms");
    }

    public static void testProductStrongLoop(List<Product> list) {
        long start = System.currentTimeMillis();
        final Product[] maxHot = {list.get(0)};
        list.forEach(current -> {
            if (current.getHot() > maxHot[0].getHot()) {
                maxHot[0] = current;
            }
        });
        System.out.println(maxHot[0]);
        long end = System.currentTimeMillis();
        System.out.println("testProductStrongLoop：" + (end - start) + "ms");
    }

    public static void testProductIterator(List<Product> list) {
        long start = System.currentTimeMillis();
        Product maxHot = list.get(0);
        Iterator<Product> it = list.iterator();
        while (it.hasNext()) {
            Product current = it.next();
            if (current.getHot() > maxHot.getHot()) {
                maxHot = current;
            }
        }
        System.out.println(maxHot);
        long end = System.currentTimeMillis();
        System.out.println("testProductIterator：" + (end - start) + "ms");
    }


    /**
     * 传统处理模式
     */
    private static int commenDeal(List<Integer> list) {
        int result = Integer.MIN_VALUE;

        for (int i = 0; i < list.size(); i++) {
            result = Integer.max(result, list.get(i));
        } // 9

        /*for (Integer temp : list) {
            result = Integer.max(result, temp);
        } // 10*/

        return result;
    }

    /**
     * lambda处理模式
     */
    private static int lambdaDeal(List<Integer> list) {
        Optional<Integer> optional = list.parallelStream().reduce(Integer::max);
        return optional.get(); // 95

        /*Optional<Integer> optional = list.parallelStream().max((x, y) -> x - y);
        return optional.get(); // 120*/

        /*return list.stream().reduce(Integer.MIN_VALUE, (x, y) -> Integer.max(x, y)); // 109*/

        /*Optional<Integer> optional = list.stream().max((x, y) -> (x - y));
        return optional.get(); // 117*/
    }
}

@Data
@AllArgsConstructor
class Product {
    private String name; // 名称
    private Integer stock; //库存
    private Integer hot; //热度
}
