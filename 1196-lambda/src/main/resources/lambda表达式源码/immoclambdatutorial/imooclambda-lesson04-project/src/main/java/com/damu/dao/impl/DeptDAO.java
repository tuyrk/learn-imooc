package com.damu.dao.impl;

import com.damu.dao.BaseDAO;
import com.damu.dao.IStrategy;
import com.damu.dao.mapper.DeptMapper;
import com.damu.pojo.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DeptDAO implements BaseDAO<Department> {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DeptMapper mapper;
    private List<Department> list;

    @Override
    public Integer add(Department department) throws SQLException {
        return jdbcTemplate.update("insert into department(deptname, location) values(?, ?)",
                department.getDeptName(), department.getLocation());
    }

    @Override
    public Integer delete(String id) throws SQLException {
        return jdbcTemplate.update("delete from department where deptno = ?", id);
    }

    @Override
    public Integer update(Department department) throws SQLException {
        return jdbcTemplate.update("update department set deptname = ?, location = ? where deptno = ?",
                department.getDeptName(),
                department.getLocation(),
                department.getDeptNo());
    }

    @Override
    public Department findById(String id) throws SQLException {
        return jdbcTemplate.queryForObject("select * from department where deptno = ?", mapper, id);
    }

    @Override
    public List<Department> findAll() throws SQLException {
        return jdbcTemplate.query("select * from department", mapper);
    }

    @Override
    public List<Department> findByStrategy(IStrategy strategy) throws SQLException {
        List<Department> tempList = new ArrayList<>();
        list = this.findAll();
        for (Department department : list) {
            if (strategy.test(department)) {
                tempList.add(department);
            }
        }
        return tempList;
    }
}
