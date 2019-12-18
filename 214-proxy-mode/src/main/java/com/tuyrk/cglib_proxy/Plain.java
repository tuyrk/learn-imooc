package com.tuyrk.cglib_proxy;

import com.tuyrk.static_proxy.Moveable;

/**
 * 飞机
 *
 * @author tuyrk
 */
public class Plain implements Moveable {
    @Override
    public void move() {
        System.out.println("飞机✈行驶中");
    }
}
