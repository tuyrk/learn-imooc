package com.tuyrk.decorator;

/**
 * 抽象组件-饮品
 *
 * @author tuyrk
 */
public interface Drink {
    /**
     * 饮品价格
     *
     * @return 价格
     */
    double money();

    /**
     * 饮品品种描述
     *
     * @return 描述
     */
    String desc();
}
