package com.tuyrk.unit05;

/**
 * 可重入粒度测试：调用父类的方法
 */
public class SynchronizedSuperClass12 {
    public synchronized void doSomething() {
        System.out.println("我是父类方法");
    }
}

class TestClass extends SynchronizedSuperClass12 {
    public static void main(String[] args) {
        TestClass t = new TestClass();
        t.doSomething();
    }

    @Override
    public synchronized void doSomething() {
        System.out.println("我是子类方法");
        super.doSomething();
    }
}