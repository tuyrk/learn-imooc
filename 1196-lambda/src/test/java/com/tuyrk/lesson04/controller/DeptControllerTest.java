package com.tuyrk.lesson04.controller;

import com.tuyrk.lesson04.entity.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


public class DeptControllerTest {

    private DeptController deptController;

    @BeforeEach
    public void init() {
        deptController = new DeptController();
    }


    @Test
    public void hello() {
        String result = deptController.hello();
        System.out.println(result);
    }

    @Test
    public void getAllDepartment() {
        List<Department> result = deptController.getAllDepartment();
        result.forEach(System.out::println);
    }

    @Test
    public void getDepartmentByName() {
        List<Department> result = deptController.getDepartmentByName("军");
        result.forEach(System.out::println);
    }

    @Test
    public void getDepartmentByLocation() {
        List<Department> result = deptController.getDepartmentByLocation("村");
        result.forEach(System.out::println);
    }
}
