package com.tuyrk.person;

/**
 * 人物的实现接口
 *
 * @author tuyrk
 */
public interface PersonFactory {
    /**
     * 男孩接口
     *
     * @return 男孩实例
     */
    Boy getBoy();

    /**
     * 女孩接口
     *
     * @return 女孩实例
     */
    Girl getGirl();
}
