package com.damu.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * 数据ORM接口
 * @author 大牧
 * @version V1.0
 */
public interface BaseDAO<T> {

    /**
     * 增加对象数据
     * @param t 要增加的对象
     * @return 增加操作影响的记录数
     * @throws SQLException 可能会抛出SQLException异常
     */
    Integer add(T t) throws SQLException;

    /**
     * 根据编号删除对象数据
     * @param id 要删除的对象编号
     * @return 返回删除操作影响的记录数
     * @throws SQLException 可能会抛出SQLException异常
     */
    Integer delete(String id) throws SQLException;

    /**
     * 修改对象数据
     * @param t 更新后的对象数据
     * @return 返回更新操作影响的记录数
     * @throws SQLException 可能会抛出SQLException异常
     */
    Integer update(T t) throws SQLException;

    /**
     * 根据编号查询T对象
     * @param id 编号
     * @return 返回查询到的单个对象
     * @throws SQLException 可能会抛出SQLException异常
     */
    T findById(String id) throws SQLException;

    /**
     * 查询所有T数据
     * @return 返回查询到的T数据
     * @throws SQLException 可能会抛出SQLException异常
     */
    List<T> findAll() throws SQLException;

    /**
     * 按照指定策略查询数据
     * @param strategy 查询策略
     * @return 返回查询到符合条件的T数据
     * @throws SQLException 可能会抛出SQLException异常
     */
    List<T> findByStrategy(IStrategy strategy) throws SQLException;
}
