package com.damu.dao.mapper;

import com.damu.pojo.Department;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DeptMapper implements RowMapper<Department> {
    @Override
    public Department mapRow(ResultSet resultSet, int i) throws SQLException {
        Department dept = new Department();
        dept.setDeptNo(resultSet.getLong("deptno"));
        dept.setDeptName(resultSet.getString("deptName"));
        dept.setLocation(resultSet.getString("location"));
        return dept;
    }
}
