package com.damu.controller;

import com.damu.pojo.Employee;
import com.damu.service.EmpService;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 职员控制器
 * @author 大牧
 * @version 1.0.0
 */
@RestController
@RequestMapping("/emp")
public class EmpController {

    @Autowired
    private EmpService empService;

    @GetMapping("/list")
    public List<Employee> getAllEmployee() {
        return empService.getAllEmployee();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable String id) {
        return empService.getEmplyeeById(id);
    }

    @GetMapping("/nickname/{nickname}")
    public List<Employee> getEmployeeByNickname(@PathVariable String nickname) {
//        return empService.getEmployeeByNickname(nickname);
        return empService.getEmployeeByLambda(employee->employee.getNickname().contains(nickname));
    }

    @GetMapping("/empname/{empname}")
    public List<Employee> getEmployeeByName(@PathVariable("empname") String name) {
//        return empService.getEmployeeByName(name);
        return empService.getEmployeeByLambda(employee->employee.getEmpName().contains(name));
    }
}
