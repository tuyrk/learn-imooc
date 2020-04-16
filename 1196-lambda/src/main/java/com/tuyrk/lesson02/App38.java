package com.tuyrk.lesson02;

/**
 * 3-8 方法重载和Lambda表达式
 * 方法重载对于Lambda表达式的影响
 *
 * @author tuyrk
 */
public class App38 {
    interface Param1 {
        void outInfo(String info);
    }

    interface Param2 {
        void outInfo(String info);
    }

    // 定义重载方法
    public void lambdaMethod(Param1 param) {
        param.outInfo("hello param1 lambda");
    }

    public void lambdaMethod(Param2 param) {
        param.outInfo("hello param2 lambda");
    }

    public static void main(String[] args) {
        App38 app38 = new App38();
        app38.lambdaMethod(new Param1() {
            @Override
            public void outInfo(String info) {
                System.out.println(info);
            }
        });
        app38.lambdaMethod(new Param2() {
            @Override
            public void outInfo(String info) {
                System.out.println(info);
            }
        });

        app38.lambdaMethod((Param2) (info) -> {
            System.out.println(info);
        });
        app38.lambdaMethod((Param2) System.out::println);
    }
}
