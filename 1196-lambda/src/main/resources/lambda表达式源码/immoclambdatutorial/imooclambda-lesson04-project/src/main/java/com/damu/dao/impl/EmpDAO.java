package com.damu.dao.impl;

import com.damu.dao.BaseDAO;
import com.damu.dao.IStrategy;
import com.damu.dao.mapper.DeptMapper;
import com.damu.dao.mapper.EmpMapper;
import com.damu.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmpDAO implements BaseDAO<Employee> {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EmpMapper mapper;
    private List<Employee> list;

    @Override
    public Integer add(Employee employee) throws SQLException {
        return jdbcTemplate.update("insert into employee(empname, nickname, job, mgr, hirdate, salary, comm, deptno)" +
                "values(?,?,?,?,?,?,?,?)", employee.getEmpName(), employee.getNickname(), employee.getJob(), employee.getMgr(),
                employee.getHirdate(), employee.getSalary(), employee.getComm(), employee.getDept().getDeptNo());
    }

    @Override
    public Integer delete(String id) throws SQLException {
        return jdbcTemplate.update("delete from employee where empno = ?", id);
    }

    @Override
    public Integer update(Employee employee) throws SQLException {
        return jdbcTemplate.update("update employee set empname = ?, nickname = ?, job = ?, mgr = ?," +
                "hirdate = ?, comm = ?, salary = ?, deptno = ? where empno = ?",
                employee.getEmpName(), employee.getNickname(), employee.getJob(), employee.getMgr(),
                employee.getHirdate(), employee.getComm(), employee.getSalary(), employee.getDept().getDeptNo(), employee.getEmpNo());
    }

    @Override
    public Employee findById(String id) throws SQLException {
        return jdbcTemplate.queryForObject("select * from employee where empno = ?", mapper, id);
    }

    @Override
    public List<Employee> findAll() throws SQLException {
        return jdbcTemplate.query("select * from employee", mapper);
    }

    @Override
    public List<Employee> findByStrategy(IStrategy strategy) throws SQLException {
        list = this.findAll();

        List<Employee> tempList = new ArrayList<>();
        for (Employee employee : list) {
            if (strategy.test(employee)) {
                tempList.add(employee);
            }
        }

        return tempList;
    }
}
