package com.tuyrk.mybatisplus.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tuyrk.mybatisplus.ApplicationTests;
import com.tuyrk.mybatisplus.quickstart.dao.UserMapper;
import com.tuyrk.mybatisplus.quickstart.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SelectCustomTest extends ApplicationTests {
    @Autowired
    private UserMapper userMapper;

    /**
     * 自定义SQL-注解式查询
     */
    @Test
    public void selectAllByAnnotation() {
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();

        lambdaQuery.like(User::getName, "雨").lt(User::getAge, 40);
        List<User> userList1 = userMapper.selectAllByAnnotation(lambdaQuery);
        userList1.forEach(System.out::println);
    }

    /**
     * 自定义SQL-注解式查询
     */
    @Test
    public void selectAllByXml() {
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();

        lambdaQuery.like(User::getName, "雨").lt(User::getAge, 40);
        List<User> userList1 = userMapper.selectAllByXml(lambdaQuery);
        userList1.forEach(System.out::println);
    }
}
