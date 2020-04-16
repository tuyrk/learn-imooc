package com.tuyrk.lesson04.controller;

import com.tuyrk.lesson04.entity.Department;
import com.tuyrk.lesson04.service.DeptService;

import java.util.List;

/**
 * @author tuyrk
 */
public class DeptController {
    private DeptService deptService = new DeptService();

    public String hello() {
        return "hello imooc!";
    }

    public List<Department> getAllDepartment() {
        return deptService.getAllDepartment();
    }

    public List<Department> getDepartmentByName(String dname) {
        return deptService.getDepartmentByName(dname);
    }

    public List<Department> getDepartmentByLocation(String location) {
        return deptService.getDepartmentByLocation(location);
    }
}
