package com.tuyrk.lesson04.service;

import com.tuyrk.lesson04.dao.DeptDAO;
import com.tuyrk.lesson04.entity.Department;
import com.tuyrk.lesson04.mode.IStrategy;

import java.util.List;

/**
 * 部门业务受理类
 *
 * @author tuyrk
 */
public class DeptService {
    private DeptDAO deptDAO = new DeptDAO();

    /**
     * 查询所有部门数据
     *
     * @return 返回所有的部门数据
     */
    public List<Department> getAllDepartment() {
        return deptDAO.findAll();
    }

    /**
     * 根据部门名称查询部门
     */
    public List<Department> getDepartmentByName(String name) {
        return deptDAO.findByStrategy(new IStrategy<Department>() {

            @Override
            public boolean test(Department department) {
                return department.getDeptName().contains(name);
            }
        });
    }

    /**
     * 根据地区获取部门
     *
     * @param location 地区名称
     * @return 查询到的部门
     */
    public List<Department> getDepartmentByLocation(String location) {
        return deptDAO.findByStrategy(new IStrategy<Department>() {
            @Override
            public boolean test(Department department) {
                return department.getLocation().contains(location);
            }
        });
    }
}
