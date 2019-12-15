package com.tuyrk.person;

import com.tuyrk.person.impl.MCBoy;
import com.tuyrk.person.impl.MCGirl;

/**
 * 圣诞系列工厂
 *
 * @author tuyrk
 */
public class MCFactory implements PersonFactory {
    @Override
    public Boy getBoy() {
        return new MCBoy();
    }

    @Override
    public Girl getGirl() {
        return new MCGirl();
    }
}
