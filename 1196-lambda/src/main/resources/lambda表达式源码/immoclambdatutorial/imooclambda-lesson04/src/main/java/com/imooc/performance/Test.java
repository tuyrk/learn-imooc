package com.imooc.performance;


import java.util.*;

public class Test {

    public static void main(String[] args) {

        Random random = new Random();
        // 1. 基本数据类型：整数
//        List<Integer> integerList = new ArrayList<Integer>();
//
//        for(int i = 0; i < 1000000; i++) {
//            integerList.add(random.nextInt(Integer.MAX_VALUE));
//        }
//
//        // 1) stream
//        testStream(integerList);
//        // 2) parallelStream
//        testParallelStream(integerList);
//        // 3) 普通for
//        testForloop(integerList);
//        // 4) 增强型for
//        testStrongForloop(integerList);
//        // 5) 迭代器
//        testIterator(integerList);

        // 2. 复杂数据类型：对象
        List<Product> productList = new ArrayList<>();
        for(int i = 0; i < 1000000; i++) {
            productList.add(new Product("pro" + i, i, random.nextInt(Integer.MAX_VALUE)));
        }

        // 调用执行
        testProductStream(productList);
        testProductParallelStream(productList);
        testProductForloop(productList);
        testProductStrongForloop(productList);
        testProductIterator(productList);

    }

    public static void testStream(List<Integer> list) {
        long start = System.currentTimeMillis();

        Optional optional = list.stream().max(Integer::compare);
        System.out.println(optional.get());

        long end = System.currentTimeMillis();
        System.out.println("testStream:" + (end - start) + "ms");
    }

    public static void testParallelStream(List<Integer> list) {
        long start = System.currentTimeMillis();

        Optional optional = list.parallelStream().max(Integer::compare);
        System.out.println(optional.get());

        long end = System.currentTimeMillis();
        System.out.println("testParallelStream:" + (end - start) + "ms");
    }

    public static void testForloop(List<Integer> list) {
        long start = System.currentTimeMillis();

        int max = Integer.MIN_VALUE;
        for(int i = 0; i < list.size(); i++) {
            int current = list.get(i);
            if (current > max) {
                max = current;
            }
        }
        System.out.println(max);

        long end = System.currentTimeMillis();
        System.out.println("testForloop:" + (end - start) + "ms");
    }

    public static void testStrongForloop(List<Integer> list) {
        long start = System.currentTimeMillis();

        int max = Integer.MIN_VALUE;
        for (Integer integer : list) {
            if(integer > max) {
                max = integer;
            }
        }
        System.out.println(max);

        long end = System.currentTimeMillis();
        System.out.println("testStrongForloop:" + (end - start) + "ms");
    }

    public static void testIterator(List<Integer> list) {
        long start = System.currentTimeMillis();

        Iterator<Integer> it = list.iterator();
        int max = it.next();

        while(it.hasNext()) {
            int current = it.next();
            if(current > max) {
                max = current;
            }
        }
        System.out.println(max);

        long end = System.currentTimeMillis();
        System.out.println("testIterator:" + (end - start) + "ms");
    }


    public static void testProductStream(List<Product> list) {
        long start = System.currentTimeMillis();

        Optional optional = list.stream().max((p1, p2)-> p1.hot - p2.hot);
        System.out.println(optional.get());

        long end = System.currentTimeMillis();
        System.out.println("testProductStream:" + (end - start) + "ms");
    }

    public static void testProductParallelStream(List<Product> list) {
        long start = System.currentTimeMillis();

        Optional optional = list.stream().max((p1, p2)-> p1.hot - p2.hot);
        System.out.println(optional.get());

        long end = System.currentTimeMillis();
        System.out.println("testProductParallelStream:" + (end - start) + "ms");
    }

    public static void testProductForloop(List<Product> list) {
        long start = System.currentTimeMillis();

        Product maxHot = list.get(0);
        for(int i = 0; i < list.size(); i++) {
            Product current = list.get(i);
            if (current.hot > maxHot.hot) {
                maxHot = current;
            }
        }
        System.out.println(maxHot);

        long end = System.currentTimeMillis();
        System.out.println("testProductForloop:" + (end - start) + "ms");
    }

    public static void testProductStrongForloop(List<Product> list) {
        long start = System.currentTimeMillis();

        Product maxHot = list.get(0);
        for (Product product : list) {
            if(product.hot > maxHot.hot) {
                maxHot = product;
            }
        }
        System.out.println(maxHot);

        long end = System.currentTimeMillis();
        System.out.println("testProductStrongForloop:" + (end - start) + "ms");
    }

    public static void testProductIterator(List<Product> list) {
        long start = System.currentTimeMillis();

        Iterator<Product> it = list.iterator();
        Product maxHot = it.next();

        while(it.hasNext()) {
            Product current = it.next();
            if (current.hot > maxHot.hot) {
                maxHot = current;
            }
        }
        System.out.println(maxHot);

        long end = System.currentTimeMillis();
        System.out.println("testProductIterator:" + (end - start) + "ms");
    }

}

class Product {
    String name;    // 名称
    Integer stock;  // 库存
    Integer hot;    // 热度

    public Product(String name, Integer stock, Integer hot) {
        this.name = name;
        this.stock = stock;
        this.hot = hot;
    }
}