package com.damu.service;

import com.damu.dao.IStrategy;
import com.damu.dao.impl.EmpDAO;
import com.damu.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.SQLException;
import java.util.List;

/**
 * 职员业务受理类
 * @author 大牧
 * @version 1.0.0
 */
@Service
public class EmpService {
    @Autowired
    private EmpDAO empDAO;

    /**
     * 查询所有职员
     * @return 查询到的职员
     */
    public List<Employee> getAllEmployee() {
        List<Employee> list = null;
        try {
            list = empDAO.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 根据编号查询职员
     * @param id 职员编号
     * @return 职员数据
     */
    public Employee getEmplyeeById(String id) {
        Employee employee = null;
        try {
            employee = empDAO.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    /**
     * 增加职员数据
     * @param employee 要增加的职员数据
     * @return 增加结果
     */
    public Integer addEmplyee(Employee employee) {
        Integer id = null;
        try {
            id = empDAO.add(employee);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * 修改职员数据
     * @param employee 新的职员数据
     * @return 返回修改结果
     */
    public Integer updateEmployee(Employee employee) {
        Integer id = null;
        try {
            id = empDAO.update(employee);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * 删除职员数据
     * @param id 要删除的职员编号
     * @return 返回删除结果
     */
    public Integer deleteEmployee(String id) {
        Integer did = null;
        try {
            did = empDAO.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return did;
    }

    /**
     * 根据昵称获取职员数据
     * @param nickname 职员昵称
     * @return 返回职员数据
     */
    public List<Employee> getEmployeeByNickname(String nickname) {
        List<Employee> list = null;
        try {
            list = empDAO.findByStrategy(new IStrategy<Employee>() {
                @Override
                public Boolean test(Employee employee) {
                    return employee.getNickname().contains(nickname);
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Employee> getEmployeeByName(String name) {
        List<Employee> list = null;
        try {
            list = empDAO.findByStrategy(new IStrategy<Employee>() {
                @Override
                public Boolean test(Employee employee) {
                    return employee.getEmpName().contains(name);
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 按照条件查询职员数据的方法
     * @param strategy 条件策略
     * @return 符合条件的职员数据
     */
    public List<Employee> getEmployeeByLambda(IStrategy<Employee> strategy) {
        List<Employee> list = null;
        try {
            list = empDAO.findByStrategy(strategy);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
