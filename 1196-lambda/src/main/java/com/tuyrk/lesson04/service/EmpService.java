package com.tuyrk.lesson04.service;

import com.tuyrk.lesson04.dao.EmpDAO;
import com.tuyrk.lesson04.entity.Employee;
import com.tuyrk.lesson04.mode.IStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 职员业务受理类
 *
 * @author tuyrk
 */
public class EmpService {
    private EmpDAO empDAO = new EmpDAO();

    /**
     * 查询所有职员
     *
     * @return 查询到的职员
     */
    public List<Employee> getAllEmployee() {
        return empDAO.findAll();
    }

    /**
     * 根据编号查询职员
     *
     * @param id 职员编号
     * @return 职员数据
     */
    public Employee getEmplyeeById(String id) {
        return empDAO.findById(id);
    }

    /**
     * 增加职员数据
     *
     * @param employee 要增加的职员数据
     * @return 增加结果
     */
    public boolean addEmplyee(Employee employee) {
        return empDAO.add(employee);
    }

    /**
     * 修改职员数据
     *
     * @param employee 新的职员数据
     * @return 返回修改结果
     */
    public boolean updateEmployee(Employee employee) {
        return empDAO.update(employee);
    }

    /**
     * 删除职员数据
     *
     * @param id 要删除的职员编号
     * @return 返回删除结果
     */
    public boolean deleteEmployee(String id) {
        return empDAO.delete(id);
    }

    /**
     * 根据昵称获取职员数据
     *
     * @param nickname 职员昵称
     * @return 返回职员数据
     */
    public List<Employee> getEmployeeByNickname(String nickname) {
        return empDAO.findByStrategy(new IStrategy<Employee>() {
            @Override
            public boolean test(Employee employee) {
                return employee.getNickname().contains(nickname);
            }
        });
    }

    public List<Employee> getEmployeeByName(String name) {
        return empDAO.findByStrategy((IStrategy<Employee>) employee -> employee.getEmpName().contains(name));
    }

    /**
     * 按照条件查询职员数据的方法
     *
     * @param strategy 条件策略
     * @return 符合条件的职员数据
     */
    public List<Employee> getEmployeeByLambda(IStrategy<Employee> strategy) {
        return empDAO.findByStrategy(strategy);
    }
}
