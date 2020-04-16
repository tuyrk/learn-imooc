package com.tuyrk.lesson04.controller;

import com.tuyrk.lesson04.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;


public class EmpControllerTest {

    private EmpController empController;

    @BeforeEach
    public void init() {
        empController = new EmpController();
    }

    @Test
    public void getAllEmployee() {
        List<Employee> result = empController.getAllEmployee();
        System.out.println(result);
    }

    @Test
    public void getEmployeeById() {
        Employee result = empController.getEmployeeById("1");
        System.out.println(result);
    }

    @Test
    public void getEmployeeByNickname() {
        List<Employee> result = empController.getEmployeeByNickname("天");
        result.forEach(System.out::println);
    }

    @Test
    public void getEmployeeByName() {
        List<Employee> result = empController.getEmployeeByName("李");
        result.forEach(System.out::println);
    }
}
