package com.damu.service;

import com.damu.dao.IStrategy;
import com.damu.dao.impl.DeptDAO;
import com.damu.pojo.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * 部门业务受理类
 * @author 大牧
 * @version 1.0.0
 */
@Service
public class DeptService {
    @Autowired
    private DeptDAO deptDAO;
    private List<Department> list;

    /**
     * 查询所有部门数据
     * @return 返回所有的部门数据
     */
    public List<Department> getAllDepartment() {
        try {
            list = deptDAO.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 根据部门名称查询部门
     */
    public List<Department> getDepartmentByName(String name) {
        try {
            list = deptDAO.findByStrategy(new IStrategy<Department>() {

                @Override
                public Boolean test(Department department) {
                    return department.getDeptName().contains(name);
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 根据地区获取部门
     * @param location 地区名称
     * @return 查询到的部门
     */
    public List<Department> getDepartmentByLocation(String location) {
        try {
            list = deptDAO.findByStrategy(new IStrategy<Department>() {

                @Override
                public Boolean test(Department department) {
                    return department.getLocation().contains(location);
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
