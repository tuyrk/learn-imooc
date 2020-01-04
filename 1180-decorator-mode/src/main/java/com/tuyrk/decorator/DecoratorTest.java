package com.tuyrk.decorator;

/**
 * 装饰者模式测试类
 *
 * @author tuyrk
 */
public class DecoratorTest {
    public static void main(String[] args) {
        // 开始搭配
        // 创建豆浆对象
        Drink soya = new Soya();
        System.out.println(soya.money());
        System.out.println(soya.desc());
        // 向纯豆浆中加入红豆
        Drink redBeanSoya = new RedBean(soya);
        System.out.println(redBeanSoya.money());
        System.out.println(redBeanSoya.desc());
        // 向红豆豆浆中加入鸡蛋
        Drink eggRedBeanSoya = new Egg(redBeanSoya);
        System.out.println(eggRedBeanSoya.money());
        System.out.println(eggRedBeanSoya.desc());

        // 需要糖豆浆
        Drink sugarSoya = new Sugar(soya);
        System.out.println(sugarSoya.money());
        System.out.println(sugarSoya.desc());

        // 新增具体装饰-创建西瓜对象
        Watermelon watermelonSugarSoya = new Watermelon(sugarSoya);
        System.out.println(watermelonSugarSoya.money());
        System.out.println(watermelonSugarSoya.desc());

        // 新增被装饰者-搭配果汁
        Drink fruit = new Fruit();
        System.out.println(fruit.money());
        System.out.println(fruit.desc());
        Drink eggFruit = new Egg(fruit);
        System.out.println(eggFruit.money());
        System.out.println(eggFruit.desc());
    }
}
