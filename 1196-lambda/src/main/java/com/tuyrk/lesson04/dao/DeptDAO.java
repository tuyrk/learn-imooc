package com.tuyrk.lesson04.dao;

import com.tuyrk.lesson04.entity.Department;
import com.tuyrk.lesson04.mode.IStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tuyrk
 */
public class DeptDAO implements BaseDAO<Department> {
    private static List<Department> LIST = new ArrayList<Department>() {
        {
            add(new Department(10L, "中央", "梁山本部"));
            add(new Department(20L, "近卫", "梁山本部"));
            add(new Department(21L, "军委1部", "梁山本部"));
            add(new Department(22L, "军委2部", "梁山本部"));
            add(new Department(23L, "军委3部", "梁山本部"));
            add(new Department(30L, "财务部", "梁山本部"));
            add(new Department(40L, "参谋部", "梁山本部"));
            add(new Department(50L, "后勤部", "梁山本部"));
            add(new Department(60L, "军情部", "梁山本部"));
            add(new Department(70L, "迎宾部", "梁山本部"));
            add(new Department(80L, "刑罚部", "梁山本部村"));
        }
    };

    @Override
    public boolean add(Department department) {
        return LIST.add(department);
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public boolean update(Department department) {
        return false;
    }

    @Override
    public Department findById(String id) {
        return LIST.stream().filter(x -> x.getDeptNo().equals(Long.parseLong(id))).findAny().orElse(null);
    }

    @Override
    public List<Department> findAll() {
        return LIST;
    }

    @Override
    public List<Department> findByStrategy(IStrategy strategy) {
        List<Department> tempList = new ArrayList<>();
        for (Department department : LIST) {
            if (strategy.test(department)) {
                tempList.add(department);
            }
        }
        return tempList;
    }
}
