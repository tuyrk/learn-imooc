package com.tuyrk.lesson04.controller;

import com.tuyrk.lesson04.dao.EmpDAO;
import com.tuyrk.lesson04.entity.Employee;
import com.tuyrk.lesson04.service.EmpService;

import java.util.List;

/**
 * 职员控制器
 *
 * @author tuyrk
 */
public class EmpController {
    private EmpService empService = new EmpService();

    public List<Employee> getAllEmployee() {
        return empService.getAllEmployee();
    }

    public Employee getEmployeeById(String id) {
        return empService.getEmplyeeById(id);
    }

    public List<Employee> getEmployeeByNickname(String nickname) {
        return empService.getEmployeeByNickname(nickname);
        // return empService.getEmployeeByLambda(employee -> employee.getNickname().contains(nickname));
    }

    public List<Employee> getEmployeeByName(String name) {
        // return empService.getEmployeeByName(name);
        return empService.getEmployeeByLambda(employee -> employee.getEmpName().contains(name));
    }
}
