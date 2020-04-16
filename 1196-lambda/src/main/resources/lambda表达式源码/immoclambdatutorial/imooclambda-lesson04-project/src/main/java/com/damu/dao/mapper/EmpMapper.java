package com.damu.dao.mapper;

import com.damu.dao.impl.DeptDAO;
import com.damu.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EmpMapper implements RowMapper<Employee> {
    @Autowired
    private DeptDAO deptDAO;

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Employee employee = new Employee();
        employee.setEmpNo(rs.getLong("empno"));
        employee.setEmpName(rs.getString("empname"));
        employee.setNickname(rs.getString("nickname"));
        employee.setJob(rs.getString("job"));
        employee.setMgr(rs.getLong("mgr"));
        employee.setComm(rs.getInt("comm"));
        employee.setHirdate(rs.getDate("hirdate"));
        employee.setSalary(rs.getInt("salary"));
        employee.setDept(deptDAO.findById(rs.getString("deptno")));
        return employee;
    }
}
