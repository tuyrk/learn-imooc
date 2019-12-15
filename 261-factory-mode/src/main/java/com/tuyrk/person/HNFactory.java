package com.tuyrk.person;

import com.tuyrk.person.impl.HNBoy;
import com.tuyrk.person.impl.HNGirl;

/**
 * 新年系列工厂
 *
 * @author tuyrk
 */
public class HNFactory implements PersonFactory {
    @Override
    public Boy getBoy() {
        return new HNBoy();
    }

    @Override
    public Girl getGirl() {
        return new HNGirl();
    }
}
