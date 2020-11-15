package com.tuyrk.mybatisplus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 公用Mapper
 *
 * @author tuyuankun
 */
public interface CommMapper<T> extends BaseMapper<T> {
    /**
     * 删除所有数据
     *
     * @return 影响行数
     */
    int deleteAll();


    /**
     * 批量新增数据,自选字段 insert(排除逻辑删除字段)
     *
     * @param entityList 批量插入的数据集合
     * @return 影响行数
     */
    int insertBatchSomeColumn(List<T> entityList);

    /**
     * 根据 id 逻辑删除数据,并带字段填充功能
     *
     * @param entity 删除实体
     * @return 影响行数
     */
    int deleteByIdWithFill(T entity);

    /**
     * 根据 ID 更新固定的那几个字段(但是不包含逻辑删除)
     *
     * @param entity 更新实体
     * @return 影响行数
     */
    int alwaysUpdateSomeColumnById(@Param(Constants.ENTITY) T entity);
}
