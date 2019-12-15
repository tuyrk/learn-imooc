package com.tuyrk.hair;

/**
 * 模拟客户端实现
 *
 * @author tuyrk
 */
public class Test {
    public static void main(String[] args) {
        // 每需要创建一个发型都需要重新new，并且调用draw()
        /*HairInterface left = new LeftHair();
        left.draw();// 左偏分发型
        HairInterface right = new RightHair();
        right.draw();// 右偏分发型*/

        // if-else实现
        /*HairFactory factory = new HairFactory();
        HairInterface left = factory.getHair("left");
        left.draw();// 左偏分发型*/

        // className实现
        /*HairFactory factory = new HairFactory();
         *//*HairInterface left = factory.getHairByClass("com.tuyrk.hair.impl.LeftHair");*//*
        HairInterface left = factory.getHairByClass(LeftHair.class.getName());
        left.draw();// 左偏分发型*/

        // 读取properties实现
        HairFactory factory = new HairFactory();
        HairInterface hair = factory.getHairByClassKey("in");
        hair.draw();// 中分发型

    }
}
