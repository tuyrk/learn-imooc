package com.tuyrk.mybatisplus.dao;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tuyrk.mybatisplus.ApplicationTests;
import com.tuyrk.mybatisplus.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LogicDeleteMapperTest extends ApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void deleteById() {
        int rows = userMapper.deleteById(1094592041087729666L);
        System.out.println("影响行数：" + rows);
    }

    @Test
    public void selectList() {
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }

    @Test
    public void updateById() {
        User user = new User();
        user.setAge(24);
        user.setId(1088248166370832385L);
        int rows = userMapper.updateById(user);
        System.out.println("影响行数：" + rows);
    }

    @Test
    public void mySelectList() {
        List<User> userList = userMapper.mySelectList(
                Wrappers.<User>lambdaQuery().gt(User::getAge, 25).eq(User::getDeleted, 0)
        );
        userList.forEach(System.out::println);
    }

    /**
     * 测试过滤特定方法SQL
     * 多租户特定方法过滤`@InterceptorIgnore(tenantLine = "true")`也适用于动态表名替换
     */
    @Test
    public void selectById() {
        User user = userMapper.selectById(1087982257332887553L);
        System.out.println(user);
        // SELECT id, name, age, email, manager_id, create_time, update_time, version FROM user WHERE id = ? AND deleted = 0
        // SELECT id, name, age, email, manager_id, create_time, update_time, version FROM user WHERE id = ? AND deleted = 0 AND manager_id = 1088248166370832385
    }
}
