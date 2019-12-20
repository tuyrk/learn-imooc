package com.tuyrk.inherit;

import com.tuyrk.polymerize.GbTwoPlug;
import com.tuyrk.polymerize.ThreePlug;

/**
 * 二相转三相的插座适配器-继承方式
 *
 * @author tuyrk
 */
public class TwoPlugAdapterExtends extends GbTwoPlug implements ThreePlug {
    @Override
    public void powerWithThree() {
        System.out.println("通过转换-继承方式");
        this.powerWithTwo();
    }
}