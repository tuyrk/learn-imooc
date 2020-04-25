package com.tuyrk.jdk11;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * methodhandler相较于反射更轻量级：
 * <p>
 * 1.本质上讲两个都是在模拟方法的调用，但Reflection是模拟java代码层次的调用，
 * 而MethodHandle是模拟的字节码层次的，上面的例子中是模拟的invokevirtual指令。
 * <p>
 * 2.Reflection是重量级的，而MethodHandle是轻量级的。
 * <p>
 * 最重要的是，Reflection 只为java服务，
 * 而MethodHandle则可以服务于所有的运行在java虚拟机上的语言。
 *
 * @author tuyrk
 */
public class MethodHandlerTest {
    private static class A {
        public void println(String s) {
            System.out.println("i am class A: " + s);
        }
    }

    private static MethodHandle getPrintMh(Object target) throws Exception {
        MethodType methodType = MethodType.methodType(void.class, String.class);
        return MethodHandles.lookup().findVirtual(target.getClass(), "println", methodType).bindTo(target);
    }

    public static void main(String[] args) throws Throwable {
        Object object = System.currentTimeMillis() % 2 == 0 ? System.out : new A();
        getPrintMh(object).invokeExact("hello");
    }
}
