package com.imooc.mapper;

import com.imooc.pojo.Dept;
import com.imooc.util.DeptStrategy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Copyright (C), 2018-2019, copyright info. DAMU., Ltd.
 * FileName: com.imooc.mapper DeptMapper
 * <p>TODO</p>
 *
 * @author <a href="http://blog.csdn.net/muwenbin_flex">大牧莫邪</a>
 * @version 1.00
 */
@Mapper
public interface DeptMapper {


    /**
     * 根据编号查询部门
     * @param id 部门编号
     * @return 查询到的部门数据
     */
//    @Results({
//            @Result(property = "deptNo", column = "deptNo"),
//            @Result(property = "deptName", column = "dname"),
//            @Result(property = "location", column = "loc")
//    })
//    @Select("select * from dept where deptNo = #{id}")
    @Select("select deptno as deptno, dname as deptname, loc as location from dept where deptNo = #{id}")
    Dept findById(String id);

    /**
     * 查询所有部门
     * @return 返回系统中所有部门数据
     */
    @Select("select deptno as deptno, dname as deptname, loc as location from dept")
    List<Dept> findAll();

    /**
     * 根据指定策略查询部门数据
     * @param strategy 策略条件
     * @return 返回查询到的部门数据
     */
    List<Dept> findByStrategy(DeptStrategy strategy);

    /**
     * 增加一个新部门
     * @param dept 要增加的部门数据
     * @return 影响的记录数
     */
    Integer add(Dept dept);

    /**
     * 修改部门数据
     * @param newDept 新的部门数据
     * @return 影响的记录数
     */
    Integer update(Dept newDept);

    /**
     * 删除部门数据
     * @param id 部门编号
     * @return 影响的记录数
     */
    Integer delete(String id);
}
