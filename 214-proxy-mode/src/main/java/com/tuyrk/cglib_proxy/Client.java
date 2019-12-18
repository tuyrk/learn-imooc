package com.tuyrk.cglib_proxy;

/**
 * CGLIB代理的测试类
 *
 * @author tuyrk
 */
public class Client {
    public static void main(String[] args) {
        CglibProxy cglibProxy = new CglibProxy();
        Train t = (Train) cglibProxy.getProxy(Train.class);
        t.move();
        Plain p = (Plain) cglibProxy.getProxy(Plain.class);
        p.move();
    }
}
