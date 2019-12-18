package com.tuyrk.cglib_proxy;

import com.tuyrk.static_proxy.Moveable;

/**
 * 火车
 *
 * @author tuyrk
 */
public class Train implements Moveable {
    @Override
    public void move() {
        System.out.println("火车🚄行驶中");
    }
}
