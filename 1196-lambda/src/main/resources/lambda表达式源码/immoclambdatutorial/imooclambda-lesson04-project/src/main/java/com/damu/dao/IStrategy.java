package com.damu.dao;

/**
 * ORM查询策略
 */
public interface IStrategy<T> {
    /**
     * 测试方法
     * @param t 要测试的对象
     * @return 返回测试结果
     */
    Boolean test(T t);
}
