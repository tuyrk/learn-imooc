package com.tuyrk.lesson04.dao;

import com.tuyrk.lesson04.mode.IStrategy;

import java.util.List;

/**
 * 数据ORM接口
 *
 * @author tuyrk
 */
public interface BaseDAO<T> {
    /**
     * 增加对象数据
     *
     * @param t 要增加的对象
     * @return 操作是否成功
     */
    boolean add(T t);

    /**
     * 根据编号删除对象数据
     *
     * @param id 要删除的对象编号
     * @return 操作是否成功
     */
    boolean delete(String id);

    /**
     * 修改对象数据
     *
     * @param t 更新后的对象数据
     * @return 操作是否成功
     */
    boolean update(T t);

    /**
     * 根据编号查询T对象
     *
     * @param id 编号
     * @return 返回查询到的单个对象
     */
    T findById(String id);

    /**
     * 查询所有T数据
     *
     * @return 返回查询到的T数据
     */
    List<T> findAll();

    /**
     * 按照指定策略查询数据
     *
     * @param strategy 查询策略
     * @return 返回查询到符合条件的T数据
     */
    List<T> findByStrategy(IStrategy strategy);
}
