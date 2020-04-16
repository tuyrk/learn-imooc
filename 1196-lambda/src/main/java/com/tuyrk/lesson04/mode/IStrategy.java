package com.tuyrk.lesson04.mode;

/**
 * ORM查询策略
 *
 * @author tuyrk
 */
@FunctionalInterface
public interface IStrategy<T> {
    /**
     * 测试方法
     *
     * @param t 要测试的对象
     * @return 返回测试结果
     */
    boolean test(T t);
}
