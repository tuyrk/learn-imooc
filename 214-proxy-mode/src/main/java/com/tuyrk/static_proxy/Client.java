package com.tuyrk.static_proxy;

/**
 * 静态代理测试类
 *
 * @author tuyrk
 */
public class Client {
    public static void main(String[] args) {
        // 1. 在执行逻辑代码前后添加信息
        /*Moveable car1 = new Car();
        car1.move();*/

        // 2. 使用继承方式实现静态代理
        /*Moveable car2 = new Car2();
        car2.move();*/

        // 3. 使用聚合方式实现静态代理
        Car car = new Car();
        Moveable car3 = new Car3(car);
        car3.move();
    }
}
